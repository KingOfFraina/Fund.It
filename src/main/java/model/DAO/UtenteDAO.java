package model.DAO;

import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class UtenteDAO implements DAO<Utente> {
    @Override
    public Utente getById(final int id) {
        Utente utente = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM utente WHERE idUtente = ?";

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        utente = extract(resultSet, null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utente;
    }

    /**
     * Metodo che permette il login.
     *
     * @param utente Il bean contenete alcune informazioni sull'utente che
     *               intende effettuare il login
     * @return Il bean dell'utente presente nel database
     */
    public Utente doLogin(final Utente utente) {
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT *"
                             + "FROM utente WHERE email = ? "
                             + "AND passwordhash = ?")) {

            statement.setString(1, utente.getEmail());
            statement.setString(2, utente.getPassword());

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return extract(set, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Utente> getAll() {
        List<Utente> utenteList = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM utente";
                utenteList = new ArrayList<>();

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        utenteList.add(extract(resultSet, null));
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenteList;
    }

    @Override
    public boolean update(final Utente entity) {
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
                    String query =
                            "UPDATE utente SET dataBan = ?, admin = ?,"
                                    + "fotoProfilo = ?, passwordhash = ?,"
                                    + " telefono = ?, nome = ?, "
                                    + "cognome = ?, email = ?,"
                                    + " strada = ?, città = ?"
                                    + ", cap = ?, cf = ?, dataDiNascita = ?"
                                    + "WHERE idUtente = ?";

                    try (PreparedStatement preparedStatement =
                                 connection.prepareStatement(query)) {

                        int index = fillPreparedStatement(preparedStatement,
                                entity);
                        preparedStatement.setInt(index, entity.getIdUtente());

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
    public boolean save(final Utente entity) {
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
                    String query =
                            "INSERT INTO utente (dataBan, admin, fotoProfilo, "
                                    + "passwordhash, telefono, nome,"
                                    + " cognome, email,"
                                    + " strada, città, cap, cf, dataDiNascita) "
                                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    try (PreparedStatement preparedStatement =
                                 connection.prepareStatement(query)) {

                        fillPreparedStatement(preparedStatement, entity);

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
    public boolean delete(final Utente entity) {
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
                    String query =
                            "DELETE FROM utente WHERE idUtente = ?";

                    try (PreparedStatement preparedStatement =
                                 connection.prepareStatement(query)) {

                        preparedStatement.setInt(1, entity.getIdUtente());

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
    public Utente extract(final ResultSet resultSet, final String alias)
            throws SQLException {
        Utente utente = null;
        String tableAlias = "";

        if (resultSet != null) {
            utente = new Utente();

            if (alias != null) {
                tableAlias = alias + ".";
            }

            utente.setAdmin(resultSet.getBoolean(tableAlias + "admin"));
            utente.setCap(resultSet.getString(tableAlias + "cap"));
            utente.setCf(resultSet.getString(tableAlias + "cf"));
            utente.setCitta(resultSet.getString(tableAlias + "città"));
            utente.setCognome(resultSet.getString(tableAlias + "cognome"));
            utente.setDataBan(LocalDateTime
                    .from(resultSet.getDate(tableAlias + "dataBan")
                            .toLocalDate()));
            utente.setDataDiNascita(resultSet.getDate(tableAlias
                    + "dataDiNascita").toLocalDate());
            utente.setEmail(resultSet.getString(tableAlias
                    + "email"));
            utente.setFotoProfilo(
                    resultSet.getString(tableAlias + "fotoProfilo"));
            utente.setIdUtente(resultSet.getInt(tableAlias + "idUtente"));
            utente.setNome(resultSet.getString(tableAlias + "nome"));
            utente.setPassword(
                    resultSet.getString(tableAlias + "passwordhash"));
            utente.setStrada(resultSet.getString(tableAlias + "strada"));
            utente.setTelefono(resultSet.getString(tableAlias + "telefono"));
            utente.setCampagne(null);
            utente.setDonazioni(null);
            utente.setSegnalazioni(null);
        }

        return utente;
    }

    private int fillPreparedStatement(final PreparedStatement preparedStatement,
                                      final Utente entity) throws SQLException {
        int index = 1;

        preparedStatement.setDate(index++, Date
                .valueOf(entity.getDataBan().toLocalDate()));
        preparedStatement.setBoolean(index++, entity.isAdmin());
        preparedStatement.setString(index++, entity.getFotoProfilo());
        preparedStatement.setString(index++, entity.getPassword());
        preparedStatement.setString(index++, entity.getTelefono());
        preparedStatement.setString(index++, entity.getNome());
        preparedStatement.setString(index++, entity.getCognome());
        preparedStatement.setString(index++, entity.getEmail());
        preparedStatement.setString(index++, entity.getStrada());
        preparedStatement.setString(index++, entity.getCitta());
        preparedStatement.setString(index++, entity.getCap());
        preparedStatement.setString(index++, entity.getCf());
        preparedStatement.setDate(index++,
                Date.valueOf(entity.getDataDiNascita()));

        return index;
    }
}
