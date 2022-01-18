package controller;

import model.DAO.UtenteDAO;
import model.beans.proxies.CampagnaProxy;
import model.DAO.CampagnaDAO;
import model.beans.*;
import model.beans.proxies.UtenteProxy;
import model.storage.ConPool;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Campagna campagna = new CampagnaDAO().getById(1);
        CampagnaProxy segnalazioneProxy = new CampagnaProxy(campagna);

        for (Segnalazione i : segnalazioneProxy.getSegnalazioni())
            System.out.println(i);

        Utente u = new UtenteDAO().getById(2);
        UtenteProxy proxy = new UtenteProxy(u);
        for (Segnalazione s1 : proxy.getSegnalazioni())
            System.out.println(s1);

        for (Segnalazione s1 : proxy.getSegnalazioni())
            System.out.println(s1);
    }

    @Override
    public void destroy() {
        super.destroy();
        ConPool.getInstance().closeDataSource();
    }
}
