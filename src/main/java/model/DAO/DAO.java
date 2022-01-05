package model.DAO;

import java.sql.ResultSet;
import java.util.List;

public interface DAO<T> {
   /**
    * @param id rappresenta l'identificativo dell'entity
    * @return null se non viene trovato nessun risultato,
    * un'instanza di T nel caso in cui viene trovato un risultato
    */
   T getById(int id);

   /**
    * @return una lista di entity T
    */
   List<T> getAll();

   boolean save(T entity);

   boolean update(T entity);

   boolean delete(T entity);

   T extractor(ResultSet resultSet, String alias);
}
