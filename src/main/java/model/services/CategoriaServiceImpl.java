package model.services;

import model.DAO.CategoriaDAO;
import model.beans.Categoria;

import java.util.List;

public final class CategoriaServiceImpl implements CategoriaService {
    @Override
    public boolean inserisciCategoria(final Categoria categoria) {
        if (categoria == null || categoria.getNome() == null) {
            throw new IllegalArgumentException("Categoria null");
        }

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

    @Override
    public Categoria visualizzaCategoria(final Categoria categoria) {
        return new CategoriaDAO().getById(categoria.getIdCategoria());
    }

}
