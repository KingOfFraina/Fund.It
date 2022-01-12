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
    /**
     * Costante indica il primo parametro di un PreparedStatement.
     */
    private static final int FIRST_PARAMETER = 1;
    /**
     * Costante che indica il secondo parametro di un PreparedStatement.
     */
    private static final int SECOND_PARAMETER = 2;
    /**
     * Costante che indica il terzo parametro di un PreparedStatement.
     */
    private static final int THIRD_PARAMETER = 3;
    /**
     * Costante che indica il quarto parametro di un PreparedStatement.
     */
    private static final int FOURTH_PARAMETER = 4;
    /**
     * Costante che indica il quinto parametro di un PreparedStatement.
     */
    private static final int FIFTH_PARAMETER = 5;


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

            statement.setDate(
                    FIRST_PARAMETER, (java.sql.Date) entity.getDataOra());

            statement.setString(
                    SECOND_PARAMETER, entity.getDescrizione());

            statement.setInt(
                    THIRD_PARAMETER, entity.getSegnalatore().getIdUtente());

            statement.setInt(
                    FOURTH_PARAMETER, entity.getSegnalato().getIdUtente());

            statement.setString(
                    FIFTH_PARAMETER, entity.getStatoSegnalazione().toString());

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
        return 0;
    }
}
