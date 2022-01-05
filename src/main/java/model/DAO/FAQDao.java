package model.DAO;

import model.beans.FAQ;
import model.beans.Utente;
import model.persistence.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FAQDao implements  DAO<FAQ> {

    @Override
    public FAQ getById(int id) {

        try (Connection con = ConPool.getInstance().getConnection())
        {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM faq WHERE idFaq = ?"))
            {
                ps.setInt(0, id);

                ResultSet rs = ps.executeQuery();

                FAQ retrieved = null;

                while (rs.next())
                {
                    retrieved = extract(rs, "");
                }

                return retrieved;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FAQ> getAll() {
        return null;
    }

    @Override
    public boolean save(FAQ entity) {
        return false;
    }

    @Override
    public boolean update(FAQ entity) {
        return false;
    }

    @Override
    public boolean delete(FAQ entity) {
        return false;
    }

    @Override
    public FAQ extract(ResultSet resultSet, String alias) throws SQLException{
        if(resultSet != null) {
            FAQ fq = new FAQ();

            if(!alias.isEmpty())
                alias+=".";

            fq.setIdFaq(resultSet.getInt(alias+"idFaq"));
            fq.setDomanda(resultSet.getString(alias+"domanda"));
            fq.setRisposta(resultSet.getString(alias+"risposta"));

            Utente ut = new Utente();
            ut.setIdUtente(resultSet.getInt(alias+"idUtente"));

            fq.setUtenteCreatore(ut);
            return fq;
        }

        return null;
    }
}
