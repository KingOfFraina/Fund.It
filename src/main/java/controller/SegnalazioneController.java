package controller;


import model.DAO.CampagnaDAO;
import model.beans.*;
import model.beans.proxies.CampagnaProxy;
import model.beans.proxies.DonazioneProxy;
import model.beans.proxyInterfaces.CampagnaInterface;
import model.beans.proxyInterfaces.DonazioneInterface;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import model.services.SegnalazioniService;
import model.services.SegnalazioniServiceImpl;
import model.services.UtenteService;
import model.services.UtenteServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "SegnalazioneController",
        value = "/segnalazioni/*")
public final class SegnalazioneController extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {


        String path = request.getPathInfo();

        HttpSession session = request.getSession();
        String idSegnalazione = request.getParameter("idSegnalazione");
        int id = 0;
        try {
            id = Integer.parseInt(idSegnalazione);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        SegnalazioniService service = new SegnalazioniServiceImpl();


        switch (path) {
            case "/get":
                Segnalazione s = service.trovaSegnalazione(id);
                request.setAttribute("obj", s);
                break;
            case "/getAll":
                List<Segnalazione> segnalazioni = service.trovaSegnalazioni();
                request.setAttribute("segnalazioni", segnalazioni);
                break;
            default:
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException {

        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        String idCampagna = request.getParameter("idCampagna");
        CampagnaService campagnaService = new CampagnaServiceImpl(new CampagnaDAO());
        SegnalazioniService segnalazioniService = new SegnalazioniServiceImpl();

        String descrizione = request.getParameter("descrizione");
        String resource = "/";

        switch (path) {
            case "/segnala":
                Campagna c = campagnaService.
                        trovaCampagna(Integer.parseInt(idCampagna));
                Utente utente = new Utente();
                System.out.println(request.getParameter("idUtente"));
                utente.setIdUtente(
                        Integer.parseInt(request.getParameter("idUtente")));
                if (segnalazioniService.
                        segnalaCampagna(c, utente, descrizione)) {
                    response.sendRedirect(
                            getServletContext().getContextPath()
                                    + "/index.jsp");
                    return;
                }
                break;
            case "/risolvi":
                String scelta = request.getParameter("sceltaSegnalazione");
                int id = Integer.parseInt(request.getParameter("idCampagna"));
                int idSegnalazione =
                        Integer.parseInt(
                                request.getParameter("idSegnalazione"));

                if (scelta.equals("Risolta")) {
                    segnalazioniService
                            .risolviSegnalazione(idSegnalazione,
                                    StatoSegnalazione.RISOLTA);
                    CampagnaService campagnaService1 =
                            new CampagnaServiceImpl(new CampagnaDAO());
                    UtenteService utenteService = new UtenteServiceImpl();
                    Campagna c2 = campagnaService1.trovaCampagna(id);
                    Utente utenteSegnalato =
                            utenteService.visualizzaDashboardUtente(
                                    c2.getUtente().getIdUtente());

                    campagnaService1.cancellaCampagna(c2);

                    utenteService.sospensioneUtente(utenteSegnalato);
                    CampagnaInterface campagnaProxy = new CampagnaProxy(c2);
                    List<Donazione> donazioni = campagnaProxy.getDonazioni();
                    donazioni.forEach(d -> d.setSommaDonata(-d.getSommaDonata()));
                }

                break;
            default:
                break;
        }
    }
}
