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
        if (id <= 0) {
            throw new IllegalArgumentException("Id <= 0");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("SELECT"
                         + " * FROM campagna "
                         + " WHERE idCampagna = ?")) {
                int index = 1;

                statement.setInt(index, id);

                ResultSet set = statement.executeQuery();
                Campagna campagna = null;
                if (set.next()) {
                    campagna = extract(set);
                }
                return campagna;
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * @param idUtente id dell'utente della quale si vogliono
     *                 trovare le campagne
     * @return lista di campagne create dall'utente
     */
    public List<Campagna> getByIdUtente(final int idUtente) {

        if (idUtente <= 0) {
            throw new IllegalArgumentException("Id <= 0");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("SELECT *"
                                 + " FROM campagna"
                                 + " WHERE idUtente = ?")) {

                statement.setInt(1, idUtente);
                List<Campagna> list = new ArrayList<>();

                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    list.add(extract(set));
                }

                return list;

            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * @return una lista di entity T
     */
    @Override
    public List<Campagna> getAll() {
        try (Connection connection = ConPool.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * "
                             + "FROM campagna ")) {
            List<Campagna> list = new ArrayList<>();
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                list.add(extract(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
        }
    }

    /**
     * @param entity l'istanza da salvare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
    @Override
    public boolean save(final Campagna entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("INSERT INTO campagna "
                                         + "(STATO, TITOLO, DESCRIZIONE,"
                                         + " SOMMARACCOLTA, SOMMATARGET,"
                                         + " IDCATEGORIA, IDUTENTE)"
                                         + "VALUES (?,?,?,?,?,?,?)",
                                 PreparedStatement.RETURN_GENERATED_KEYS)) {

                int index = 1;
                statement.setString(index++, entity.getStato().toString());
                statement.setString(index++, entity.getTitolo());
                statement.setString(index++, entity.getDescrizione());
                statement.setDouble(index++, entity.getSommaRaccolta());
                statement.setDouble(index++, entity.getSommaTarget());
                statement.setInt(index++,
                        entity.getCategoria().getIdCategoria());
                statement.setInt(index, entity.getUtente().getIdUtente());

                int ret = statement.executeUpdate();
                ResultSet set = statement.getGeneratedKeys();
                if (set.next()) {
                    entity.setIdCampagna(set.getInt(1));
                }

                return ret > 0;
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * @param entity l'istanza da aggiornare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
    @Override
    public boolean update(final Campagna entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE campagna SET"
                                 + " Stato = ?, titolo = ?, descrizione = ?,"
                                 + " sommaRaccolta = ?, sommaTarget = ?,"
                                 + " idCategoria = ?"
                                 + " WHERE idCampagna = ?")) {
                int index = 0;
                statement.setString(++index, entity.getStato().toString());
                statement.setString(++index, entity.getTitolo());
                statement.setString(++index, entity.getDescrizione());
                statement.setDouble(++index, entity.getSommaRaccolta());
                statement.setDouble(++index, entity.getSommaTarget());
                statement.setInt(++index,
                        entity.getCategoria().getIdCategoria());
                statement.setInt(++index, entity.getIdCampagna());

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * @param entity l'istanza da eliminare
     * @return false --> se l'operazione non va a buon fine,
     * true --> se l'operazione va a buon fine
     */
    @Override
    public boolean delete(final Campagna entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("DELETE FROM campagna"
                                 + " WHERE idCampagna = ?")) {
                statement.setInt(1, entity.getIdCampagna());

                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * @param resultSet resultSet della query eseguita
     * @return l'istanza della della classe T popolata con le informazioni
     * presenti nel resultSet
     * @throws SQLException eccezione lanciata in caso di problemi
     */
    @Override
    public Campagna extract(final ResultSet resultSet)
            throws SQLException {

        if (resultSet == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            Campagna c = new Campagna();
            c.setIdCampagna(resultSet.getInt("idCampagna"));
            c.setStato(StatoCampagna.valueOf(
                    resultSet.getString("Stato").toUpperCase()));
            c.setTitolo(resultSet.getString("titolo"));
            c.setDescrizione(resultSet.getString("descrizione"));
            c.setSommaRaccolta(resultSet.getDouble("sommaRaccolta"));
            c.setSommaTarget(resultSet.getDouble("sommaTarget"));
            Categoria c1 = new Categoria();
            c1.setIdCategoria(resultSet.getInt("idCategoria"));
            c.setCategoria(c1);
            Utente utente = new Utente();
            utente.setIdUtente(resultSet.getInt("idUtente"));
            c.setUtente(utente);
            return c;
        }
    }

    /**
     * Permette la ricerca di campagne.
     *
     * @param text una stringa per effettuare la ricerca
     * @return la lista di campagne che soddisfano il parametro passato
     */
    public List<Campagna> getByKeyword(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT * FROM campagna "
                                 + " where UPPER(CONCAT"
                                 + "(idCampagna, titolo, descrizione)) "
                                 + "LIKE UPPER(?)"
                 )) {
                List<Campagna> campagnaList = new ArrayList<>();
                statement.setString(1, "%" + text + "%");

                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    campagnaList.add(extract(set));
                }

                return campagnaList;

            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * Permette la ricerca di campagne.
     *
     * @param category una stringa per effettuare la ricerca
     * @return la lista di campagne che soddisfano il parametro passato
     */
    public List<Campagna> getByCategory(final String category) {
        if (category == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT * FROM campagna c "
                                 + "LEFT JOIN categoria ca "
                                 + "ON c.idCategoria = ca.idCategoria"
                                 + " WHERE ca.nomeCategoria = ?"
                 )) {
                List<Campagna> campagnaList = new ArrayList<>();
                statement.setString(1, category);

                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    campagnaList.add(extract(set));
                }

                return campagnaList;

            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    /**
     * Permette la ricerca di una porzione precisa delle campagne presenti.
     *
     * @param size   il numero di campagne da recuperare
     * @param offset il punto da cui iniziare il recupero
     * @return la lista delle campagne che soddisfano i parametri passati
     */
    public List<Campagna> getBySizeOffset(final int size, final int offset) {
        if (size <= 0 || offset < 0) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection connection = ConPool.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT * FROM campagna LIMIT ?, ?"
                 )) {

                int index = 1;
                List<Campagna> campagnaList = new ArrayList<>();
                statement.setInt(index++, offset);
                statement.setInt(index, size);

                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    campagnaList.add(extract(set));
                }
                return campagnaList;
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }
}
