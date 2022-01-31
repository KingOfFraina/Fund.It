import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.beans.Categoria;
import model.services.CategoriaService;
import model.services.CategoriaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoriaServiceTest {
    CategoriaService service;
    DAO<Categoria> dao;

    @Before
    public void setUp() {
        dao = Mockito.mock(CategoriaDAO.class);
        service = new CategoriaServiceImpl(dao);
    }

    @Test
    public void testGetCategoria() {
        int id = -1;
        int id1 = 1;
        Categoria c1 = new Categoria();
        c1.setIdCategoria(id1);
        when(dao.getById(1)).thenReturn(c1);
        Categoria c = new Categoria();
        c.setIdCategoria(id1);
        assertTrue(() -> service.visualizzaCategoria(c).getIdCategoria() == id, "Errore");
    }
}
