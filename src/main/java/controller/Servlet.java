package controller;

import model.storage.ConPool;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Connection c = ConPool.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM utente");
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getString("nome") + rs.getString("cognome"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConPool.getInstance().closeDataSource();
    }
}
