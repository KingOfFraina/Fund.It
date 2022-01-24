package controller;


import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.Utente;
import model.services.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

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
            throws ServletException, IOException {

        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        String idCampagna = request.getParameter("idCampagna");
        CampagnaService campagnaService = new CampagnaServiceImpl();
        SegnalazioniService segnalazioniService = new SegnalazioniServiceImpl();
        String descrizione = request.getParameter("descrizione");
        String resource = "/";

        switch (path) {
            case "/segnala":
                Campagna c = campagnaService.trovaCampagna(Integer.parseInt(idCampagna));
                Utente utente = new Utente();
                utente.setIdUtente(2);
                if (segnalazioniService.segnalaCampagna(c, utente, descrizione)) {
                    response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
                    return;
                } else {
                    System.out.println("segnalaCampagna returned false");
                }
                break;
            default:
                System.out.println("wtf");
                break;
        }


    }
}
