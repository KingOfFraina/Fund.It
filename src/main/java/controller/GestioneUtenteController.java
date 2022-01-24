package controller;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.services.UtenteService;
import model.services.UtenteServiceImpl;
import model.storage.ConPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "GestioneUtenteController",
        value = "/GestioneUtenteController/*")
public final class GestioneUtenteController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        String resource = "/";

        switch (path) {
            case "/visualizzaDashboard":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/modificaProfilo":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/visualizzaUtenti":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/promuoviDeclassaUtente":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/sospensioneUtente":
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
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        switch (path) {
            case "/visualizzaDashboard":
                visualizzaDashboard(request, response);
                break;
            case "/modificaProfilo":
                //todo meth
                break;
            case "/visualizzaUtenti":
                visualizzaUtenti(request, response);
                break;
            case "/promuoviDeclassaUtente":
                promuoviDeclassaUtente(request, response);
                break;
            case "/sospensioneUtente":
                sospensioneUtente(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }

    }

    private void sospensioneUtente(final HttpServletRequest request,
                                   final HttpServletResponse response)
            throws ServletException, IOException {
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
        String parameter = request.getParameter("utenteban");
        int idUtenteban = -1;
        if (parameter != null) {
            try {
                idUtenteban = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                e.printStackTrace(); //todo
                return;
            }
        }

        DAO dao = new UtenteDAO();
        Utente banned = (Utente) dao.getById(idUtenteban);
        UtenteService us = new UtenteServiceImpl();
        us.sospensioneUtente(banned);
        //todo return!!
    }

    private void visualizzaDashboard(final HttpServletRequest request,
                                     final HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("utente") == null
                || !session.getAttribute("utente").getClass().getSimpleName().
                equals(Utente.class.getSimpleName())) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }

        UtenteService uts = new UtenteServiceImpl();
        Utente ut = uts.visualizzaDashboardUtente(
                ((Utente) session.getAttribute("utente")).getIdUtente());

        /*UtenteInterface ui = new UtenteProxy(ut);
        ut.setDonazioni(ui.getDonazioni());
        ut.setCampagne(ui.getCampagne());
        ut.setSegnalazioni(ui.getSegnalazioni()); todo elimina, chiamata a
                                                    rispettivi controller da parte della jsp*/
        request.setAttribute("utente", ut);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/results/dati_utente.jsp");
        dispatcher.forward(request, response);
        return;
    }

    private void visualizzaUtenti(final HttpServletRequest request,
                                  final HttpServletResponse response)
            throws ServletException, IOException {
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
        UtenteService us = new UtenteServiceImpl();
        request.setAttribute("utentiList", us.visualizzaUtenti(utente));
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/results/dati_utente.jsp"); //todo path
        dispatcher.forward(request, response);
    }

    private void promuoviDeclassaUtente(final HttpServletRequest request,
                                        final HttpServletResponse response)
            throws ServletException, IOException {
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
        String parameter = request.getParameter("utentemod");
        int idUtenteSwitched = -1;
        if (parameter != null) {
            try {
                idUtenteSwitched = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                e.printStackTrace(); //todo
                return;
            }
        }

        DAO dao = new UtenteDAO();
        Utente switched = (Utente) dao.getById(idUtenteSwitched);
        UtenteService us = new UtenteServiceImpl();
        us.promuoviDeclassaUtente(utente, switched);
        //todo return con ajax!!
    }
    @Override
    public void destroy() {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }
}
