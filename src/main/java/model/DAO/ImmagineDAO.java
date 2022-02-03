package model.DAO;

import model.beans.Campagna;
import model.beans.Immagine;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ImmagineDAO implements DAO<Immagine> {
   @Override
   public Immagine getById(final int id) {

      if (id > 0) {
         try (Connection connection = ConPool.getInstance().getConnection()) {
            String query = "SELECT * FROM immagine WHERE idImmagine = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
               preparedStatement.setInt(1, id);
               ResultSet resultSet = preparedStatement.executeQuery();

               Immagine immagine = null;

               if (resultSet.next()) {
                  immagine = extract(resultSet);
               }
               return immagine;
            }
         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Null object");
      }
   }

   @Override
   public List<Immagine> getAll() {
      try (Connection connection = ConPool.getInstance().getConnection()) {
         String query = "SELECT * FROM immagine";

         try (PreparedStatement preparedStatement =
                      connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Immagine> immagineList = new ArrayList<>();

            while (resultSet.next()) {
               immagineList.add(extract(resultSet));
            }

            return immagineList;
         }
      } catch (SQLException e) {
         throw new RuntimeException("SQL error: " + e.getMessage());
      }
   }

   @Override
   public boolean save(final Immagine entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            String query =
                    "INSERT INTO immagine (idCampagna, path) "
                            + "VALUES (?,?)";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query,
                                 PreparedStatement
                                         .RETURN_GENERATED_KEYS)) {

               int index = 1;

               preparedStatement.setInt(index++,
                       entity.getCampagna().getIdCampagna());
               preparedStatement.setString(index++, entity.getPath());

               boolean esito = preparedStatement.executeUpdate() > 0;

               ResultSet resultSet = preparedStatement.getGeneratedKeys();
               if (resultSet.next()) {
                  entity.setId(resultSet.getInt(1));
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
   public boolean update(final Immagine entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            String query = "UPDATE immagine "
                    + "SET path = ? WHERE idImmagine = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

               int index = 1;

               preparedStatement.setString(index++, entity.getPath());
               preparedStatement.setInt(index, entity.getId());

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
   public boolean delete(final Immagine entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            String query =
                    "DELETE FROM immagine WHERE idImmagine = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

               preparedStatement.setInt(1, entity.getId());

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
   public Immagine extract(final ResultSet resultSet)
           throws SQLException {


      if (resultSet != null) {
         Immagine immagine = new Immagine();

         immagine.setId(resultSet.getInt("idImmagine"));
         immagine.setPath(resultSet.getString("path"));

         Campagna campagna = new Campagna();
         campagna.setIdCampagna(resultSet.getInt("idCampagna"));
         immagine.setCampagna(campagna);

         return immagine;
      } else {
         throw new IllegalArgumentException("Null object");
      }
   }

   /**
    * Permette la cancellazione di tutte le foto collegate di una campagna.
    *
    * @param idCampagna l'idCampagna della campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   public boolean deleteByIdCampagna(final int idCampagna) {
      if (idCampagna > 0) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            String query =
                    "DELETE FROM immagine WHERE idCampagna = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

               preparedStatement.setInt(1, idCampagna);

               return preparedStatement.executeUpdate() > 0;
            }

         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Id <= 0");
      }
   }

   /**
    * @param idCampagna id della campagna
    * @return tutte le immagini della campagna
    */
   public List<Immagine> getByIdCampagna(final int idCampagna) {
      if (idCampagna > 0) {
         try (Connection connection = ConPool.getInstance().getConnection()) {
            List<Immagine> immagineList = new ArrayList<>();

            String query = "SELECT * FROM immagine WHERE idCampagna = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
               preparedStatement.setInt(1, idCampagna);
               ResultSet resultSet = preparedStatement.executeQuery();

               while (resultSet.next()) {
                  immagineList.add(extract(resultSet));
               }

               return immagineList;
            }
         } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Id <= 0");
      }
   }
}
