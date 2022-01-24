package controller;

import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.StatoCampagna;
import model.beans.Utente;
import model.services.CampagnaServiceImpl;
import model.services.CategoriaServiceImpl;
import model.storage.ConPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GestioneCampagnaController", value = "/GestioneCampagnaController/*")
public class GestioneCampagnaController extends HttpServlet {
   HttpSession session;

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String resource = "/";

      session = request.getSession(false);

      if (session == null || session.getAttribute("utente") == null) {
         response.sendRedirect(
                 getServletContext().getContextPath()
                         + "/AutenticazioneController/login");
         return;
      }

      switch (request.getPathInfo()) {
         case "/creaCampagna": {
            request.setAttribute("categorie", new CategoriaServiceImpl().visualizzaCategorie());
            resource = "/WEB-INF/results/form_campagna.jsp";
            break;
         }

         default:
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            return;
      }

      RequestDispatcher dispatcher =
              request.getRequestDispatcher(resource);
      dispatcher.forward(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      if (session == null && session.getAttribute("utente") == null) {
         response.sendRedirect(
                 getServletContext().getContextPath()
                         + "/AutenticazioneController/login");

         return;
      }

      switch (request.getPathInfo()) {
         case "/creaCampagna":
            creaCampagna(request, response);
            break;

         default:
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Risorsa non trovata");
            return;
      }
   }

   @Override
   public void destroy() {
      ConPool.getInstance().closeDataSource();
      super.destroy();
   }

   private void creaCampagna(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

      Campagna c = new Campagna();
      c.setStato(StatoCampagna.ATTIVA);
      c.setTitolo(request.getParameter("titolo"));
      c.setDescrizione(request.getParameter("descrizione"));
      c.setSommaRaccolta(0d);
      c.setSommaTarget(Double.parseDouble(request.getParameter("sommaTarget")));
      c.setUtente((Utente) request.getSession(false).getAttribute("utente"));

      Categoria categoria = new Categoria();
      categoria.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));

      c.setCategoria(new CategoriaServiceImpl().visualizzaCategoria(categoria));
      System.out.println(c);

      if (new CampagnaServiceImpl().creazioneCampagna(c)) {
         response.sendRedirect(
                 getServletContext().getContextPath() + "/index.jsp");
         return;
      } else {
         response.sendRedirect(
                 getServletContext().getContextPath()
                         + "/GestioneCampagnaController/creaCampagna");
         return;
      }
   }
}
