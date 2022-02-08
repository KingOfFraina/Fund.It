package controller;

import controller.utils.FileServlet;
import controller.utils.Validator;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.beans.proxies.CampagnaProxy;
import model.beans.proxies.DonazioneProxy;
import model.beans.proxies.UtenteProxy;
import model.beans.proxyInterfaces.CampagnaInterface;
import model.services.*;
import model.storage.ConPool;

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
    static UtenteService utenteService;

    public GestioneUtenteController(UtenteService service) {
        utenteService = service;
    }

    public GestioneUtenteController() {
        utenteService = new UtenteServiceImpl();
    }

    @Override
    public void doGet(final HttpServletRequest request,
                      final HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        switch (path) {
            case "/visualizzaDashboard" -> visualizzaDashboard(request, response);
            case "/visualizzaDashboardAdmin" -> visualizzaDashboardAdmin(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(final HttpServletRequest request,
                       final HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        switch (path) {
            case "/modificaProfilo" -> modificaProfilo(request, response);
            case "/promuoviDeclassaUtente" -> promuoviDeclassaUtente(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void visualizzaDashboardAdmin(final HttpServletRequest request,
                                          final HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utente userSession = (Utente) session.getAttribute("utente");
        if (!new Validator(request).isValidBean(Utente.class,
                userSession)) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/autenticazione/login");
        } else {
            if (!userSession.isAdmin()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Non autorizzato.");
            } else {
                SegnalazioniService segnalazioniService =
                        new SegnalazioniServiceImpl();
                DonazioniService donazioniService = new DonazioniServiceImpl();

                List<Donazione> list = donazioniService.visualizzaDonazioni();
                DonazioneProxy proxy = new DonazioneProxy(new Donazione());
                list.forEach(d -> {
                    proxy.setDonazione(d);
                    d.setUtente(proxy.getUtente());
                });

                CampagnaService cs = new CampagnaServiceImpl();
                List<Campagna> lst = cs.getAllCampagne();
                lst.forEach(c -> {
                    CampagnaInterface proxyCamp = new CampagnaProxy(c);
                    proxyCamp.getUtente();
                });

                request.setAttribute("campagneList", lst);
                request.setAttribute("utentiList", utenteService.visualizzaUtenti(userSession));
                request.setAttribute("segnalazioniList",
                        segnalazioniService.trovaSegnalazioni());
                request.setAttribute("donazioniList", list);

                request.getRequestDispatcher("/WEB-INF/results/admin.jsp")
                        .forward(request, response);
            }
        }
    }

    private void visualizzaDashboard(final HttpServletRequest request,
                                     final HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente userSession = (Utente) session.getAttribute("utente");

        if (!new Validator(request).isValidBean(Utente.class,
                userSession)) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/autenticazione/login");
        } else {
            Utente ut = utenteService.visualizzaDashboardUtente(
                    userSession.getIdUtente());

            UtenteProxy utenteProxy = new UtenteProxy(ut);
            ut.setDonazioni(utenteProxy.getDonazioni());
            ut.setCampagne(utenteProxy.getCampagne());

            request.setAttribute("utente", ut);

            request.getRequestDispatcher("/WEB-INF/results/profilo_utente.jsp")
                    .forward(request, response);
        }
    }

    private void modificaProfilo(final HttpServletRequest request,
                                 final HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utente userSession = (Utente) session.getAttribute("utente");
        Utente utente = new Utente();
        if (!new Validator(request).isValidBean(Utente.class, userSession)) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/autenticazione/login");
        } else {
            if (request.getParameter("password").equals(
                    request.getParameter("confermaPassword"))
                    && request.getParameter("email").equals(
                    request.getParameter("confermaEmail"))) {
                if (new Validator(request).assertUtente()) {
                    utente.setIdUtente(userSession
                            .getIdUtente());
                    utente.createPasswordHash(request.getParameter("password"));
                    utente.setEmail(request.getParameter("email"));
                    utente.setNome(request.getParameter("nome"));
                    utente.setCognome(request.getParameter("cognome"));
                    utente.setDataDiNascita(
                            LocalDate.parse(
                                    request.getParameter("dataDiNascita")));
                    utente.setTelefono(request.getParameter("telefono"));
                    utente.setStrada(request.getParameter("indirizzo"));
                    utente.setCitta(request.getParameter("citta"));
                    utente.setCap(request.getParameter("cap"));
                    utente.setCf(request.getParameter("cf"));
                    utente.setAdmin(userSession.isAdmin());
                    List<String> listFoto = FileServlet.uploadFoto(request);

                    if (!listFoto.isEmpty()) {
                        utente.setFotoProfilo(listFoto.get(0));
                    } else {
                        utente.setFotoProfilo(userSession.getFotoProfilo());
                    }

                    if (utenteService.modificaProfilo(utente)) {
                        ReportService.creaReport(request, TipoReport.INFO,
                                "Esito operazione:",
                                "Modifica effettuata con successo");
                    } else {
                        ReportService.creaReport(request, TipoReport.ERRORE,
                                "Esito operazione:",
                                "Modifica non effettuata con successo");
                    }

                    session.setAttribute("utente", utente);
                    request.getRequestDispatcher(
                                    "/WEB-INF/results/profilo_utente.jsp")
                            .forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void promuoviDeclassaUtente(final HttpServletRequest request,
                                        final HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        Utente userSession = (Utente) session.getAttribute("utente");
        if (!new Validator(request).isValidBean(Utente.class,
                userSession)) {
            response.sendRedirect(request.getServletContext().getContextPath()
                    + "/autenticazione/login");
        } else {
            if (!userSession.isAdmin()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Non Autorizzato");
            } else {
                String idUtente = request.getParameter("utentemod");
                Utente ut;
                if (idUtente != null) {
                    try {
                        ut = utenteService
                                .visualizzaDashboardUtente(
                                        Integer.parseInt(idUtente));
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse
                                .SC_INTERNAL_SERVER_ERROR);
                        return;
                    }

                    if (utenteService.promuoviDeclassaUtente(userSession, ut)) {
                        ReportService.creaReport(request, TipoReport.INFO,
                                "Esito operazione:",
                                "Modifica effettuata con successo");
                    } else {
                        ReportService.creaReport(request, TipoReport.ERRORE,
                                "Esito operazione:",
                                "Modifica non effettuata con successo");
                    }
                    visualizzaDashboardAdmin(request, response);
                } else {
                    response.sendError(HttpServletResponse.
                            SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    @Override
    public void destroy() {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }
}
