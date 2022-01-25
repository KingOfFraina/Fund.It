package controller;

import model.beans.FAQ;
import model.beans.Utente;
import model.services.FaqService;
import model.services.FaqServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneFAQController",
        value = "/GestioneFAQController/*")
public final class GestioneFAQController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        String resource = "/";

        switch (path) {
            case "/inserisciFAQ":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/modificaFAQ":
                resource = "/WEB-INF/results/"; //todo path
                break;
            case "/visualizzaFAQ":
                visualizzaFAQ(request, response);
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
        return;
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getPathInfo();

        switch (path) {
            case "/inserisciFAQ":
                inserisciFAQ(request, response);
                break;
            case "/modificaFAQ":
                modificaFAQ(request, response);
                break;
            case "/cancellaFAQ":
                cancellaFAQ(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }
        return;
    }
    private void visualizzaFAQ(final HttpServletRequest request,
                               final HttpServletResponse response) {
        FaqService fs = new FaqServiceImpl();
        request.setAttribute("faqList", fs.visualizzaFaq());
    }
    private void cancellaFAQ(final HttpServletRequest request,
                             final HttpServletResponse response)
            throws IOException {
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
        if (request.getAttribute("faq") == null
                || request.getAttribute("faq").getClass().getSimpleName().
                equals(FAQ.class.getSimpleName())) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }
        FAQ faq = (FAQ) request.getAttribute("faq");
        FaqService fs = new FaqServiceImpl();
        fs.cancellaFaq(faq);
        //todo return
    }
    private void modificaFAQ(final HttpServletRequest request,
                             final HttpServletResponse response)
            throws IOException {
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

        if (request.getAttribute("faq") == null
                || request.getAttribute("faq").getClass().getSimpleName().
                equals(FAQ.class.getSimpleName())) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }
        FAQ faq = (FAQ) request.getAttribute("faq");
        FaqService fs = new FaqServiceImpl();
        fs.modificaFaq(faq);
        //todo return
    }
    private void inserisciFAQ(final HttpServletRequest request,
                              final HttpServletResponse response)
            throws IOException {
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
        String domanda = request.getParameter("domanda");
        String risposta = request.getParameter("risposta");
        if (domanda == null || domanda.isBlank()
                || risposta == null || domanda.isBlank()) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Input errato");
            return;
        }

        FAQ faq = new FAQ();
        faq.setDomanda(domanda);
        faq.setRisposta(risposta);
        faq.setUtenteCreatore(utente);
        FaqService fs = new FaqServiceImpl();
        fs.inserisciFaq(faq);
        //todo return
    }

}
