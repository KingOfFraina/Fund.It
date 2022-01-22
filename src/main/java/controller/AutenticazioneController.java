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

@WebServlet(name = "AutenticazioneController",
        value = "/AutenticazioneController/*")
public final class AutenticazioneController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        String resource = "/";


        if (path.equals("/login")) {
            resource = "/login.jsp";
        } else if (path.equals("/registrazione")) {
            resource = "/registrazione.jsp";
        } else {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
            return;
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/results" + resource);
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
            default:
                System.out.println("default clause");
                break;
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
}
