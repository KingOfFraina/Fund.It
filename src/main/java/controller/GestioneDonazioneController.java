package controller;

import model.DAO.CampagnaDAO;
import model.DAO.DonazioneDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import model.services.DonazioniService;
import model.services.DonazioniServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@WebServlet(name = "GestioneDonazioneController",
        value = "/donazione/*")
public final class GestioneDonazioneController extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        String resource = "/WEB-INF/results/visualizzaDonazioni.jsp";
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("utente") != null) {

            switch (request.getPathInfo()) {
                case "/scriviCommento":
                    if (session.getAttribute("donazione") != null) {
                        request.getRequestDispatcher(
                                        "/WEB-INF/results/"
                                                + "commentoDonazione.jsp")
                                .forward(request, response);
                        return;
                    }
                default:
                    request.setAttribute("donazioniList",
                            new DonazioniServiceImpl(new DonazioneDAO())
                                    .visualizzaDonazioni(
                                            (Utente) session
                                                    .getAttribute("utente")));
            }

        } else {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/AutenticazioneController/login");
            return;
        }

        request.getRequestDispatcher(resource).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException, ServletException {

        CampagnaService campagnaService =
                new CampagnaServiceImpl(new CampagnaDAO());
        System.out.println("Inizializzazione campagnaService");
        int id = 0;

        id = Integer.parseInt(request.getParameter("idCampagna"));

        Campagna campagna = campagnaService
                .trovaCampagna(id);

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        switch (path) {
            case "/registraDonazione" -> {
                HttpSession session = request.getSession();
                if (session != null && session.getAttribute("utente") != null) {
                    Donazione donazione = new Donazione();
                    donazione.setCampagna(campagna);
                    donazione.setUtente((Utente)
                            session.getAttribute("utente"));
                    donazione.setSommaDonata(Double.parseDouble(
                            request.getParameter("sommaDonata")));
                    donazione.setAnonimo(false);
                    donazione.setCommento("");
                    donazione.setRicevuta(request.getParameter("ricevuta"));
                    donazione.setDataOra(LocalDateTime.now());

                    session.setAttribute("donazione", donazione);

                    request.setAttribute("idCampagna", id);
                    request.getRequestDispatcher(
                                    "/WEB-INF/results/commentoDonazione.jsp")
                            .forward(request, response);
                    return;
                }
            }
            case "/scriviCommento" -> {
                if (request.getSession(false) != null) {
                    System.out.println("Entrata branch if");
                    Donazione donazione = (Donazione) request.getSession(false)
                            .getAttribute("donazione");

                    if (donazione != null) {
                        donazione.setCommento(request.getParameter("commento"));
                        if (request.getParameter("anonimo") != null) {
                            donazione.setAnonimo(true);
                        }

                        DonazioniService donazioniService =
                                new DonazioniServiceImpl(new DonazioneDAO());

                        if (donazioniService.effettuaDonazione(donazione)) {
                            campagna.setSommaRaccolta(
                                    campagna.getSommaRaccolta()
                                            + donazione.getSommaDonata());
                            campagnaService.modificaCampagna(campagna);
                        } else {
                            System.out.println("errore");
                        }
                    }
                }
            }
            default -> {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
            }
        }

        response.sendRedirect(
                getServletContext().getContextPath()
                        + "/");
    }
}
