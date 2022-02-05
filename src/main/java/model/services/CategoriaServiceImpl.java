package model.services;

import model.DAO.DAO;
import model.beans.Categoria;

import java.util.List;

public final class CategoriaServiceImpl implements CategoriaService {
   /**
    * Vincolo sulla lunghezza del campo nomeCategoria
    * della tabella categoria.
    */
   private static final int MAX_NAME_LEN = 100;
   /**
    * Wrapper di CategoriaDAO.
    */
   private final DAO<Categoria> dao;

   /**
    * @param categoriaDAO istanza di CategoriaDAO
    */
   public CategoriaServiceImpl(final DAO<Categoria> categoriaDAO) {
      this.dao = categoriaDAO;
   }

   @Override
   public boolean inserisciCategoria(final Categoria categoria) {
      if (categoria == null || categoria.getNome() == null
              || categoria.getNome().length() >= MAX_NAME_LEN) {
         throw new IllegalArgumentException("Invalid argument");
      } else {
         return dao.save(categoria);
      }
   }

   @Override
   public boolean modificaCategoria(final Categoria categoria) {
      if (categoria == null || categoria.getNome() == null
              || categoria.getNome().length() >= MAX_NAME_LEN) {
          throw new IllegalArgumentException("Invalid argument");
      } else {
          return dao.update(categoria);
      }
   }

   @Override
   public List<Categoria> visualizzaCategorie() {
      return dao.getAll();
   }

   @Override
   public Categoria visualizzaCategoria(final Categoria categoria) {
      if (categoria == null || categoria.getIdCategoria() <= 0) {
          throw new IllegalArgumentException("Invalid argument");
      } else {
          return dao.getById(categoria.getIdCategoria());
      }
   }
}
