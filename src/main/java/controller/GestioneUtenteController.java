package controller;

import controller.utils.FileServlet;
import controller.utils.Validator;
import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.beans.proxies.UtenteProxy;
import model.services.UtenteService;
import model.services.UtenteServiceImpl;
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
import java.time.LocalDate;
import java.util.List;


@WebServlet(name = "GestioneUtenteController",
        value = "/GestioneUtenteController/*")
@MultipartConfig
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
                visualizzaDashboard(request, response);
                return;
            case "/visualizzaDashboardAdmin":
                visualizzaDashboardAdmin(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }
        return;
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        switch (path) {
            case "/modificaProfilo":
                modificaProfilo(request, response);
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
        return;
    }

    private void visualizzaDashboardAdmin(final HttpServletRequest request,
                                          final HttpServletResponse response)
            throws IOException, ServletException {
        Validator val = new Validator(request);
        if (!val.isValidBean(new Utente(),
                request.getSession().getAttribute("utente"))) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }
        Utente ut = (Utente) request.getSession().getAttribute("utente");
        if (!ut.isAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Non autorizzato.");
        }
        UtenteService us = new UtenteServiceImpl();
        request.setAttribute("utentiList", us.visualizzaUtenti(ut));

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/results/admin.jsp");
        dispatcher.forward(request, response);
    }

    private void modificaProfilo(final HttpServletRequest request,
                                 final HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        Validator val = new Validator(request);
        if (!val.isValidBean(new Utente(), session.getAttribute("utente"))) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }
        Utente utente = new Utente();
        if (request.getParameter("password").equals(
                request.getParameter("confermaPassword"))
                && request.getParameter("email").equals(
                request.getParameter("confermaEmail"))) {
            if (val.assertUtente()) {
                utente.setIdUtente(((Utente) session.getAttribute("utente"))
                        .getIdUtente());
                utente.createPasswordHash(request.getParameter("password"));
                utente.setEmail(request.getParameter("email"));
                utente.setNome(request.getParameter("nome"));
                utente.setCognome(request.getParameter("cognome"));
                utente.setDataDiNascita(
                        LocalDate.parse(request.getParameter("dataDiNascita")));
                utente.setTelefono(request.getParameter("telefono"));
                utente.setStrada(request.getParameter("indirizzo"));
                utente.setCitta(request.getParameter("citta"));
                utente.setCap(request.getParameter("cap"));
                utente.setCf(request.getParameter("cf"));
                List<String> listFoto = FileServlet.uploadFoto(request);
                if (!listFoto.isEmpty()) {
                    utente.setFotoProfilo(listFoto.get(0));
                } else {
                    Utente inSessione = (Utente) session.getAttribute("utente");
                    utente.setFotoProfilo(inSessione.getFotoProfilo());
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }
        UtenteService us = new UtenteServiceImpl();

        us.modificaProfilo(utente);
        session.setAttribute("utente", utente);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/results/profilo_utente.jsp");
        dispatcher.forward(request, response);
    }

    private void sospensioneUtente(final HttpServletRequest request,
                                   final HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();

        Validator val = new Validator(request);
        if (!val.isValidBean(new Utente(), session.getAttribute("utente"))) {
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

        Validator val = new Validator(request);
        if (!val.isValidBean(new Utente(), session.getAttribute("utente"))) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/AutenticazioneController/login");
            return;
        }

        UtenteService uts = new UtenteServiceImpl();
        Utente ut = uts.visualizzaDashboardUtente(
                ((Utente) session.getAttribute("utente")).getIdUtente());

        UtenteProxy utenteProxy = new UtenteProxy(ut);
        ut.setDonazioni(utenteProxy.getDonazioni());
        ut.setCampagne(utenteProxy.getCampagne());

        request.setAttribute("utente", ut);

        request.getRequestDispatcher("/WEB-INF/results/profilo_utente.jsp")
                .forward(request, response);
    }

    private void promuoviDeclassaUtente(final HttpServletRequest request,
                                        final HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (!new Validator(request).isValidBean(new Utente(),
                session.getAttribute("utente"))) {
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
        DAO dao = new UtenteDAO();
        Utente ut = null;

        if (parameter != null) {
            try {
                ut = (Utente) dao.getById(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Errore conversione");
                return;
            }

            new UtenteServiceImpl().promuoviDeclassaUtente(utente, ut);
            visualizzaDashboardAdmin(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore input");
        }
        return;
    }

    @Override
    public void destroy() {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }
}
