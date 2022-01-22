package controller;

import model.beans.Utente;

import model.services.AutenticazioneServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;


@WebServlet(name = "AutenticazioneController",
        value = "/AutenticazioneController/*")
public final class AutenticazioneController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        String resource = "/";


        if (session.getAttribute("utente") != null) {
            response.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
            return;
        }

        switch (path) {
            case "/login":
                resource = "/WEB-INF/results/login.jsp";
                break;
            case "/registrazione":
                resource = "/WEB-INF/results/registrazione.jsp";
                break;
            case "/logout":
                session.invalidate();
                response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
                return;
            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
                return;
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(resource);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getPathInfo()) {
            case "/login":
                login(request, response);
                break;
            case "/registrazione":
                registrazione(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }
    }

    private void login(final HttpServletRequest request,
                       final HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(true);
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            utente = new Utente();
            utente.setEmail(request.getParameter("email"));
            utente.createPasswordHash(request.getParameter("password"));

            AutenticazioneServiceImpl autenticazioneService =
                    new AutenticazioneServiceImpl(request.getSession(true));
            utente = autenticazioneService.login(utente);
        }

        if (utente != null) {
            session.setAttribute("utente", utente);
        } else {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/AutenticazioneController/login");
        }
    }

    private void registrazione(final HttpServletRequest request,
                               final HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("utente") == null) {
            Utente utente = new Utente();

            if (request.getParameter("password").equals(
                    request.getParameter("confermaPassword"))
                    && request.getParameter("email").equals(
                    request.getParameter("confermaEmail"))) {
                utente.createPasswordHash(request.getParameter("password"));
                utente.setEmail(request.getParameter("email"));
                utente.setNome(request.getParameter("nome"));
                utente.setCognome(request.getParameter("cognome"));
                utente.setDataDiNascita(
                        Date.valueOf(request.getParameter("dataDiNascita")));
                utente.setTelefono(request.getParameter("telefono"));
                utente.setStrada(request.getParameter("indirizzo"));
                utente.setCitta(request.getParameter("citta"));
                utente.setCap(request.getParameter("cap"));
                utente.setCf(request.getParameter("cf"));
                utente.setFotoProfilo(request.getParameter("fotoProfilo"));

                AutenticazioneServiceImpl autenticazioneService =
                        new AutenticazioneServiceImpl(request.getSession(true));

                System.out.println(autenticazioneService.registrazione(utente));
                session.setAttribute("utente", utente);
            } else {
                response.sendRedirect(
                        getServletContext().getContextPath()
                                + "/AutenticazioneController/registrazione");
            }

        } else {
            response.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
        }
    }
}
