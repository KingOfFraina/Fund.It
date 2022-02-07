package controller;

import model.beans.FAQ;
import model.beans.Utente;
import model.services.FaqServiceImpl;
import model.services.ReportService;
import model.services.TipoReport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GestioneFAQController",
        value = "/faq/*")
public final class GestioneFAQController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {

        String resource = "/WEB-INF/results/visualizzaFAQ.jsp";
        String path = request.getPathInfo();

        if (!path.equals("/visualizzaFAQ")) {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("utente") != null) {
                Utente utente = (Utente) session.getAttribute("utente");

                if (!utente.isAdmin()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Non Autorizzato");
                    return;
                } else {
                    if (path.equals("/modificaFAQ")) {
                        request.setAttribute("faq", new FaqServiceImpl()
                                .visualizzaFaq(Integer
                                        .parseInt(request.getParameter("idFaq"))));
                    } else if (!path.equals("/inserisciFAQ")) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    resource = "/WEB-INF/results/formFAQ.jsp";
                }
            } else {
                response.sendRedirect(
                        getServletContext().getContextPath()
                                + "/autenticazione/login");
                return;
            }
        } else {
            request.setAttribute("faqList", new FaqServiceImpl()
                    .visualizzaFaq());
        }

        request.getRequestDispatcher(resource).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws IOException {
        String path = request.getPathInfo();
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("utente") != null) {
            Utente utente = (Utente) session.getAttribute("utente");

            switch (path) {
                case "/inserisciFAQ" -> {
                    String domanda = request.getParameter("domanda");
                    String risposta = request.getParameter("risposta");

                    if (domanda == null || domanda.isBlank()
                            || risposta == null || domanda.isBlank()) {
                        throw new IllegalArgumentException("Input errati");
                    }

                    FAQ faq = new FAQ();
                    faq.setDomanda(domanda);
                    faq.setRisposta(risposta);
                    faq.setUtenteCreatore(utente);

                    if (new FaqServiceImpl().inserisciFaq(faq)) {
                        ReportService.creaReport(request, TipoReport.INFO,
                                "Esito operazione:", "FAQ inserita con successo");
                    } else {
                        ReportService.creaReport(request, TipoReport.ERRORE,
                                "Esito operazione:", "FAQ non inserita");
                    }
                }
                case "/modificaFAQ" -> {
                    String domanda = request.getParameter("domanda");
                    String risposta = request.getParameter("risposta");
                    int idFAQ = Integer.parseInt(request.getParameter("idFaq"));

                    if (domanda == null || domanda.isBlank()
                            || risposta == null || domanda.isBlank()) {

                        throw new IllegalArgumentException("Input errati");
                    }

                    FAQ faq = new FAQ();
                    faq.setIdFaq(idFAQ);
                    faq.setDomanda(domanda);
                    faq.setRisposta(risposta);
                    faq.setUtenteCreatore(utente);

                    if (new FaqServiceImpl().modificaFaq(faq)) {
                        ReportService.creaReport(request, TipoReport.INFO,
                                "Esito operazione:", "FAQ modificata con successo");
                    } else {
                        ReportService.creaReport(request, TipoReport.ERRORE,
                                "Esito operazione:", "FAQ non modificata");
                    }
                }
                case "/eliminaFAQ" -> {
                    FAQ faq = new FAQ();
                    faq.setIdFaq(Integer.parseInt(request.getParameter("idFaq")));
                    faq.setUtenteCreatore(utente);

                    if (new FaqServiceImpl().cancellaFaq(faq)) {
                        ReportService.creaReport(request, TipoReport.INFO,
                                "Esito operazione:", "FAQ eliminata con successo");
                    } else {
                        ReportService.creaReport(request, TipoReport.ERRORE,
                                "Esito operazione:", "FAQ non eliminata");
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
                            + "/faq/visualizzaFAQ");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
