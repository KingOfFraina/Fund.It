package controller;

import controller.utils.Validator;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;
import model.beans.proxies.CampagnaProxy;
import model.beans.proxyInterfaces.CampagnaInterface;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import model.services.SegnalazioniService;
import model.services.SegnalazioniServiceImpl;
import model.services.UtenteService;
import model.services.ReportService;
import model.services.UtenteServiceImpl;
import model.services.TipoReport;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "GestioneSegnalazioneController",
        value = "/segnalazioni/*")
public final class GestioneSegnalazioneController extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response) throws IOException {
        Utente userSession = (Utente) request.getSession().getAttribute("utente");
        if (userSession.isAdmin()) {
            SegnalazioniService service = new SegnalazioniServiceImpl();
            List<Segnalazione> segnalazioni = service.trovaSegnalazioni();
            request.setAttribute("segnalazioni", segnalazioni);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException {

        String path = request.getPathInfo();
        HttpSession session = request.getSession();

        if (!new Validator(request)
                .isValidBean(Utente.class, session.getAttribute("utente"))) {

            response.sendRedirect(getServletContext().getContextPath()
                    + "/autenticazione/login");
            return;
        }

        Utente userSession = (Utente) session.getAttribute("utente");
        String idCampagna = request.getParameter("idCampagna");
        CampagnaService campagnaService =
                new CampagnaServiceImpl();
        SegnalazioniService segnalazioniService = new SegnalazioniServiceImpl();
        UtenteService utenteService = new UtenteServiceImpl(new UtenteDAO());

        switch (path) {
            case "/segnala" -> {
                Segnalazione segnalazione = new Segnalazione();
                String descrizione = request.getParameter("descrizione");
                Campagna c = campagnaService.
                        trovaCampagna(Integer.parseInt(idCampagna));
                Utente utente = new Utente();
                utente.setIdUtente(
                        Integer.parseInt(request.getParameter("idUtente")));

                segnalazione.setCampagnaSegnalata(c);
                segnalazione.setSegnalato(utente);
                segnalazione.setSegnalatore(userSession);
                segnalazione.setDescrizione(descrizione);
                segnalazione.setDataOra(LocalDateTime.now());
                segnalazione.setStatoSegnalazione(StatoSegnalazione.ATTIVA);

                if (segnalazioniService.segnalaCampagna(segnalazione)) {
                    ReportService.creaReport(request, TipoReport.INFO,
                            "Esito operazione:", "Segnalazione inviata");
                } else {
                    ReportService.creaReport(request, TipoReport.ERRORE,
                            "Esito operazione:", "Segnalazione non inviata");
                }

                response.sendRedirect(request.getServletContext().getContextPath()
                        + "/campagna/campagna?idCampagna=" + idCampagna);

            }
            case "/risolvi" -> {
                if (!userSession.isAdmin()) {
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Non autorizzato");
                } else {
                    String scelta = request.getParameter("sceltaSegnalazione");
                    int id = Integer.parseInt(request.getParameter("idCampagna"));
                    int idSegnalazione =
                            Integer.parseInt(
                                    request.getParameter("idSegnalazione"));
                    Campagna campagna = campagnaService.trovaCampagna(id);
                    if (scelta.equals("Risolvi")) {
                        segnalazioniService
                                .risolviSegnalazione(idSegnalazione,
                                        StatoSegnalazione.RISOLTA);
                        Utente utenteSegnalato =
                                utenteService.visualizzaDashboardUtente(
                                        campagna.getUtente().getIdUtente());
                        campagnaService.cancellaCampagna(campagna);
                        utenteService.sospensioneUtente(utenteSegnalato);
                        CampagnaInterface campagnaProxy =
                                new CampagnaProxy(campagna);
                        if (campagnaService
                                .rimborsaDonazioni(campagna, campagnaProxy)) {
                            ReportService.creaReport(request, TipoReport.INFO,
                                    "Esito operazione:", "Segnalazione risolta");
                        } else {
                            ReportService.creaReport(request, TipoReport.ERRORE,
                                    "Esito operazione:", "Segnalazione non risolta");
                        }
                    } else {
                        if (segnalazioniService
                                .risolviSegnalazione(idSegnalazione,
                                        StatoSegnalazione.ARCHIVIATA)) {
                            ReportService.creaReport(request, TipoReport.INFO,
                                    "Esito operazione:", "Segnalazione archiviata");
                        } else {
                            ReportService.creaReport(request, TipoReport.ERRORE,
                                    "Esito operazione:", "Segnalazione non archviata");
                        }
                    }
                    response.sendRedirect(request
                            .getServletContext().getContextPath()
                            + "/GestioneUtenteController/visualizzaDashboard");
                }
            }
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
