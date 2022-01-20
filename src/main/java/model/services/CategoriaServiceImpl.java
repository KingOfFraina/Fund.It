package model.services;

import model.DAO.CategoriaDAO;
import model.beans.Categoria;

import java.util.List;

public final class CategoriaServiceImpl implements CategoriaService {
   @Override
   public boolean inserisciCategoria(final Categoria categoria) {
      return new CategoriaDAO().save(categoria);
   }

   @Override
   public boolean modificaCategoria(final Categoria categoria) {
      return new CategoriaDAO().update(categoria);
   }

   @Override
   public List<Categoria> visualizzaCategorie() {
      return new CategoriaDAO().getAll();
   }
}
