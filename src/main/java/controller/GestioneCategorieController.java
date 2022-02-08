package controller;

import controller.utils.Validator;
import model.beans.Categoria;
import model.beans.Utente;
import model.services.CategoriaService;
import model.services.CategoriaServiceImpl;
import model.services.ReportService;
import model.services.TipoReport;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneCategorieController",
        value = "/categorie/*", loadOnStartup = 0)
public final class GestioneCategorieController extends HttpServlet {

   @Override
   public void init() {
      this.getServletContext().setAttribute("categorieList",
              new CategoriaServiceImpl().visualizzaCategorie());
   }

   @Override
   public void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException {
      String path = request.getPathInfo();
      HttpSession session = request.getSession();

      switch (path) {
         case "/inserisciCategoria" -> {
            if (!new Validator(request)
                    .isValidBean(Utente.class,
                            session.getAttribute("utente"))) {

               response.sendRedirect(getServletContext().getContextPath()
                       + "/autenticazione/login");
               return;
            } else {
               Utente utente = (Utente) session.getAttribute("utente");

               if (!utente.isAdmin()) {
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                  return;
               } else {
                  String nomeCategoria = request.getParameter("nomeCategoria");

                  if (nomeCategoria.isBlank()) {
                     response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                     return;
                  }

                  Categoria c = new Categoria();
                  c.setNome(nomeCategoria);

                  if (new CategoriaServiceImpl().inserisciCategoria(c)) {
                     visualizzaCategorie();
                     ReportService.creaReport(request, TipoReport.INFO,
                             "Esito operazione:",
                             "Aggiunta categoria eseguita");
                  } else {
                     ReportService.creaReport(request, TipoReport.ERRORE,
                             "Esito operazione:",
                             "Aggiunta categoria non eseguita");
                  }
               }
            }
         }
         case "/modificaCategoria" -> {
            if (!new Validator(request)
                    .isValidBean(Utente.class,
                            session.getAttribute("utente"))) {

               response.sendRedirect(getServletContext().getContextPath()
                       + "/autenticazione/login");
               return;
            } else {
               Utente utente = (Utente) session.getAttribute("utente");

               if (!utente.isAdmin()) {
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                  return;
               } else {
                  int idCategoria = Integer.parseInt(
                          request.getParameter("idCategoria"));
                  String nomeCategoria = request.getParameter("nomeCategoria");
                  CategoriaService service = new CategoriaServiceImpl();
                  Categoria categoria = new Categoria();
                  categoria.setIdCategoria(idCategoria);
                  categoria = service.visualizzaCategoria(categoria);
                  categoria.setNome(nomeCategoria);

                  if (service.modificaCategoria(categoria)) {
                     visualizzaCategorie();
                     ReportService.creaReport(request, TipoReport.INFO,
                             "Esito operazione:",
                             "Modifica categoria eseguita");
                  } else {
                     ReportService.creaReport(request, TipoReport.ERRORE,
                             "Esito operazione:",
                             "Modifica categoria non eseguita");
                  }
               }
            }
         }

         default -> {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
         }
      }

      response.sendRedirect(getServletContext().getContextPath()
              + "/GestioneUtenteController/visualizzaDashboardAdmin");
   }

   private void visualizzaCategorie() {
      this.getServletContext().setAttribute("categorieList",
              new CategoriaServiceImpl().visualizzaCategorie());
   }
}
