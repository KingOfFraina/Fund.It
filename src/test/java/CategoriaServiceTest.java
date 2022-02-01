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

import java.util.List;


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
    public void testIdBelowZero() {
        int id = -1;
        Categoria c = new Categoria();
        c.setIdCategoria(id);
        assertThrows(IllegalArgumentException.class,
                () -> service.visualizzaCategoria(c));
    }

    @Test
    public void testIdGreaterThanZero() {
        int id = 1;
        Categoria c = new Categoria();
        c.setIdCategoria(id);
        when(dao.getById(id)).thenReturn(c);

        assertEquals(c, service.visualizzaCategoria(c));
    }

    @Test
    public void testEntityNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.visualizzaCategoria(null));
    }

    @Test
    public void testGetAll() {
        Categoria c1 = new Categoria();
        Categoria c2 = new Categoria();
        Categoria c3 = new Categoria();
        Categoria c4 = new Categoria();
        c1.setIdCategoria(1);
        c2.setIdCategoria(2);
        c3.setIdCategoria(3);
        c4.setIdCategoria(4);
        List<Categoria> immutableList = List.of(
                c1, c2, c3, c4
        );
        when(dao.getAll()).thenReturn(immutableList);

        assertEquals(immutableList, service.visualizzaCategorie());
    }

    @Test
    public void testUpdateNullArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.modificaCategoria(null));
    }

    @Test
    public void testUpdateNotNull() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNome("Nomone");
        when(dao.update(categoria)).thenReturn(true);

        assertTrue(service.modificaCategoria(categoria));
    }

    @Test
    public void testUpdateNameGreaterThan100() {
        Categoria categoria = new Categoria();
        String name = "aaa" +
                "aaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        categoria.setNome(name);

        assertThrows(IllegalArgumentException.class,
                () -> service.modificaCategoria(categoria));
    }

    @Test
    public void testInsertNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.inserisciCategoria(null));
    }

    @Test
    public void testInsertNameNull() {
        Categoria categoria = new Categoria();
        categoria.setNome(null);
        assertThrows(IllegalArgumentException.class,
                () -> service.inserisciCategoria(categoria));
    }

    @Test
    public void testInsertNameGreaterThan100() {
        Categoria categoria = new Categoria();
        String randomString = "aaa" +
                "aaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        categoria.setNome(randomString);
        assertThrows(IllegalArgumentException.class,
                () -> service.inserisciCategoria(categoria));
    }

    @Test
    public void testSaveCategoria() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNome("aaa" +
                "aaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaa");
        when(dao.save(categoria)).thenReturn(false);

        assertFalse(service.inserisciCategoria(categoria));
    }
}
