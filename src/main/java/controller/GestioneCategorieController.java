package controller;

import model.beans.Categoria;
import model.beans.Utente;
import model.services.CategoriaService;
import model.services.CategoriaServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneCategorieController",
        value = "/GestioneCategorieController/*")
public final class GestioneCategorieController extends HttpServlet {

    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        String resource = "/";

        switch (path) {
            case "/inserisciCategoria":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/modificaCategoria":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/visualizzaCategorie":
                resource = "/WEB-INF/results/"; //todo path
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(resource);
        dispatcher.forward(request, response);
        return;
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        switch (path) {
            case "/inserisciCategoria":
                 inserisciCategoria(request, response);
                break;
            case "/modificaCategoria":
                 modificaCategoria(request, response);
                break;
            case "/visualizzaCategorie":
                 visualizzaCategorie(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }
        return;
    }

    private void visualizzaCategorie(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        CategoriaService cs = new CategoriaServiceImpl();
        request.setAttribute("categorieList", cs.visualizzaCategorie());
        //todo forward
    }

    private void modificaCategoria(final HttpServletRequest request,
                                   final HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("utente") == null
                || !session.getAttribute("utente").getClass().getSimpleName().
                equals(Utente.class.getSimpleName())) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }
        Utente utente = (Utente) session.getAttribute("utente");
        if (!utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
        }

        if (request.getAttribute("categoria") == null
                || !request.getAttribute("categoria").getClass().
                getSimpleName().equals(Categoria.class.getSimpleName())) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }
        Categoria c = (Categoria) request.getAttribute("categoria");
        CategoriaService cs = new CategoriaServiceImpl();
        cs.modificaCategoria(c);
        return;
        //todo return
    }

    private void inserisciCategoria(final HttpServletRequest request,
                                    final HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("utente") == null
                || !session.getAttribute("utente").getClass().getSimpleName().
                equals(Utente.class.getSimpleName())) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }
        Utente utente = (Utente) session.getAttribute("utente");
        if (!utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
        }

        String nomeCat = request.getParameter("nomeCategoria");

        if (nomeCat == null || nomeCat.isBlank()) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }

        Categoria c = new Categoria();
        c.setNome(nomeCat);
        CategoriaService cs = new CategoriaServiceImpl();
        cs.inserisciCategoria(c);
        return; //todo return
    }

}
