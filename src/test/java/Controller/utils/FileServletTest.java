package Controller.utils;

import controller.utils.FileServlet;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class FileServletTest {
    HttpServletRequest request;
    HttpServletResponse response;
    FileServlet fileServlet;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        fileServlet = new FileServlet();
    }

    @Test
    public void testFileTrovato() throws IOException {
        //Nota inserire il file test.jpg nella cartella
        when(request.getPathInfo()).thenReturn("/test.jpg");

        fileServlet.doGet(request, response);
        verify(request, atLeastOnce()).getPathInfo();
        //verify(request, atLeastOnce()).getHeader(anyString());
        //verify(request, atLeastOnce()).getDateHeader(anyString());
    }

    @Test
    public void testFileNonTrovato() throws IOException {
        when(request.getPathInfo()).thenReturn("/test1.jpg");

        fileServlet.doGet(request, response);
        verify(request, atLeastOnce()).getPathInfo();
        verify(response, atLeastOnce()).sendError(anyInt());
    }
}
