package model.DAO;

import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;
import model.persistence.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public final class SegnalazioneDao implements DAO<Segnalazione> {
    /**
     * Logger di classe.
     */
    private static final Logger LOG =
            Logger.getLogger("SegnalazioneDao");

    @Override
    public Segnalazione getById(final int id) {
        LOG.warning("---------Called SegnalazioneDao.getById----------");
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
        LOG.warning("---------Called SegnalazioneDao.getAll----------");
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

        LOG.warning("---------Called SegnalazioneDao.save----------");
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            int ret = statement.executeUpdate();
            return (ret > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(final Segnalazione entity) {
        return false;
    }

    @Override
    public boolean delete(final Segnalazione entity) {
        return false;
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
}
