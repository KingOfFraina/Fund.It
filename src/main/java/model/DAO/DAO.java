package model.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
   /**
    * @param id rappresenta l'identificativo dell'entity
    * @return null se non viene trovato nessun risultato,
    * un'instanza nel caso in cui viene trovato un risultato
    */
   T getById(int id);

   /**
    * @return
    */
   List<T> getAll();

   boolean save(T entity);

   boolean update(T entity);

   boolean delete(T entity);
}
