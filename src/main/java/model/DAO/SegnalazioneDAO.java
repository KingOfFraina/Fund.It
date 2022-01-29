package model.DAO;

import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.Utente;
import model.beans.StatoSegnalazione;
import model.storage.ConPool;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class SegnalazioneDAO
        implements DAO<Segnalazione> {
    /**
     * @param id rappresenta l'identificativo dell'entity
     * @return null se non viene trovato nessun risultato,
     * un'istanza di T nel caso in cui viene trovato un risultato
     */
    @Override
    public Segnalazione getById(final int id) {
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT * "
                                     + "FROM segnalazione AS s"
                                     + " WHERE s.idSegnalazione = ?")) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return extract(set, "s");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * @return una lista di entity T
     */
    @Override
    public List<Segnalazione> getAll() {
        String sql = "SELECT * FROM segnalazione AS s";
        List<Segnalazione> list;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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

    /**
     * @param entity l'istanza da salvare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
    @Override
    public boolean save(final Segnalazione entity) {
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO segnalazione "
                             + "(DataOra, descrizione,"
                             + " idUtenteSegnalatore,"
                             + " idUtenteSegnalato, Stato, idCampagna)"
                             + "values (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            int index = 0;

            statement.setTimestamp(++index, Timestamp.from(entity.getDataOra().toInstant()));
            statement.setString(++index, entity.getDescrizione());
            statement.setInt(++index,
                    entity.getSegnalatore().getIdUtente());
            statement.setInt(++index, entity.getSegnalato().getIdUtente());
            statement.setString(++index,
                    entity.getStatoSegnalazione().toString());
            statement.setInt(++index,
                    entity.getCampagnaSegnalata().getIdCampagna());

            int ret = statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                entity.setIdSegnalazione(set.getInt(1));
            }
            return (ret > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param entity l'istanza da aggiornare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
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

    /**
     * @param entity l'istanza da eliminare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
    @Override
    public boolean delete(final Segnalazione entity) {
        int ret;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM segnalazione "
                             + " WHERE idSegnalazione = ?")) {
            statement.setInt(1, entity.getIdSegnalazione());


            ret = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret > 0;
    }

    /**
     * @param resultSet resultSet della query eseguita
     * @param alias     eventuale alias del field
     * @return l'istanza della della classe T popolata con le informazioni
     * presenti nel resultSet
     * @throws SQLException eccezione lanciata in caso di problemi
     */
    @Override
    public Segnalazione extract(final ResultSet resultSet, final String alias)
            throws SQLException {
        Segnalazione s = new Segnalazione();
        s.setIdSegnalazione(resultSet.getInt(alias + ".idSegnalazione"));
        s.setDataOra(resultSet.getDate(alias + ".DataOra"));
        s.setStatoSegnalazione(StatoSegnalazione.valueOf(
                resultSet.getString(alias + ".Stato").toUpperCase()));
        Utente segnalato = new Utente();
        segnalato.setIdUtente(resultSet.getInt(alias + ".idUtenteSegnalato"));
        s.setSegnalato(segnalato);
        Utente segnalatore = new Utente();
        segnalatore.setIdUtente(
                resultSet.getInt(alias + ".idUtenteSegnalatore"));
        s.setSegnalatore(segnalatore);
        s.setDescrizione(resultSet.getString(alias + ".descrizione"));
        Campagna c = new Campagna();
        c.setIdCampagna(resultSet.getInt(alias + ".idCampagna"));
        s.setCampagnaSegnalata(c);
        return s;
    }

    /**
     * @param idUtente id dell'utente
     * @return lista di segnalazioni effettuate dall'utente
     */
    public List<Segnalazione> getByIdUtente(final int idUtente) {
        List<Segnalazione> list;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT *"
                             + "FROM segnalazione AS s"
                             + " WHERE idUtenteSegnalatore = ?")) {

            list = new ArrayList<>();
            statement.setInt(1, idUtente);

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                list.add(extract(set, "s"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * @param idCampagna id della campagna
     * @return lista delle segnalazioni effettuate sulla campagna
     */
    public List<Segnalazione> getByIdCampagna(final int idCampagna) {
        List<Segnalazione> segnalazioneList = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                segnalazioneList = new ArrayList<>();

                String query = "SELECT *"
                        + " FROM segnalazione"
                        + " WHERE idCampagna = ?";

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, idCampagna);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        segnalazioneList.add(extract(resultSet, null));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return segnalazioneList;
    }
}
