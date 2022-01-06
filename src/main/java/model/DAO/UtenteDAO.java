package model.DAO;

import model.beans.Immagine;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class UtenteDAO implements DAO<Utente> {
   @Override
   public Utente getById(final int id) {
      Utente utente = null;

      try (Connection connection = ConPool.getInstance().getConnection()) {
         if (connection != null) {
            String query = "SELECT * FROM immagine WHERE idImmagine = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
               preparedStatement.setInt(1, id);
               ResultSet resultSet = preparedStatement.executeQuery();

               if (resultSet.next()) {
                  immagine = extract(resultSet, null);
               }
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return utente;
   }

   @Override
   public List<Utente> getAll() {
      return null;
   }

   @Override
   public boolean save(final Utente entity) {
      return false;
   }

   @Override
   public boolean update(final Utente entity) {
      return false;
   }

   @Override
   public boolean delete(final Utente entity) {
      return false;
   }

   @Override
   public Utente extract(final ResultSet resultSet, final String alias)
           throws SQLException {
      return null;
   }
}
