package controller;

import model.beans.Segnalazione;
import model.beans.Utente;
import model.services.AutenticazioneServiceImpl;
import model.services.SegnalazioniServiceImpl;
import model.storage.ConPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AutenticazioneController", value = "/AutenticazioneController/*")
public class AutenticazioneController extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

      String path = request.getPathInfo();

      String resource = "/";


      if(path.equals("/login")) {
         resource = "/login.jsp";
      } else if (path.equals("/registrazione")) {
         resource = "/registrazione.jsp";
      } else {
         response.sen
      }

      switch (path) {
         case "/login":
            resource = "/login.jsp";
            break;
         case "/registrazione":
            resource = "/registrazione.jsp";
            break;
         default:

      }

      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results" + resource);
      dispatcher.forward(request, response);

   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

   }

   private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
      HttpSession session = request.getSession(true);
      Utente utente = (Utente) session.getAttribute("utente");

      if(utente == null) {
         utente = new Utente();
         utente.setEmail(request.getParameter("email"));
         utente.createPasswordHash(request.getParameter("password"));

         AutenticazioneServiceImpl autenticazioneService = new AutenticazioneServiceImpl(request.getSession(true));
         utente = autenticazioneService.login(utente);
      }

      if (utente != null) {
         session.setAttribute("utente", utente);
      } else {
         response.sendRedirect(getServletContext().getContextPath() + "/AutenticazioneController/login");
      }
   }
}
