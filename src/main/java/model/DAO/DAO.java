package model.DAO;

import java.util.List;

public interface DAO<T> {
   /**
    * @param id rappresenta l'identificativo dell'entity
    * @return null se non viene trovato nessun risultato,
    * un'istanza di T nel caso in cui viene trovato un risultato
    */
   T getById(int id);

   /**
    * @return una lista di entity T
    */
   List<T> getAll();

   /**
    * @param entity l'istanza da salvare
    * @return false --> se l'operazione non va a buon fine,
    * true --> se l'operazione va a buon fine
    */
   boolean save(T entity);

   /**
    * @param entity l'istanza da aggiornare
    * @return false --> se l'operazione non va a buon fine,
    * true --> se l'operazione va a buon fine
    */
   boolean update(T entity);

   /**
    * @param entity l'istanza da eliminare
    * @return false --> se l'operazione non va a buon fine,
    * true --> se l'operazione va a buon fine
    */
   boolean delete(T entity);
}
