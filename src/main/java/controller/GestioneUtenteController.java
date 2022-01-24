package controller;

import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.beans.proxies.UtenteProxy;
import model.beans.proxyInterfaces.UtenteInterface;
import model.services.UtenteService;
import model.services.UtenteServiceImpl;
import model.storage.ConPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "GestioneUtenteController", value = "/GestioneUtenteController/*")
public class GestioneUtenteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        String resource = "/";

        switch(path){
            case "/visualizzaDashboard":
                resource = "/WEB-INF/results/";//todo path
            break;
            case "/modificaProfilo":
                resource = "/WEB-INF/results/";//todo path
            break;
            case "/visualizzaUtenti":
                resource = "/WEB-INF/results/";//todo path
            break;
            case "/promuoviDeclassaUtente":
                resource = "/WEB-INF/results/";//todo path
            break;
            case "/sospensioneUtente":
                resource = "/WEB-INF/results/";//todo path
            break;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();

        switch(path){
            case "/visualizzaDashboard":
                visualizzaDashboard(request, response);
                break;
            case "/modificaProfilo":
                //todo meth
                break;
            case "/visualizzaUtenti":
                //todo meth
                break;
            case "/promuoviDeclassaUtente":
                //todo meth
                break;
            case "/sospensioneUtente":
                //todo meth
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Risorsa non trovata");
                return;
        }

    }

    private void visualizzaDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if(session.getAttribute("utente") == null || !session.getAttribute("utente").getClass().getSimpleName().equals((new Utente()).getClass().getSimpleName())){
            response.sendRedirect(request.getServletContext().getContextPath() + "/AutenticazioneController/login");
            return;
        }

        UtenteService uts = new UtenteServiceImpl();
        Utente ut = uts.visualizzaDashboardUtente(((Utente) request.getAttribute("utente")).getIdUtente());

        UtenteInterface ui = new UtenteProxy(ut);
        ut.setDonazioni(ui.getDonazioni());
        ut.setCampagne(ui.getCampagne());
        ut.setSegnalazioni(ui.getSegnalazioni());
        request.setAttribute("utente", ut);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/results/profilo_utente.jsp");
        dispatcher.forward(request, response);
        return;
    }


    @Override
    public void destroy()
    {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }
}
