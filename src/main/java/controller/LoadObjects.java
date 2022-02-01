package controller;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.beans.Campagna;
import model.storage.ConPool;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;

@WebServlet(name = "LoadObjects",
        value = "/LoadObjects",
        loadOnStartup = 0)
public class LoadObjects extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        List<Campagna> campagne;
        DAO<Campagna> campagnaDAO = new CampagnaDAO();
        campagne = campagnaDAO.getAll();
        getServletContext().setAttribute("campagneList", campagne);
    }

    @Override
    public void destroy() {
        super.destroy();
        List<Campagna> campagne = (List<Campagna>) getServletContext().getAttribute("campagneList");
        campagne.clear();
        ConPool.getInstance().closeDataSource();
    }

}
