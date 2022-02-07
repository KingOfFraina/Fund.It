package controller;

import controller.utils.FileServlet;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.services.AutenticazioneService;
import model.services.AutenticazioneServiceImpl;
import model.services.ReportService;
import model.services.TipoReport;

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


@WebServlet(name = "AutenticazioneController",
        value = "/AutenticazioneController/*")
@MultipartConfig
public final class AutenticazioneController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        String resource = "/";

        if (session.getAttribute("utente") != null && !path.equals("/logout")) {
            response.sendRedirect(
                    getServletContext().getContextPath() + "/index.jsp");
            return;
        }

        Utente userSession = (Utente) session.getAttribute("utente");
        switch (path) {
            case "/login":
                resource = "/WEB-INF/results/login.jsp";
                break;
            case "/registrazione":
                resource = "/WEB-INF/results/registrazione.jsp";
                break;
            case "/logout":
                AutenticazioneService service =
                        new AutenticazioneServiceImpl(session, new UtenteDAO());
                service.logout();
                response.sendRedirect(getServletContext().getContextPath()
                        + "/index.jsp");

                return;
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
                break;
        }
    }

    private void login(final HttpServletRequest request,
                       final HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            utente = new Utente();
            utente.setEmail(request.getParameter("email"));
            utente.createPasswordHash(request.getParameter("password"));

            AutenticazioneService autenticazioneService =
                    new AutenticazioneServiceImpl(
                            request.getSession(true), new UtenteDAO());
            utente = autenticazioneService.login(utente);
        }

        if (utente != null && utente.getIdUtente() != -1) {
            session.setAttribute("utente", utente);
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/index.jsp");
        } else if (utente.getIdUtente() == -1) {
            ReportService.creaReport(request, TipoReport.ERRORE,
                    "Utente bannato", "Ritenta il login dopo 5 giorni");
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/AutenticazioneController/login");
        } else {
            response.sendRedirect(
                    getServletContext().getContextPath()
                            + "/AutenticazioneController/login");
        }
    }

    private void registrazione(final HttpServletRequest request,
                               final HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);

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
                        LocalDate.parse(request.getParameter("dataDiNascita")));
                utente.setTelefono(request.getParameter("telefono"));
                utente.setStrada(request.getParameter("indirizzo"));
                utente.setCitta(request.getParameter("citta"));
                utente.setCap(request.getParameter("cap"));
                utente.setCf(request.getParameter("cf"));

                List<String> fileNames = FileServlet.uploadFoto(request);

                if (fileNames.size() > 0) {
                    utente.setFotoProfilo(fileNames.get(0));
                } else {
                    utente.setFotoProfilo("");
                }

                AutenticazioneService autenticazioneService =
                        new AutenticazioneServiceImpl(session, new UtenteDAO());

                if (autenticazioneService.registrazione(utente)) {
                    session.setAttribute("utente", utente);
                }
            } else {
                response.sendRedirect(
                        getServletContext().getContextPath()
                                + "/AutenticazioneController/registrazione");
            }

        }
        response.sendRedirect(
                getServletContext().getContextPath() + "/index.jsp");
    }
}
