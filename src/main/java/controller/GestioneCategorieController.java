package controller;

import controller.utils.Validator;
import model.DAO.CategoriaDAO;
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
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GestioneCategorieController",
        value = "/categorie/*", loadOnStartup = 0)
public final class GestioneCategorieController extends HttpServlet {

   @Override
   public void init() {
      this.getServletContext().setAttribute("categorieList",
              new CategoriaServiceImpl().visualizzaCategorie());
   }

   @Override
   protected void doPost(final HttpServletRequest request,
                         final HttpServletResponse response)
           throws IOException {
      String path = request.getPathInfo();
      HttpSession session = request.getSession();

      switch (path) {
         case "/inserisciCategoria" -> {
            if (!new Validator(request)
                    .isValidBean(new Utente(),
                            session.getAttribute("utente"))) {

               response.sendRedirect(getServletContext().getContextPath()
                       + "/AutenticazioneController/login");
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

            break;
         }
         case "/modificaCategoria" -> {
            modificaCategoria(request, response, session);
            break;
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

   private void modificaCategoria(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final HttpSession session)
           throws IOException {

        /*Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }

        if (!utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
        }*/

      int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
      String nomeCategoria = request.getParameter("nomeCategoria");
      CategoriaService service = new CategoriaServiceImpl(new CategoriaDAO());
      Categoria c = new Categoria();
      c.setIdCategoria(idCategoria);
      c = service.visualizzaCategoria(c);

      c.setNome(nomeCategoria);
      if (service.modificaCategoria(c)) {
         System.out.println("ok");
         List<Categoria> categorie =
                 (List<Categoria>)
                         getServletContext().getAttribute("categorieList");

         Optional<Categoria> optional = categorie.stream()
                 .filter(c1 -> c1.getIdCategoria() == idCategoria)
                 .findFirst();
         if (optional.isPresent()) {
            Categoria categoria = optional.get();
            categoria.setNome(nomeCategoria);

         } else {
            System.out.println("not found");
         }
      } else {
         System.out.println("errore");
      }

   }
}
