package controller;

import model.beans.Donazione;
import model.beans.Utente;
import model.services.DonazioniServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneDonazioneController",
        value = "/donazione/*")

public final class GestioneDonazioneController extends HttpServlet {
   @Override
   protected void doGet(final HttpServletRequest request,
                        final HttpServletResponse response)
           throws ServletException, IOException {

      String resource = "/WEB-INF/results/visualizzaDonazioni.jsp";
      HttpSession session = request.getSession(false);

      if (session != null && session.getAttribute("utente") != null) {

         switch (request.getPathInfo()) {

            case "/effettuaDonazione":
               request.getRequestDispatcher(
                               "/WEB-INF/results/PaymentProcessorMock.jsp")
                       .forward(request, response);
               return;

            case "/scriviCommento":

               if (session.getAttribute("donazione") != null) {
                  request.getRequestDispatcher(
                                  "/WEB-INF/results/commentoDonazione.jsp")
                          .forward(request, response);
                  return;
               }

            default:
               request.setAttribute("donazioniList", new DonazioniServiceImpl()
                       .visualizzaDonazioni(
                               (Utente) session.getAttribute("utente")));
         }

      } else {
         response.sendRedirect(
                 getServletContext().getContextPath()
                         + "/AutenticazioneController/login");
         return;
      }

      request.getRequestDispatcher(resource).forward(request, response);
   }

   @Override
   protected void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException {

      switch (request.getPathInfo()) {
         case "/registraDonazione":
            //TODO DAL PAYMENT
            break;

         case "/scriviCommento":
            if (request.getSession(false) != null) {
               Donazione donazione = (Donazione) request.getSession(false)
                       .getAttribute("donazione");

               if (donazione != null) {
                  donazione.setCommento(request.getParameter("commento"));
                  new DonazioniServiceImpl().commenta(donazione);
               }
            }
            break;

         default:
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            return;
      }

      response.sendRedirect(
              getServletContext().getContextPath()
                      + "/donazione/");
   }
}
