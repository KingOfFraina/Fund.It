package controller;

import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.StatoCampagna;
import model.beans.Utente;
import model.beans.proxies.CampagnaProxy;
import model.beans.proxyInterfaces.CampagnaInterface;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import model.services.CategoriaService;
import model.services.CategoriaServiceImpl;
import model.storage.ConPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneCampagnaController",
        value = "/GestioneCampagnaController/*")
public final class GestioneCampagnaController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        String resource = "/";
        HttpSession session;
        CampagnaService service = new CampagnaServiceImpl();
        CategoriaService categoriaService = new CategoriaServiceImpl();

        session = request.getSession();

        if (session.getAttribute("utente") == null
                || !session.getAttribute("utente").getClass().getSimpleName().
                equals(Utente.class.getSimpleName())) {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/AutenticazioneController/login");
            return;
        }

        switch (request.getPathInfo()) {
            case "/creaCampagna":
                request.setAttribute("categorie",
                        categoriaService.visualizzaCategorie());
                resource = "/WEB-INF/results/form_campagna.jsp";
                break;
            case "/modificaCampagna":
                visualizzaModificaCampagna(request, response);
                break;
            case "/campagna":
                visualizzaCampagna(request, response);
                break;
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
    private void visualizzaCampagna(final HttpServletRequest request,
                                    final HttpServletResponse response)
            throws ServletException, IOException {
        CampagnaService service = new CampagnaServiceImpl();
        String id = request.getParameter("idCampagna");
        Campagna c = service.trovaCampagna(Integer.parseInt(id));
        CampagnaInterface proxy = new CampagnaProxy(c);
        c.setUtente(proxy.getUtente());
        c.setDonazioni(proxy.getDonazioni());
        request.setAttribute("campagna", c);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/results/campagna.jsp");
        dispatcher.forward(request, response);
        return;
    }
    private void visualizzaModificaCampagna(final HttpServletRequest request,
                                            final HttpServletResponse response)
            throws ServletException, IOException {
        CampagnaService service = new CampagnaServiceImpl();
        CategoriaService categoriaService = new CategoriaServiceImpl();
        Utente ut = (Utente) request.getSession().getAttribute("utente");
        String idCampagna = request.getParameter("idCampagna");
        int id = 0;
        try {
            id = Integer.parseInt(idCampagna);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            e.printStackTrace();
            return;
        }
        Campagna c = service.trovaCampagna(id);
        if (c.getUtente().getIdUtente() != id) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non Autorizzato");
            return;
        }
        request.setAttribute("campagna", c);
        request.setAttribute("categorie",
                categoriaService.visualizzaCategorie());
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/results/form_campagna.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        CampagnaService service = new CampagnaServiceImpl();

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

            case "/modificaCampagna":
                String idCampagna = request.getParameter("idCampagna");

                if (idCampagna != null) {
                    modificaCampagna(request, response, service
                            .trovaCampagna(Integer.parseInt(idCampagna)));
                }

                break;
            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                break;
        }
    }

    @Override
    public void destroy() {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }

    private void creaCampagna(final HttpServletRequest req,
                              final HttpServletResponse res)
            throws IOException {

        Campagna c = extractCampagna(req);
        c.setSommaRaccolta(0d);

        if (new CampagnaServiceImpl().creazioneCampagna(c)) {
            res.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
        } else {
            res.sendRedirect(
                    getServletContext().getContextPath()
                            + "/GestioneCampagnaController/creaCampagna");
        }
    }

    private Campagna extractCampagna(final HttpServletRequest request) {
        Campagna c = new Campagna();

        c.setStato(StatoCampagna.ATTIVA);
        c.setTitolo(request.getParameter("titolo"));
        c.setDescrizione(request.getParameter("descrizione"));

        c.setSommaTarget(
                Double.parseDouble(request.getParameter("sommaTarget")));
        c.setUtente((Utente) request.getSession(false).getAttribute("utente"));

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(Integer.parseInt(
                request.getParameter("idCategoria")));

        c.setCategoria(
                new CategoriaServiceImpl().visualizzaCategoria(categoria));

        return c;
    }

    private void modificaCampagna(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final Campagna campagna)
            throws IOException, ServletException {

        Campagna c = extractCampagna(request);

        c.setIdCampagna(campagna.getIdCampagna());
        c.setSommaRaccolta(campagna.getSommaRaccolta());

        if (new CampagnaServiceImpl().modificaCampagna(c)) {
            response.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
        } else {
            request.getRequestDispatcher("/GestioneCampagnaController"
                    + "/modificaCampagna").forward(request, response);
        }
    }
}
