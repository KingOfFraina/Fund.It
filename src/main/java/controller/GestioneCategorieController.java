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
        value = "/GestioneCategorieController/*", loadOnStartup = 0)
public final class GestioneCategorieController extends HttpServlet {

    @Override
    public void init() {
        CategoriaService cs = new CategoriaServiceImpl();

        this.getServletContext().setAttribute("categorieList",
                cs.visualizzaCategorie());

    }

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        String resource = "/";

        switch (path) {
            case "/inserisciCategoria":
                resource = "/WEB-INF/results/ddfgf";
                break;
            case "/modificaCategoria":
                resource = "/WEB-INF/results/dd";
                break;
            case "/visualizzaCategorie":
                resource = "/WEB-INF/results/ffwe";
                visualizzaCategorie(request);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(resource);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();
        HttpSession session = request.getSession();

        switch (path) {
            case "/inserisciCategoria":
                inserisciCategoria(request, response, session);
                break;
            case "/modificaCategoria":
                modificaCategoria(request, response, session);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                break;
        }
    }

    private void visualizzaCategorie(final HttpServletRequest request) {
        CategoriaService cs = new CategoriaServiceImpl();

        request.setAttribute("categorieList", cs.visualizzaCategorie());
    }

    private void modificaCategoria(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final HttpSession session)
            throws IOException {

        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }

        if (!utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
        }

        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        String nomeCategoria = request.getParameter("nomeCategoria");
        CategoriaService service = new CategoriaServiceImpl();
        Categoria c = new Categoria();
        c.setIdCategoria(idCategoria);
        c = service.visualizzaCategoria(c);

        c.setNome(nomeCategoria);
        if (service.modificaCategoria(c)) {
            System.out.println("ok");
        } else {
            System.out.println("errore");
        }
    }

    private void inserisciCategoria(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final HttpSession session)
            throws IOException {

        Utente utente = (Utente) session.getAttribute("utente");

        if (session.getAttribute("utente") == null) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }

        if (!utente.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
        }

        String nomeCategoria = request.getParameter("nomeCategoria");

        if (nomeCategoria == null || nomeCategoria.isBlank()) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }

        Categoria c = new Categoria();
        c.setNome(nomeCategoria);
        CategoriaService cs = new CategoriaServiceImpl();
        if (cs.inserisciCategoria(c)) {
            System.out.println("ok");
        } else {
            System.out.println("errore");
        }
    }
}
