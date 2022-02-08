package controller;

import controller.utils.FileServlet;
import controller.utils.Validator;
import model.beans.Utente;
import model.services.AutenticazioneService;
import model.services.ReportService;
import model.services.TipoReport;
import model.services.AutenticazioneServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@WebServlet(name = "AutenticazioneController",
        value = "/autenticazione/*")
@MultipartConfig
public final class AutenticazioneController extends HttpServlet {
   /**
    * Variabile per il service di Autenticazione.
    */
   private AutenticazioneService service;

   /**
    * Costruttore classe AutenticazioneController.
    *
    * @param newService il service che si interfaccia con i DAO
    */
   public AutenticazioneController(final AutenticazioneService newService) {
      service = newService;
   }

   /**
    * Costruttore classe AutenticazioneController.
    */
   public AutenticazioneController() {
      service = new AutenticazioneServiceImpl();
   }

   @Override
   public void doGet(final HttpServletRequest request,
                     final HttpServletResponse response)
           throws ServletException, IOException {
      HttpSession session = request.getSession();
      String path;

      if (!new Validator(request)
              .isValidBean(Utente.class, session.getAttribute("utente"))) {
         String pathInfo = request.getPathInfo();
         if ("/login".equals(pathInfo)) {
            path = "/WEB-INF/results/login.jsp";
         } else if ("/registrazione".equals(pathInfo)) {
            path = "/WEB-INF/results/registrazione.jsp";
         } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
         }
         request.getRequestDispatcher(path).forward(request, response);
      } else {
         if (request.getPathInfo().equals("/logout")) {
            service.logout(session);
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/index.jsp");
         } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
         }
      }
   }

   @Override
   public void doPost(final HttpServletRequest request,
                      final HttpServletResponse response)
           throws IOException, ServletException {
      HttpSession session = request.getSession();
      Utente utente;

      switch (request.getPathInfo()) {
         case "/login" -> {
            utente = new Utente();
            utente.setEmail(request.getParameter("email"));
            utente.createPasswordHash(request.getParameter("password"));

            utente = service.login(utente);

            if (utente != null && utente.getIdUtente() != -1) {
               session.setAttribute("utente", utente);
            } else if (utente != null && utente.getIdUtente() == -1) {
               long minuti = ChronoUnit.MINUTES
                       .between(LocalDateTime.now(), utente.getDataBan());
               final long min = 60;
               long ore = minuti / min;
               minuti = minuti - (ore * min);

               ReportService.creaReport(request, TipoReport.ERRORE,
                       "Utente bannato", "Ritenta il login tra "
                               + ore + " ore", "e " + minuti + " minuti");
               response.sendRedirect(
                       request.getServletContext().getContextPath()
                               + "/autenticazione/login");
               return;
            } else {
               ReportService.creaReport(request, TipoReport.ERRORE,
                       "Credenziali sbagliate", "Ritenta il login");
               response.sendRedirect(
                       request.getServletContext().getContextPath()
                               + "/autenticazione/login");
               return;
            }
         }
         case "/registrazione" -> {
            utente = new Utente();

            if (request.getParameter("password").equals(
                    request.getParameter("confermaPassword"))
                    && request.getParameter("email").equals(
                    request.getParameter("confermaEmail"))) {
               utente.createPasswordHash(request.getParameter("password"));
               utente.setEmail(request.getParameter("email"));
               utente.setNome(request.getParameter("nome"));
               utente.setCognome(request.getParameter("cognome"));
               utente.setDataDiNascita(
                       LocalDate.parse(
                               request.getParameter("dataDiNascita")));
               utente.setTelefono(request.getParameter("telefono"));
               utente.setStrada(request.getParameter("indirizzo"));
               utente.setCitta(request.getParameter("citta"));
               utente.setCap(request.getParameter("cap"));
               utente.setCf(request.getParameter("cf"));

               List<String> fileNames = FileServlet.uploadFoto(request);

               if (fileNames.size() > 0) {
                  utente.setFotoProfilo(fileNames.get(0));
               } else {
                  utente.setFotoProfilo("");
               }

               if (service.registrazione(utente)) {
                  session.setAttribute("utente", utente);

                  ReportService.creaReport(request, TipoReport.INFO,
                          "Esito operazione:",
                          "Registrazione avvenuta con successo");
               } else {
                  ReportService.creaReport(request, TipoReport.ERRORE,
                          "Esito operazione:",
                          "Registrazione non avvenuta con successo");
               }
            } else {
               ReportService.creaReport(request, TipoReport.ERRORE,
                       "Esito operazione:",
                       "Registrazione non avvenuta con successo");

               response.sendRedirect(
                       request.getServletContext().getContextPath()
                               + "/autenticazione/registrazione");
            }
         }

         default -> {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
         }
      }

      response.sendRedirect(request.getServletContext()
              .getContextPath() + "/index.jsp");
   }
}
