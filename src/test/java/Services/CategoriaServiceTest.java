package Services;

import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.beans.Categoria;
import model.services.CategoriaService;
import model.services.CategoriaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoriaServiceTest {
   Categoria categoria;

   @Before
   public void setup() {
      categoria = new Categoria();
      categoria.setIdCategoria(1);
      categoria.setNome("Nome Categoria");
   }

   @Test
   public void inserisciCategoria1() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertThrows(IllegalArgumentException.class, () -> categoriaService.inserisciCategoria(null));
   }

   @Test
   public void inserisciCategoria2() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      categoria.setNome(null);

      assertThrows(IllegalArgumentException.class, () -> categoriaService.inserisciCategoria(categoria));
   }

   @Test
   public void inserisciCategoria3() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      categoria.setNome("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

      assertThrows(IllegalArgumentException.class, () -> categoriaService.inserisciCategoria(categoria));
   }

   @Test
   public void inserisciCategoria4() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      Mockito.when(categoriaDAO.save(categoria)).thenReturn(false);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertFalse(categoriaService.inserisciCategoria(categoria));
   }

   @Test
   public void inserisciCategoria5() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      Mockito.when(categoriaDAO.save(categoria)).thenReturn(true);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertTrue(categoriaService.inserisciCategoria(categoria));
   }

   @Test
   public void modificaCategoria1() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertThrows(IllegalArgumentException.class, () -> categoriaService.modificaCategoria(null));
   }

   @Test
   public void modificaCategoria2() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      categoria.setNome(null);

      assertThrows(IllegalArgumentException.class, () -> categoriaService.modificaCategoria(categoria));
   }

   @Test
   public void modificaCategoria3() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      categoria.setNome("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

      assertThrows(IllegalArgumentException.class, () -> categoriaService.modificaCategoria(categoria));
   }

   @Test
   public void modificaCategoria4() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      Mockito.when(categoriaDAO.update(categoria)).thenReturn(false);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertFalse(categoriaService.modificaCategoria(categoria));
   }

   @Test
   public void modificaCategoria5() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      Mockito.when(categoriaDAO.update(categoria)).thenReturn(true);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertTrue(categoriaService.modificaCategoria(categoria));
   }

   @Test
   public void visualizzaCategoria1() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertThrows(IllegalArgumentException.class, () -> categoriaService.visualizzaCategoria(null));
   }

   @Test
   public void visualizzaCategoria2() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      categoria.setIdCategoria(0);

      assertThrows(IllegalArgumentException.class, () -> categoriaService.visualizzaCategoria(categoria));
   }

   @Test
   public void visualizzaCategoria3() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      Mockito.when(categoriaDAO.getById(categoria.getIdCategoria())).thenReturn(null);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);

      assertNull(categoriaService.visualizzaCategoria(categoria));
   }

   @Test
   public void visualizzaCategoria4() {
      DAO<Categoria> categoriaDAO = Mockito.mock(CategoriaDAO.class);
      Mockito.when(categoriaDAO.getById(categoria.getIdCategoria())).thenReturn(categoria);
      CategoriaService categoriaService = new CategoriaServiceImpl(categoriaDAO);
      Categoria categoriaDB = categoriaService.visualizzaCategoria(categoria);

      assertAll(
              () -> assertNotNull(categoriaDB),
              () -> assertEquals(categoria.getIdCategoria(), categoriaDB.getIdCategoria()),
              () -> assertEquals(categoria.getNome(), categoriaDB.getNome())
      );
   }

}
