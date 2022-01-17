package model.DAO;

import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DonazioneDAO implements DAO<Donazione> {

   @Override
   public Donazione getById(final int id) {
      try (Connection con = ConPool.getInstance().getConnection()) {
         try (PreparedStatement ps = con.prepareStatement("SELECT * "
                 + "FROM donazione WHERE idDonazione = ?")) {
            ps.setInt(0, id);

            ResultSet rs = ps.executeQuery();

            Donazione retrieved = null;

            if (rs.next()) {
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
   public List<Donazione> getAll() {
      List<Donazione> donazioneList = null;

      try (Connection connection = ConPool.getInstance().getConnection()) {
         if (connection != null) {
            String query = "SELECT * FROM donazione";
            donazioneList = new ArrayList<>();

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
               ResultSet resultSet = preparedStatement.executeQuery();

               while (resultSet.next()) {
                  donazioneList.add(extract(resultSet, null));
               }
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return donazioneList;
   }

   @Override
   public boolean save(final Donazione entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            if (connection != null) {
               String query =
                       "INSERT INTO donazione (DataOra, ricevuta, sommaDonata, "
                               + "commento, anonimo, idUtente, idCampagna) "
                               + "VALUES (?,?,?,?,?,?,?)";

               try (PreparedStatement preparedStatement =
                            connection.prepareStatement(query)) {

                  int index = 1;

                  preparedStatement.setDate(index++,
                          (Date) entity.getDataOra());
                  preparedStatement.setString(index++, entity.getRicevuta());
                  preparedStatement.setDouble(index++, entity.getSommaDonata());
                  preparedStatement.setString(index++, entity.getCommento());
                  preparedStatement.setBoolean(index++, entity.isAnonimo());
                  preparedStatement.setInt(index++,
                          entity.getUtente().getIdUtente());
                  preparedStatement.setInt(index++,
                          entity.getCampagna().getIdCampagna());

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
   public boolean update(final Donazione entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            if (connection != null) {
               String query =
                       "UPDATE donazione SET sommaDonata = ?"
                               + "WHERE idDonazione = ?";

               try (PreparedStatement preparedStatement =
                            connection.prepareStatement(query)) {

                  int index = 1;

                  preparedStatement.setDouble(index++, entity.getSommaDonata());
                  preparedStatement.setInt(index, entity.getIdDonazione());

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
   public boolean delete(final Donazione entity) {
      if (entity != null) {
         try (Connection connection =
                      ConPool.getInstance().getConnection()) {
            if (connection != null) {
               String query =
                       "DELETE FROM donazione WHERE idDonazione = ?";

               try (PreparedStatement preparedStatement =
                            connection.prepareStatement(query)) {

                  preparedStatement.setInt(1, entity.getIdDonazione());

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
   public Donazione extract(final ResultSet resultSet, final String alias)
           throws SQLException {
      Donazione donazione = null;
      String tableAlias = "";

      if (resultSet != null) {
         donazione = new Donazione();

         if (alias != null) {
            tableAlias = alias + ".";
         }

         donazione.setDataOra(resultSet.getDate(tableAlias + "DataOra"));
         donazione.setRicevuta(resultSet.getString(tableAlias + "ricevuta"));
         donazione.setSommaDonata(resultSet.getDouble(tableAlias
                 + "sommaDonata"));
         donazione.setCommento(resultSet.getString(tableAlias + "commento"));
         donazione.setAnonimo(resultSet.getBoolean(tableAlias + "anonimo"));

         Utente utente = new Utente();
         utente.setIdUtente(resultSet.getInt(tableAlias + "idUtente"));

         Campagna campagna = new Campagna();
         campagna.setIdCampagna(resultSet.getInt(tableAlias + "idCampagna"));

         donazione.setUtente(utente);
         donazione.setCampagna(campagna);
      }

      return donazione;
   }
}
