package model.DAO;

import java.util.List;

public interface DAO<T> {
   T getById(int id);

   List<T> getAll();

   boolean save(T entity);

   boolean update(T entity);

   boolean delete(T entity);
}
