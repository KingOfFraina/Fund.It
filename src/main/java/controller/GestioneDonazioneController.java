package controller;

import model.DAO.DonazioneDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import model.services.DonazioniService;
import model.services.DonazioniServiceImpl;
import model.services.ReportService;
import model.services.TipoReport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "GestioneDonazioneController",
        value = "/donazione/*")
public final class GestioneDonazioneController extends HttpServlet {
   @Override
   protected void doGet(final HttpServletRequest request,
                        final HttpServletResponse response)
           throws ServletException, IOException {

      String resource = "/WEB-INF/results/visualizzaDonazioni.jsp";
      HttpSession session = request.getSession();
      Utente userSession = (Utente) session.getAttribute("utente");
      String path = request.getPathInfo() == null
              ? "/" : request.getPathInfo();
      Donazione donazione = (Donazione) session.getAttribute("donazione");
      DonazioniService donazioniService =
              new DonazioniServiceImpl(new DonazioneDAO());

      if (userSession != null) {
         if ("/scriviCommento".equals(path)) {
            if (donazione != null) {
               request.getRequestDispatcher(
                               "/WEB-INF/results/"
                                       + "commentoDonazione.jsp")
                       .forward(request, response);
               return;
            }
         } else {
            request.setAttribute("donazioniList",
                    donazioniService
                            .visualizzaDonazioni(userSession));
         }
      } else {
         response.sendRedirect(
                 getServletContext().getContextPath()
                         + "/autenticazione/login");
         return;
      }
      request.getRequestDispatcher(resource).forward(request, response);
   }

   @Override
   protected void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException, ServletException {

      String path = request.getPathInfo() == null ? "/"
              : request.getPathInfo();
      CampagnaService campagnaService =
              new CampagnaServiceImpl();
      DonazioniService donazioniService =
              new DonazioniServiceImpl(new DonazioneDAO());

      int id = Integer.parseInt(request.getParameter("idCampagna"));
      HttpSession session = request.getSession();
      Utente utente = (Utente) session.getAttribute("utente");

      Campagna campagna = campagnaService
              .trovaCampagna(id);

      switch (path) {
         case "/registraDonazione" -> {
            if (utente != null) {
               Donazione donazione = new Donazione();
               donazione.setCampagna(campagna);
               donazione.setUtente((Utente)
                       session.getAttribute("utente"));
               donazione.setSommaDonata(Double.parseDouble(
                       request.getParameter("sommaDonata")));
               donazione.setAnonimo(false);
               donazione.setCommento(null);
               donazione.setRicevuta(request.getParameter("ricevuta"));
               donazione.setDataOra(LocalDateTime.now());

               if (new DonazioniServiceImpl().
                       effettuaDonazione(donazione)) {
                  ReportService.creaReport(request,
                          TipoReport.INFO,
                          "Donazione andata a buon fine");
               } else {
                  ReportService.creaReport(request, TipoReport.ERRORE,
                          "Donazione non salvata");
               }
               session.setAttribute("donazione", donazione);

               request.setAttribute("idCampagna", id);
               request.getRequestDispatcher(
                               "/WEB-INF/results/commentoDonazione.jsp")
                       .forward(request, response);
               return;
            }
         }
         case "/scriviCommento" -> {
            Donazione donazione = (Donazione) session
                    .getAttribute("donazione");
            if (donazione != null) {
               donazione.setCommento(request.getParameter("commento"));
               if (request.getParameter("anonimo") != null) {
                  donazione.setAnonimo(true);
               }
               if (donazioniService.commenta(donazione)) {
                  campagna.setSommaRaccolta(
                          campagna.getSommaRaccolta()
                                  + donazione.getSommaDonata());
                  campagnaService.modificaCampagna(campagna);
                  session.removeAttribute("donazione");
                  ReportService.creaReport(request,
                          TipoReport.INFO,
                          "Commento andato a buon fine");
                  response.sendRedirect(getServletContext()
                          .getContextPath()
                          + "/campagna/campagna?idCampagna="
                          + campagna.getIdCampagna());
                  return;
               } else {
                  ReportService.creaReport(request, TipoReport.ERRORE,
                          "Commento non salvato");
                  response
                          .sendError(HttpServletResponse
                                  .SC_INTERNAL_SERVER_ERROR);
                  return;
               }
            }
         }
         default -> {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            return;
         }
      }
      response.sendRedirect(
              getServletContext().getContextPath()
                      + "/");
   }
}
