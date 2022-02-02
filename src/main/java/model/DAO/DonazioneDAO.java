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
        if (id <= 0)
            throw new IllegalArgumentException("Id must be > 0");
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
        boolean flag = false;
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
                    String query =
                            "INSERT INTO donazione (DataOra, ricevuta,"
                                    + " sommaDonata, commento, anonimo,"
                                    + " idUtente, idCampagna) "
                                    + "VALUES (?,?,?,?,?,?,?)";

                    try (PreparedStatement preparedStatement =
                                 connection.prepareStatement(query,
                                         PreparedStatement.RETURN_GENERATED_KEYS)) {

                        int index = 1;


                        preparedStatement.setTimestamp(index++,
                                Timestamp.valueOf(entity.getDataOra()));
                        preparedStatement.setString(index++,
                                entity.getRicevuta());
                        preparedStatement.setDouble(index++,
                                entity.getSommaDonata());
                        preparedStatement.setString(index++,
                                entity.getCommento());
                        preparedStatement.setBoolean(index++,
                                entity.isAnonimo());
                        preparedStatement.setInt(index++,
                                entity.getUtente().getIdUtente());
                        preparedStatement.setInt(index,
                                entity.getCampagna().getIdCampagna());

                        flag = preparedStatement.executeUpdate() > 0;
                        if (flag) {
                            ResultSet set = preparedStatement.getGeneratedKeys();
                            if (set.next())
                                entity.setIdDonazione(set.getInt(1));
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return flag;
    }

    @Override
    public boolean update(final Donazione entity) {
        boolean flag = false;
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
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
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return flag;
    }

    @Override
    public boolean delete(final Donazione entity) {
        boolean flag = false;
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
                    String query =
                            "DELETE FROM donazione WHERE idDonazione = ?";

                    try (PreparedStatement preparedStatement =
                                 connection.prepareStatement(query)) {

                        preparedStatement.setInt(1, entity.getIdDonazione());

                        flag = preparedStatement.executeUpdate() > 0;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
        List<Donazione> donazioneList = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                donazioneList = new ArrayList<>();

                String query = "SELECT * FROM donazione WHERE idCampagna = ?";

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, idCampagna);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        donazioneList.add(extract(resultSet, null));
                    }
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
        List<Donazione> donazioneList = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM donazione WHERE idUtente = ?";
                donazioneList = new ArrayList<>();

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, idUtente);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        donazioneList.add(extract(resultSet, null));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return donazioneList;
    }

}
