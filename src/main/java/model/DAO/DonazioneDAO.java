package model.DAO;

import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class DonazioneDAO implements DAO<Donazione> {

    @Override
    public Donazione getById(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be > 0");
        }
        Donazione retrieved = null;
        try (Connection con = ConPool.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * "
                    + "FROM donazione WHERE idDonazione = ?")) {
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    retrieved = extract(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retrieved;
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
                        donazioneList.add(extract(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return donazioneList;
    }

    @Override
    public boolean save(final Donazione entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be not null");
        }
        boolean flag = false;
        try (Connection connection =
                     ConPool.getInstance().getConnection()) {
            String query =
                    "INSERT INTO donazione (DataOra, ricevuta,"
                            + " sommaDonata, commento, anonimo,"
                            + " idUtente, idCampagna) "
                            + "VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement ps =
                         connection.prepareStatement(query,
                                 PreparedStatement
                                         .RETURN_GENERATED_KEYS)) {
                int index = 1;
                ps.setTimestamp(index++,
                        Timestamp.valueOf(entity.getDataOra()));
                ps.setString(index++,
                        entity.getRicevuta());
                ps.setDouble(index++,
                        entity.getSommaDonata());
                ps.setString(index++,
                        entity.getCommento());
                ps.setBoolean(index++,
                        entity.isAnonimo());
                ps.setInt(index++,
                        entity.getUtente().getIdUtente());
                ps.setInt(index,
                        entity.getCampagna().getIdCampagna());
                flag = ps.executeUpdate() > 0;
                if (flag) {
                    ResultSet set = ps.getGeneratedKeys();
                    if (set.next()) {
                        entity.setIdDonazione(set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean update(final Donazione entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be not null");
        }
        boolean flag = false;
        try (Connection connection =
                     ConPool.getInstance().getConnection()) {
            String query =
                    "UPDATE donazione SET sommaDonata = ?, "
                            + "commento = ? "
                            + "WHERE idDonazione = ?";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

                int index = 1;

                preparedStatement.setDouble(index++,
                        entity.getSommaDonata());
                preparedStatement.setString(index++,
                        entity.getCommento());
                preparedStatement.setInt(index,
                        entity.getIdDonazione());

                flag = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean delete(final Donazione entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be not null");
        }
        boolean flag = false;
        try (Connection connection =
                     ConPool.getInstance().getConnection()) {
            String query =
                    "DELETE FROM donazione WHERE idDonazione = ?";
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {

                preparedStatement.setInt(1, entity.getIdDonazione());
                flag = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public Donazione extract(final ResultSet resultSet)
            throws SQLException {
        Donazione donazione = null;
        String tableAlias = "";

        if (resultSet != null) {
            donazione = new Donazione();
            donazione.setIdDonazione(resultSet.getInt(tableAlias
                    + "idDonazione"));
            donazione.setDataOra(resultSet.getTimestamp(tableAlias
                    + "DataOra").toLocalDateTime());
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

    /**
     * @param idCampagna id della campagna.
     * @return lista delle donazioni relative a una campagna.
     */
    public List<Donazione> getByIdCampagna(final int idCampagna) {
        if (idCampagna <= 0) {
            throw new IllegalArgumentException("id must be > 0");
        }
        List<Donazione> donazioneList = null;
        try (Connection connection = ConPool.getInstance().getConnection()) {
            donazioneList = new ArrayList<>();
            String query = "SELECT * FROM donazione WHERE idCampagna = ?";
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idCampagna);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    donazioneList.add(extract(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return donazioneList;
    }

    /**
     * @param idUtente id dell'utente.
     * @return lista delle donazioni effettuate dall'utente.
     */
    public List<Donazione> getAllByUtente(final int idUtente) {
        if (idUtente <= 0) {
            throw new IllegalArgumentException("Id must be > 0");
        }
        List<Donazione> donazioneList = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            String query = "SELECT * FROM donazione WHERE idUtente = ?";
            donazioneList = new ArrayList<>();
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idUtente);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    donazioneList.add(extract(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return donazioneList;
    }
}
