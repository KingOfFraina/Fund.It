package controller;

import model.beans.proxies.CampagnaProxy;
import model.DAO.CampagnaDAO;
import model.beans.*;
import model.storage.ConPool;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Campagna campagna = new CampagnaDAO().getById(1);
        CampagnaProxy segnalazioneProxy = new CampagnaProxy(campagna);

        for(Segnalazione i : segnalazioneProxy.getSegnalazioni())
            System.out.println(i);
    }

    @Override
    public void destroy() {
        super.destroy();
        ConPool.getInstance().closeDataSource();
    }
}
