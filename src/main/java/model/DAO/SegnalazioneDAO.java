package model.DAO;

import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public final class SegnalazioneDAO
        implements DAO<Segnalazione>, DAOHelper<Segnalazione> {
    /**
     * Logger di classe.
     */
    private static final Logger LOG =
            Logger.getLogger("SegnalazioneDAO");

    @Override
    public Segnalazione getById(final int id) {
        LOG.warning("---------Called SegnalazioneDAO.getById----------");
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT * "
                                     + "FROM segnalazione AS s"
                                     + " WHERE s.idSegnalazione = ?")) {
            ResultSet set = statement.executeQuery();

            return extract(set, "s");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Segnalazione> getAll() {
        LOG.warning("---------Called SegnalazioneDAO.getAll----------");
        List<Segnalazione> list;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("")) {
            list = new ArrayList<>();
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                list.add(extract(set, "s"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean save(final Segnalazione entity) {
        LOG.warning("---------Called SegnalazioneDAO.save----------");
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO segnalazione "
                             + "(DataOra, descrizione,"
                             + " idUtenteSegnalatore, idUtenteSegnalato, Stato)"
                             + "values (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            fillPreparedStatement(statement, entity);

            int ret = statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();

            if (set.next()) {
                entity.setIdSegnalazione(set.getInt(0));
            }
            return (ret > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(final Segnalazione entity) {
        int ret;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE segnalazione "
                             + "SET Stato = ? WHERE idSegnalazione = ?")) {

            statement.setString(1, entity.getStatoSegnalazione().toString());
            statement.setInt(2, entity.getIdSegnalazione());

            ret = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret > 0;
    }

    @Override
    public boolean delete(final Segnalazione entity) {
        int ret;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("")) {

            statement.setInt(1, entity.getIdSegnalazione());

            ret = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret > 0;
    }

    @Override
    public Segnalazione extract(final ResultSet resultSet, final String alias)
            throws SQLException {
        Segnalazione s = new Segnalazione();
        s.setIdSegnalazione(resultSet.getInt(""));
        s.setDataOra(new Date());
        s.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
        s.setSegnalato(new Utente());
        s.setSegnalatore(new Utente());
        s.setDescrizione(resultSet.getString(""));
        return s;
    }

    @Override
    public int fillPreparedStatement(final PreparedStatement preparedStatement,
                                     final Segnalazione entity)
            throws SQLException {
        int index = 1;

        preparedStatement.setDate(index++, (java.sql.Date) entity.getDataOra());
        preparedStatement.setString(index++, entity.getDescrizione());
        preparedStatement.setInt(index++,
                entity.getSegnalatore().getIdUtente());
        preparedStatement.setInt(index++, entity.getSegnalato().getIdUtente());
        preparedStatement.setString(index++,
                entity.getStatoSegnalazione().toString());

        return index;
    }
}
