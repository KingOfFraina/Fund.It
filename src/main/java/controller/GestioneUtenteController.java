package controller;

import model.storage.ConPool;

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

    }

    @Override
    public void destroy()
    {
        ConPool.getInstance().closeDataSource();
        super.destroy();
    }
}
