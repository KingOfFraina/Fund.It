package model.services;

import model.DAO.DAO;
import model.beans.Categoria;

import java.util.List;

public final class CategoriaServiceImpl implements CategoriaService {

    private final DAO<Categoria> dao;

    public CategoriaServiceImpl(final DAO<Categoria> categoriaDAO) {
        this.dao = categoriaDAO;
    }

    @Override
    public boolean inserisciCategoria(final Categoria categoria) {
        if (categoria == null || categoria.getNome() == null) {
            throw new IllegalArgumentException("Categoria null");
        }

        return dao.save(categoria);
    }

    @Override
    public boolean modificaCategoria(final Categoria categoria) {
        return dao.update(categoria);
    }

    @Override
    public List<Categoria> visualizzaCategorie() {
        return dao.getAll();
    }

    @Override
    public Categoria visualizzaCategoria(final Categoria categoria) {
        return dao.getById(categoria.getIdCategoria());
    }

}
