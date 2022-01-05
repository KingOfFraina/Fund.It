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

public final class SegnalazioneDao implements DAO<Segnalazione> {
    /**
     * @param id rappresenta l'identificativo dell'entity
     * @return Segnalazione Un oggetto di tipo Segnalazione associato al record
     * nel database
     */
    @Override
    public Segnalazione getById(final int id) {
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT "
                     + "* FROM segnalazione AS s"
                     + " WHERE s.idSegnalazione = ?")) {
            ResultSet set = statement.executeQuery();

            return extract(set, "s");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return List<Segnalazione> Una lista di oggetti di tipo Segnalazione
     */
    @Override
    public List<Segnalazione> getAll() {
        List<Segnalazione> list = new ArrayList<>();
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
        return false;
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
