package model.DAO;

import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.StatoCampagna;
import model.beans.Utente;
import model.storage.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class CampagnaDAO
        implements DAO<Campagna> {
    /**
     * @param id rappresenta l'identificativo dell'entity
     * @return null se non viene trovato nessun risultato,
     * un'istanza di T nel caso in cui viene trovato un risultato
     */
    @Override
    public Campagna getById(final int id) {
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT"
                     + " * FROM campagna AS c"
                     + " WHERE idCampagna = ?")) {
            int index = 1;

            statement.setInt(index, id);

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return extract(set, "c");
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
    public List<Campagna> getAll() {
        List<Campagna> list = new ArrayList<>();
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * "
                             + "FROM campagna AS c ")) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                list.add(extract(set, "c"));
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
    public boolean save(final Campagna entity) {
        int ret;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO campagna "
                                     + "(STATO, TITOLO, DESCRIZIONE,"
                                     + " SOMMARACCOLTA, SOMMATARGET,"
                                     + " IDCATEGORIA, IDUTENTE)"
                                     + "VALUES (?,?,?,?,?,?,?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {

            int index = 1;
            statement.setInt(index++, entity.getIdCampagna());
            statement.setString(index++, entity.getStato().toString());
            statement.setString(index++, entity.getTitolo());
            statement.setString(index++, entity.getDescrizione());
            statement.setDouble(index++, entity.getSommaRaccolta());
            statement.setDouble(index++, entity.getSommaTarget());
            statement.setInt(index++,
                    entity.getCategoria().getIdCategoria());
            statement.setInt(index++, entity.getUtente().getIdUtente());

            ret = statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                entity.setIdCampagna(set.getInt(0));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret > 0;
    }

    /**
     * @param entity l'istanza da aggiornare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
    @Override
    public boolean update(final Campagna entity) {
        int ret = 0;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE campagna SET"
                             + " Stato = ?, titolo = ?, descrizione = ?,"
                             + " sommaRaccolta = ?, sommaTarget = ?,"
                             + " idCategoria = ?, idUtente = ?"
                             + " WHERE idCampagna = ?")) {
            int index = 1;
            statement.setString(index++, entity.getStato().toString());
            statement.setString(index++, entity.getTitolo());
            statement.setString(index++, entity.getDescrizione());
            statement.setDouble(index++, entity.getSommaRaccolta());
            statement.setDouble(index++, entity.getSommaTarget());
            statement.setInt(index++, entity.getCategoria().getIdCategoria());

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
    public boolean delete(final Campagna entity) {
        int ret = 0;
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM campagna"
                             + " WHERE idCampagna = ?")) {
            statement.setInt(1, entity.getIdCampagna());
            ret = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
    public Campagna extract(final ResultSet resultSet, final String alias)
            throws SQLException {
        Campagna c = new Campagna();
        c.setIdCampagna(resultSet.getInt(alias + ".idCampagna"));
        c.setStato(StatoCampagna.valueOf(
                resultSet.getString(alias + ".Stato").toUpperCase()));
        c.setTitolo(resultSet.getString(alias + ".titolo"));
        c.setDescrizione(resultSet.getString(alias + ".descrizione"));
        c.setSommaRaccolta(resultSet.getDouble(alias + ".sommaRaccolta"));
        c.setSommaTarget(resultSet.getDouble(alias + ".sommaTarget"));
        Categoria c1 = new Categoria();
        c1.setIdCategoria(resultSet.getInt(alias + ".idCategoria"));
        c.setCategoria(c1);
        Utente utente = new Utente();
        utente.setIdUtente(resultSet.getInt(alias + ".idUtente"));
        c.setUtente(utente);
        return c;
    }

    /**
     * Permette la ricerca di campagne.
     *
     * @param text una stringa per effettuare la ricerca
     * @return la lista di campagne che soddisfano il parametro passato
     */
    public List<Campagna> getByKeyword(final String text) {
        List<Campagna> campagnaList = null;

        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM campagna c where UPPER("
                             + "'%'?'%') LIKE UPPER(CONCAT(c.idCampagna, "
                             + "c.titolo, c.descrizione))"
             )) {

            statement.setString(1, text);

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                campagnaList.add(extract(set, "c"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return campagnaList;
    }
}
