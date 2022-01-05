package model.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
   /**
    * @param id rappresenta l'identificativo dell'entity
    * @return null se non viene trovato nessun risultato,
    * un'instanza nel caso in cui viene trovato un risultato
    */
   T getById(int id) throws SQLException;

   List<T> getAll() throws SQLException;

   boolean save(T entity) throws SQLException;

   boolean update(T entity) throws SQLException;

   boolean delete(T entity) throws SQLException;
}
