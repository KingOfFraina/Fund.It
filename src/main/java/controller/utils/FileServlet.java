package controller.utils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.OutputStream;
import java.io.Closeable;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@WebServlet(name = "FileServlet", urlPatterns = "/file/*", loadOnStartup = 0)
@MultipartConfig
public class FileServlet extends HttpServlet {
   /**
    * Size del buffer.
    */
   private static final int DEFAULT_BUFFER_SIZE = 10240; // ..bytes = 10KB.
   /**
    * Expire time.
    */
   private static final long DEFAULT_EXPIRE_TIME = 604800000L; // ..ms = 1 week.
   /**
    * Multipart byteranges.
    */
   private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";
   /**
    * BasePath dove vengono messe le immagini.
    */
   private static String basePath;

   /**
    * Initialize the servlet.
    *
    * @see HttpServlet#init().
    */
   @Override
   public void init() throws ServletException {

      this.basePath = System.getenv("CATALINA_HOME") + File.separator
              + "webapps" + File.separator + "uploads";
      System.out.println(basePath);

      // Validate base path.
      if (this.basePath == null) {
         throw new ServletException("FileServlet init param "
                 + "'basePath' is required.");
      } else {
         File path = new File(this.basePath);
         if (!path.exists()) {
            throw new ServletException("FileServlet init param 'basePath' "
                    + "value '" + this.basePath
                    + "' does actually not exist in file system.");
         } else if (!path.isDirectory()) {
            throw new ServletException("FileServlet init param 'basePath' "
                    + "value '" + this.basePath
                    + "' is actually not a directory in file system.");
         } else if (!path.canRead()) {
            throw new ServletException("FileServlet init param 'basePath' "
                    + "value '" + this.basePath
                    + "' is actually not readable in file system.");
         }
      }
   }

   /**
    * Process HEAD request. This returns the same headers as GET request,
    * but without content.
    *
    * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse).
    */
   @Override
   protected void doHead(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException {
      // Process request without content.
      processRequest(request, response, false);
   }

   /**
    * Process GET request.
    *
    * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse).
    */
   @Override
   protected void doGet(final HttpServletRequest request,
                        final HttpServletResponse response)
           throws IOException {
      // Process request with content.
      processRequest(request, response, true);
   }

   /**
    * Process the actual request.
    *
    * @param request  The request to be processed.
    * @param response The response to be created.
    * @param content  Whether the request body should
    *                 be written (GET) or not (HEAD).
    * @throws IOException If something fails at I/O level.
    */
   private void processRequest(final HttpServletRequest request,
                               final HttpServletResponse response,
                               final boolean content)
           throws IOException {
      File file = helper(request, response);
      String fileName = file.getName();
      long length = file.length();
      long lastModified = file.lastModified();
      String eTag = fileName + "_" + length + "_" + lastModified;
      long expires = System.currentTimeMillis() + DEFAULT_EXPIRE_TIME;

      String ifNoneMatch = request.getHeader("If-None-Match");
      if (ifNoneMatch != null && matches(ifNoneMatch, eTag)) {
         response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
         response.setHeader("ETag", eTag); // Required in 304.
         response.setDateHeader("Expires", expires);
         return;
      }
      long ifModifiedSince = request.getDateHeader("If-Modified-Since");
      final int size = 1000;
      if (ifNoneMatch == null && ifModifiedSince != -1
              && ifModifiedSince + size > lastModified) {
         response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
         response.setHeader("ETag", eTag); // Required in 304.
         response.setDateHeader("Expires", expires);
         return;
      }
      String ifMatch = request.getHeader("If-Match");
      if (ifMatch != null && !matches(ifMatch, eTag)) {
         response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
         return;
      }
      long ifUnmodifiedSince = request.getDateHeader("If-Unmodified-Since");
      if (ifUnmodifiedSince != -1 && ifUnmodifiedSince + size <= lastModified) {
         response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
         return;
      }
      Range full = new Range(0, length - 1, length);
      List<Range> ranges = new ArrayList<Range>();
      String range = request.getHeader("Range");
      if (range != null) {
         if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
            response.setHeader("Content-Range",
                    "bytes */" + length); // Required in 416.
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
         }
         String ifRange = request.getHeader("If-Range");
         if (ifRange != null && !ifRange.equals(eTag)) {
            try {
               long ifRangeTime = request.getDateHeader("If-Range");
               if (ifRangeTime != -1 && ifRangeTime + size < lastModified) {
                  ranges.add(full);
               }
            } catch (IllegalArgumentException ignore) {
               ranges.add(full);
            }
         }
         if (ranges.isEmpty()) {
            final int len = 6;
            for (String part : range.substring(len).split(",")) {
               long start = sublong(part, 0, part.indexOf("-"));
               long end = sublong(part, part.indexOf("-") + 1, part.length());
               if (start == -1) {
                  start = length - end;
                  end = length - 1;
               } else if (end == -1 || end > length - 1) {
                  end = length - 1;
               }
               if (start > end) {
                  response.setHeader("Content-Range", "bytes */" + length);
                  response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
                  return;
               }
               ranges.add(new Range(start, end, length));
            }
         }
      }
      String contentType = getServletContext().getMimeType(fileName);
      boolean acceptsGzip = false;
      String disposition = "inline";
      if (contentType == null) {
         contentType = "application/octet-stream";
      }
      if (contentType.startsWith("text")) {
         String acceptEncoding = request.getHeader("Accept-Encoding");
         acceptsGzip = acceptEncoding != null
                 && accepts(acceptEncoding, "gzip");
         contentType += ";charset=UTF-8";
      } else if (!contentType.startsWith("image")) {
         String accept = request.getHeader("Accept");
         disposition = accept != null
                 && accepts(accept, contentType) ? "inline" : "attachment";
      }
      response.reset();
      response.setBufferSize(DEFAULT_BUFFER_SIZE);
      response.setHeader("Content-Disposition",
              disposition + ";filename=\"" + fileName + "\"");
      response.setHeader("Accept-Ranges", "bytes");
      response.setHeader("ETag", eTag);
      response.setDateHeader("Last-Modified", lastModified);
      response.setDateHeader("Expires", expires);
      RandomAccessFile input = null;
      OutputStream output = null;
      try {
         input = new RandomAccessFile(file, "r");
         output = response.getOutputStream();
         if (ranges.isEmpty() || ranges.get(0) == full) {
            Range r = full;
            response.setContentType(contentType);
            if (content) {
               if (acceptsGzip) {
                  response.setHeader("Content-Encoding", "gzip");
                  output = new GZIPOutputStream(output, DEFAULT_BUFFER_SIZE);
               } else {
                  response.setHeader("Content-Length",
                          String.valueOf(r.length));
               }
               copy(input, output, r.start, r.length);
            }
         } else if (ranges.size() == 1) {
            Range r = ranges.get(0);
            response.setContentType(contentType);
            response.setHeader("Content-Range", "bytes "
                    + r.start + "-" + r.end + "/" + r.total);
            response.setHeader("Content-Length", String.valueOf(r.length));
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.
            if (content) {
               copy(input, output, r.start, r.length);
            }
         } else {
            response.setContentType("multipart/byteranges; boundary="
                    + MULTIPART_BOUNDARY);
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.
            if (content) {
               ServletOutputStream sos = (ServletOutputStream) output;
               for (Range r : ranges) {
                  sos.println();
                  sos.println("--" + MULTIPART_BOUNDARY);
                  sos.println("Content-Type: " + contentType);
                  sos.println("Content-Range: bytes " + r.start
                          + "-" + r.end + "/" + r.total);
                  copy(input, output, r.start, r.length);
               }
               sos.println();
               sos.println("--" + MULTIPART_BOUNDARY + "--");
            }
         }
      } finally {
         close(output);
         close(input);
      }
   }

   /**
    * Help the actual request.
    *
    * @param request  The request to be processed.
    * @param response The response to be created.
    * @throws IOException If something fails at I/O level.
    * @return file
    */
   private File helper(final HttpServletRequest request,
                       final HttpServletResponse response) throws IOException {
      String requestedFile = request.getPathInfo();

      if (requestedFile == null) {
         response.sendError(HttpServletResponse.SC_NOT_FOUND);
         return null;
      }
      File file = new File(basePath, URLDecoder.decode(requestedFile,
              StandardCharsets.UTF_8));

      if (!file.exists()) {
         response.sendError(HttpServletResponse.SC_NOT_FOUND);
         return null;
      }

      return file;
   }

   /**
    * Returns true if the given accept header accepts the given value.
    *
    * @param acceptHeader The accept header.
    * @param toAccept     The value to be accepted.
    * @return True if the given accept header accepts the given value.
    */
   private static boolean accepts(final String acceptHeader,
                                  final String toAccept) {
      String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
      Arrays.sort(acceptValues);
      return Arrays.binarySearch(acceptValues, toAccept) > -1
              || Arrays.binarySearch(acceptValues,
              toAccept.replaceAll("/.*$", "/*")) > -1
              || Arrays.binarySearch(acceptValues, "*/*") > -1;
   }

   /**
    * Returns true if the given match header matches the given value.
    *
    * @param matchHeader The match header.
    * @param toMatch     The value to be matched.
    * @return True if the given match header matches the given value.
    */
   private static boolean matches(final String matchHeader,
                                  final String toMatch) {
      String[] matchValues = matchHeader.split("\\s*,\\s*");
      Arrays.sort(matchValues);
      return Arrays.binarySearch(matchValues, toMatch) > -1
              || Arrays.binarySearch(matchValues, "*") > -1;
   }

   /**
    * Returns a substring of the given string value from the given begin
    * index to the given end
    * index as a long. If the substring is empty, then -1 will be returned
    *
    * @param value      The string value to return a substring as long for.
    * @param beginIndex The begin index of the substring to be returned as long.
    * @param endIndex   The end index of the substring to be returned as long.
    * @return A substring of the given string value as long or -1
    * if substring is empty.
    */
   private static long sublong(final String value,
                               final int beginIndex,
                               final int endIndex) {
      String substring = value.substring(beginIndex, endIndex);
      return (substring.length() > 0) ? Long.parseLong(substring) : -1;
   }

   /**
    * Copy the given byte range of the given input to the given output.
    *
    * @param input  The input to copy the given range to the given output for.
    * @param output The output to copy the given range from the given input for.
    * @param start  Start of the byte range.
    * @param length Length of the byte range.
    * @throws IOException If something fails at I/O level.
    */
   private static void copy(final RandomAccessFile input,
                            final OutputStream output,
                            final long start,
                            final long length)
           throws IOException {
      byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
      int read;

      if (input.length() == length) {
         // Write full range.
         while ((read = input.read(buffer)) > 0) {
            output.write(buffer, 0, read);
         }
      } else {
         // Write partial range.
         input.seek(start);
         long toRead = length;

         while ((read = input.read(buffer)) > 0) {
            toRead -= read;
            if (toRead > 0) {
               output.write(buffer, 0, read);
            } else {
               output.write(buffer, 0, (int) toRead + read);
               break;
            }
         }
      }
   }

   /**
    * Close the given resource.
    *
    * @param resource The resource to be closed.
    */
   private static void close(final Closeable resource) {
      if (resource != null) {
         try {
            resource.close();
         } catch (IOException ignore) {

         }
      }
   }

   /**
    * This class represents a byte range.
    */
   protected class Range {
      /**
       * start range.
       */
      private long start;
      /**
       * end range.
       */
      private long end;
      /**
       * length range.
       */
      private long length;
      /**
       * total range.
       */
      private long total;

      /**
       * Construct a byte range.
       *
       * @param st  Start of the byte range.
       * @param en  End of the byte range.
       * @param tot Total length of the byte source.
       */
      public Range(final long st,
                   final long en,
                   final long tot) {
         this.start = st;
         this.end = en;
         this.length = en - st + 1;
         this.total = tot;
      }

   }

   /**
    * Metodo per upload delle foto.
    *
    * @param request Request da dove prelevare le foto
    * @return lista dei nomi dei file
    * @throws ServletException
    * @throws IOException
    */
   public static List<String> uploadFoto(final HttpServletRequest request)
           throws ServletException, IOException {
      List<String> fileNameList = new ArrayList<>();

      for (Part p : request.getParts()) {
         if (p.getSubmittedFileName() != null
                 && !p.getSubmittedFileName().isEmpty()) {
            try (InputStream is = p.getInputStream()) {
               String path = FileServlet.basePath + File.separator;
               String nameFile = LocalDateTime.now().toString()
                       .replace(":", "-") + p.getSubmittedFileName();
               File file = new File(path + nameFile);
               Files.copy(is, file.toPath(),
                       StandardCopyOption.REPLACE_EXISTING);
               fileNameList.add(nameFile);
            }
         }
      }

      return fileNameList;
   }

}
