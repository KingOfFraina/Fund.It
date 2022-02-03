package model.DAO;

import model.beans.FAQ;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class FaqDAO implements DAO<FAQ> {

   @Override
   public FAQ getById(final int id) {
      if (id > 0) {
         try (Connection con = ConPool.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM faq WHERE idFaq = ?")) {
               ps.setInt(1, id);

               ResultSet rs = ps.executeQuery();

               FAQ retrieved = null;

               if (rs.next()) {
                  retrieved = extract(rs);
               }

               return retrieved;
            }
         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Id <= 0");
      }
   }

   @Override
   public List<FAQ> getAll() {
      try (Connection con = ConPool.getInstance().getConnection()) {
         try (PreparedStatement ps =
                      con.prepareStatement("SELECT * FROM faq")) {
            ResultSet rs = ps.executeQuery();
            List<FAQ> retrieved = new ArrayList<>();

            while (rs.next()) {
               retrieved.add(extract(rs));
            }

            return retrieved;
         }
      } catch (SQLException e) {
         throw new RuntimeException("SQL error: " + e.getMessage());
      }
   }

   @Override
   public boolean save(final FAQ entity) {
      if (entity != null) {
         try (Connection con = ConPool.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO "
                            + "faq (domanda, risposta, idUtente) "
                            + "VALUES (?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

               int index = 1;

               ps.setString(index++, entity.getDomanda());
               ps.setString(index++, entity.getRisposta());
               ps.setInt(index, entity.getUtenteCreatore().getIdUtente());

               boolean esito = ps.executeUpdate() > 0;
               ResultSet resultSet = ps.getGeneratedKeys();
               if (resultSet.next()) {
                  entity.setIdFaq(resultSet.getInt(1));
               }

               return esito;
            }
         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Null object");
      }
   }

   @Override
   public boolean update(final FAQ entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            String query =
                    "UPDATE faq SET domanda = ?, risposta = ?, idUtente = ? "
                            + "WHERE idFaq = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

               int index = 1;

               preparedStatement.setString(index++, entity.getDomanda());
               preparedStatement.setString(index++, entity.getRisposta());
               preparedStatement.setInt(index++,
                       entity.getUtenteCreatore().getIdUtente());
               preparedStatement.setInt(index, entity.getIdFaq());

               return preparedStatement.executeUpdate() > 0;
            }
         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Null object");
      }
   }

   @Override
   public boolean delete(final FAQ entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            String query =
                    "DELETE FROM faq WHERE idFaq = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

               preparedStatement.setInt(1, entity.getIdFaq());

               return preparedStatement.executeUpdate() > 0;
            }
         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Null object");
      }
   }

   @Override
   public FAQ extract(final ResultSet resultSet)
           throws SQLException {
      if (resultSet != null) {
         FAQ fq = new FAQ();

         fq.setIdFaq(resultSet.getInt("idFaq"));
         fq.setDomanda(resultSet.getString("domanda"));
         fq.setRisposta(resultSet.getString("risposta"));

         Utente ut = new Utente();
         ut.setIdUtente(resultSet.getInt("idUtente"));

         fq.setUtenteCreatore(ut);
         return fq;
      } else {
         throw new IllegalArgumentException("Null object");
      }
   }
}
