package controller;

import model.DAO.FaqDAO;
import model.beans.FAQ;
import model.beans.Utente;
import model.services.FaqServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneFAQController",
        value = "/faq/*")
public final class GestioneFAQController extends HttpServlet {

   @Override
   protected void doGet(final HttpServletRequest request,
                        final HttpServletResponse response)
           throws ServletException, IOException {

      String path = request.getPathInfo();
      String resource = "/WEB-INF/results/visualizzaFAQ.jsp";

      if (!path.equals("/visualizzaFAQ")) {
         HttpSession session = request.getSession(false);

         if (session != null && session.getAttribute("utente") != null) {
            Utente utente = (Utente) session.getAttribute("utente");

            if (!utente.isAdmin()) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                       "Non Autorizzato");
               return;
            }

            if (path.equals("/modificaFAQ")) {
               request.setAttribute("faq", new FaqServiceImpl(new FaqDAO())
                       .visualizzaFaq(Integer
                               .parseInt(request.getParameter("idFaq"))));
            }

            resource = "/WEB-INF/results/formFAQ.jsp";
         } else {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/AutenticazioneController/login");
            return;
         }
      } else {
         visualizzaFAQ(request);
      }

      request.getRequestDispatcher(resource).forward(request, response);
   }

   @Override
   protected void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException {
      String path = request.getPathInfo();

      switch (path) {
         case "/inserisciFAQ":
            inserisciFAQ(request);
            break;
         case "/modificaFAQ":
            modificaFAQ(request);
            break;
         case "/eliminaFAQ":
            cancellaFAQ(request);
            break;
         default:
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            return;
      }

      response.sendRedirect(
              getServletContext().getContextPath()
                      + "/faq/visualizzaFAQ");
   }

   private void visualizzaFAQ(final HttpServletRequest request) {
      request.setAttribute("faqList", new FaqServiceImpl(new FaqDAO())
              .visualizzaFaq());
   }

   private void inserisciFAQ(final HttpServletRequest request) {
      Utente utente = (Utente) request.getSession(false).getAttribute("utente");

      String domanda = request.getParameter("domanda");
      String risposta = request.getParameter("risposta");

      if (domanda == null || domanda.isBlank()
              || risposta == null || domanda.isBlank()) {

         new IllegalArgumentException("Input errati");
      }

      FAQ faq = new FAQ();
      faq.setDomanda(domanda);
      faq.setRisposta(risposta);
      faq.setUtenteCreatore(utente);

      new FaqServiceImpl(new FaqDAO()).inserisciFaq(faq);
   }

   private void modificaFAQ(final HttpServletRequest request) {
      Utente utente = (Utente) request.getSession(false).getAttribute("utente");

      String domanda = request.getParameter("domanda");
      String risposta = request.getParameter("risposta");
      int idFAQ = Integer.parseInt(request.getParameter("idFaq"));

      if (domanda == null || domanda.isBlank()
              || risposta == null || domanda.isBlank()) {

         new IllegalArgumentException("Input errati");
      }

      FAQ faq = new FAQ();
      faq.setIdFaq(idFAQ);
      faq.setDomanda(domanda);
      faq.setRisposta(risposta);
      faq.setUtenteCreatore(utente);

      new FaqServiceImpl(new FaqDAO()).modificaFaq(faq);
   }

   private void cancellaFAQ(final HttpServletRequest request) {

      FAQ faq = new FAQ();
      faq.setIdFaq(Integer.parseInt(request.getParameter("idFaq")));
      new FaqServiceImpl(new FaqDAO()).cancellaFaq(faq);
   }
}
