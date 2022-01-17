package model.DAO;

import model.beans.FAQ;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class FaqDAO implements DAO<FAQ> {

   @Override
   public FAQ getById(final int id) {

      try (Connection con = ConPool.getInstance().getConnection()) {
         try (PreparedStatement ps = con.prepareStatement("SELECT * "
                 + "FROM faq WHERE idFaq = ?")) {
            ps.setInt(0, id);

            ResultSet rs = ps.executeQuery();

            FAQ retrieved = null;

            while (rs.next()) {
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

      try (Connection con = ConPool.getInstance().getConnection()) {
         try (PreparedStatement ps =
                      con.prepareStatement("SELECT * FROM faq")) {
            ResultSet rs = ps.executeQuery();

            List<FAQ> retrieved = null;

            while (rs.next()) {
                  retrieved.add(extract(rs, ""));
            }

            return retrieved;
         }
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
      return null;
   }

   @Override
   public boolean save(final FAQ entity) {
      if (entity != null) {
         try (Connection con = ConPool.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO "
                    + "faq (domanda, risposta, idUtente) VALUES (?,?,?)")) {

               int index = 1;

               ps.setString(index++, entity.getDomanda());
               ps.setString(index++, entity.getRisposta());
               ps.setInt(index, entity.getUtenteCreatore().getIdUtente());

               return ps.executeUpdate() > 0;
            }
         } catch (SQLException throwables) {
            throwables.printStackTrace();
         }
      }
      return false;
   }

   @Override
   public boolean update(final FAQ entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            if (connection != null) {
               String query =
                       "UPDATE faq SET domanda = ?, risposta = ?, idUtente = ?"
                               + "WHERE idFaq = ?";

               try (PreparedStatement preparedStatement =
                            connection.prepareStatement(query)) {

                  int index = 1;

                  preparedStatement.setString(index++, entity.getDomanda());
                  preparedStatement.setString(index++, entity.getRisposta());
                  preparedStatement.setInt(index, entity.getIdFaq());

                  return preparedStatement.executeUpdate() > 0;
               }
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return false;
   }

   @Override
   public boolean delete(final FAQ entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            if (connection != null) {
               String query =
                       "DELETE FROM faq WHERE idFaq = ?";

               try (PreparedStatement preparedStatement =
                            connection.prepareStatement(query)) {

                  preparedStatement.setInt(1, entity.getIdFaq());

                  return preparedStatement.executeUpdate() > 0;
               }
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return false;
   }

   @Override
   public FAQ extract(final ResultSet resultSet, final String alias)
           throws SQLException {
      if (resultSet != null) {
         String tableAlias = "";
         FAQ fq = new FAQ();

         if (alias != null) {
            tableAlias = alias + ".";
         }

         fq.setIdFaq(resultSet.getInt(alias + "idFaq"));
         fq.setDomanda(resultSet.getString(alias + "domanda"));
         fq.setRisposta(resultSet.getString(alias + "risposta"));

         Utente ut = new Utente();
         ut.setIdUtente(resultSet.getInt(alias + "idUtente"));

         fq.setUtenteCreatore(ut);
         return fq;
      }

      return null;
   }
}
