package controller;

import controller.utils.FileServlet;
import controller.utils.Validator;
import model.DAO.CampagnaDAO;
import model.DAO.CategoriaDAO;
import model.DAO.ImmagineDAO;
import model.DAO.DAO;
import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.Donazione;
import model.beans.Immagine;
import model.beans.StatoCampagna;
import model.beans.Utente;
import model.beans.proxies.CampagnaProxy;
import model.beans.proxies.DonazioneProxy;
import model.beans.proxyInterfaces.CampagnaInterface;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import model.services.CategoriaService;
import model.services.CategoriaServiceImpl;
import model.services.ImmagineService;
import model.services.ImmagineServiceImpl;
import model.storage.ConPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GestioneCampagnaController",
        value = "/campagna/*",
        loadOnStartup = 0)
@MultipartConfig
public final class GestioneCampagnaController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        List<Campagna> campagne;
        DAO<Campagna> campagnaDAO = new CampagnaDAO();
        campagne = campagnaDAO.getAll();

        if (campagne != null) {
            campagne.forEach(c -> {
                CampagnaInterface proxy = new CampagnaProxy(c);
                proxy.getUtente();
                proxy.getImmagini();
            });
        }


        getServletContext().setAttribute("campagneList", campagne);
    }

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        String resource = "/";
        HttpSession session = request.getSession();
        CampagnaService service = new CampagnaServiceImpl(new CampagnaDAO());
        CategoriaService categoriaService =
                new CategoriaServiceImpl(new CategoriaDAO());



        switch (request.getPathInfo()) {
            case "/main" -> resource = "/WEB-INF/results/main_page.jsp";
            case "/creaCampagna" -> {
                if (new Validator(request).isValidBean(Utente.class,
                        session.getAttribute("utente"))) {
                    response.sendRedirect(
                            getServletContext().getContextPath()
                                    + "/autenticazione/login");
                    return;
                } else {
                    request.setAttribute("categorie",
                            categoriaService.visualizzaCategorie());
                    resource = "/WEB-INF/results/form_campagna.jsp";
                }
            }
            case "/modificaCampagna" -> {
                visualizzaModificaCampagna(request, response);
                return;
            }
            case "/campagna" -> {
                String id = request.getParameter("idCampagna");
                Campagna c = service.trovaCampagna(Integer.parseInt(id));
                if (c == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "Campagna non trovata");
                    return;
                } else {
                    CampagnaInterface proxy = new CampagnaProxy(c);
                    c.setUtente(proxy.getUtente());
                    DonazioneProxy proxy2 = new DonazioneProxy();
                    c.setImmagini(proxy.getImmagini());
                    List<Donazione> donazioni = proxy.getDonazioni();
                    donazioni.forEach(d -> {
                        proxy2.setDonazione(d);
                        d.setUtente(proxy2.getUtente());
                    });
                    c.setDonazioni(proxy.getDonazioni());
                    request.setAttribute("campagna", c);
                    condividiCampagna(request, response, c.getIdCampagna());
                    resource = "/WEB-INF/results/campagna.jsp";
                }
            }
            case "/ricerca" -> {
                String searchText = request.getParameter("searchText");
                searchText = searchText.trim();
                List<Campagna> campagne = service.ricercaCampagna(searchText);
                if (campagne.size() > 0 && !searchText.isBlank()) {
                    for (Campagna campagna : campagne) {
                        new CampagnaProxy(campagna).getImmagini();
                    }
                    request.setAttribute("campagneList", campagne);
                    resource = "/WEB-INF/results/campagne.jsp";
                } else {
                    request.setAttribute("errorSearch",
                            "Nessun risultato trovato");
                    resource = "/WEB-INF/results/campagne.jsp";
                }
            }
            default -> {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
            }
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(resource);
        dispatcher.forward(request, response);
    }

    private void condividiCampagna(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final int idCampagna) throws IOException {
        Map<String, String> map =
                new CampagnaServiceImpl(new CampagnaDAO())
                        .condividiCampagna(idCampagna, request);

        if (map != null) {
            request.setAttribute("linkList", map);
        } else {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/index.jsp");
        }
        return;
    }

    private void visualizzaModificaCampagna(final HttpServletRequest request,
                                            final HttpServletResponse response)
            throws ServletException, IOException {
        CampagnaService service = new CampagnaServiceImpl(new CampagnaDAO());
        CategoriaService categoriaService =
                new CategoriaServiceImpl(new CategoriaDAO());
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/autenticazione/login");
            return;
        }

        Utente ut = (Utente) request.getSession().getAttribute("utente");
        String idCampagna = request.getParameter("idCampagna");
        int id;
        try {
            id = Integer.parseInt(idCampagna);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            e.printStackTrace();
            return;
        }
        Campagna c = service.trovaCampagna(id);
        if (c.getUtente().getIdUtente() != ut.getIdUtente()) {
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

        HttpSession session = request.getSession();
        CampagnaService service = new CampagnaServiceImpl(new CampagnaDAO());
        Utente userSession = (Utente) session.getAttribute("utente");
        String idCampagna = request.getParameter("idCampagna");
        int id = 0;
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();

        if (userSession == null) {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/autenticazione/login");

            return;
        }

        switch (path) {
            case "/creaCampagna":
                creaCampagna(request, response);
                break;
            case "/modificaCampagna":
                if (idCampagna != null) {
                    modificaCampagna(request, response, service
                            .trovaCampagna(Integer.parseInt(idCampagna)));
                }

                break;
            case "/cancellaCampagna":
                id = Integer.parseInt(idCampagna);
                Campagna campagna = service.trovaCampagna(id);
                if (service.cancellaCampagna(campagna)) {
                    if (service.rimborsaDonazioni(campagna,
                            new CampagnaProxy(campagna))) {
                        System.out.println("rimborso ok");
                    } else {
                        System.out.println("rimborso errore");
                    }
                    System.out.println("cancellazione Ok");
                } else {
                    System.out.println("cancellazione errore");
                }
                break;
            case "/chiudiCampagna":
                id = Integer.parseInt(idCampagna);
                Campagna campagna1 = service.trovaCampagna(id);
                if (service.chiudiCampagna(campagna1)) {
                    response.sendRedirect(getServletContext().getContextPath()
                            + "/GestioneUtenteController/visualizzaDashboard");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                return;
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
            throws IOException, ServletException {

        Campagna c = extractCampagna(req);
        c.setSommaRaccolta(0d);
        c.setUtente((Utente) req.getSession().getAttribute("utente"));

        if (new CampagnaServiceImpl(new CampagnaDAO()).creazioneCampagna(c)) {
            uploadFoto(req, c);
            res.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
        } else {
            res.sendRedirect(
                    getServletContext().getContextPath()
                            + "/campagna/creaCampagna");
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
                new CategoriaServiceImpl(new CategoriaDAO())
                        .visualizzaCategoria(categoria));

        return c;
    }

    private void uploadFoto(final HttpServletRequest request,
                            final Campagna campagna) throws ServletException,
            IOException {
        List<String> fotoList = FileServlet.uploadFoto(request);
        ImmagineService immagineService =
                new ImmagineServiceImpl(new ImmagineDAO());
        Immagine immagine = new Immagine();
        immagine.setCampagna(campagna);

        if (!fotoList.isEmpty()) {
            immagineService.eliminaImmaginiCampagna(campagna.getIdCampagna());
        }

        for (String fotoPath : fotoList) {
            immagine.setPath(fotoPath);
            immagineService.salvaImmagine(immagine);
        }
    }

    private void modificaCampagna(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final Campagna campagna)
            throws IOException, ServletException {

        Campagna c = extractCampagna(request);

        c.setIdCampagna(campagna.getIdCampagna());
        c.setSommaRaccolta(campagna.getSommaRaccolta());

        if (new CampagnaServiceImpl(new CampagnaDAO()).modificaCampagna(c)) {
            uploadFoto(request, c);
            response.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
        } else {
            request.getRequestDispatcher("/campagna"
                    + "/modificaCampagna").forward(request, response);
        }
    }
}
