package controller;

import model.persistence.ConPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection c = ConPool.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM utente");
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getString("nome") + rs.getString("cognome"));
            ConPool.getInstance().closeDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
