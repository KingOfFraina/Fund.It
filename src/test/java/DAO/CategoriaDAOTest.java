package DAO;

import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.beans.Categoria;
import model.storage.ConPool;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaDAOTest {
   DAO<Categoria> categoriaDAO;

   @BeforeClass
   public static void openConnection() throws SQLException {
      ConPool.getInstance().getConnection();
   }

   @Before
   public void setup() {
      categoriaDAO = new CategoriaDAO();
   }

   @Test
   public void getByIdError() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.getById(0);
      });
   }

   @Test
   public void getById() {
      Categoria categoria = new Categoria();
      categoria.setNome("nomeCategoria");

      categoriaDAO.save(categoria);
      Categoria categoriaDB = categoriaDAO.getById(categoria.getIdCategoria());

      assertAll(
              () -> assertNotNull(categoriaDB),
              () -> assertEquals(categoria.getNome(), categoriaDB.getNome()),
              () -> assertEquals(categoria.getIdCategoria(), categoriaDB.getIdCategoria())
      );

      categoriaDAO.delete(categoria);
   }

   @Test
   public void getAll() {
      Categoria categoria = new Categoria();
      categoria.setNome("nomeCategoria");

      categoriaDAO.save(categoria);

      List<Categoria> categoriaList = categoriaDAO.getAll();

      assertAll(
              () -> assertNotNull(categoriaList),
              () -> assertTrue(categoriaList.size() > 0)
      );

      categoriaDAO.delete(categoria);
   }

   @Test
   public void extractNullResultSet() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.extract(null);
      });
   }

   @Test
   public void saveEntityNull() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.save(null);
      });
   }

   @Test
   public void saveEntityEmpty() {
      assertThrows(RuntimeException.class, () -> {
         Categoria categoria = new Categoria();
         categoria.setNome(null);
         categoriaDAO.save(categoria);
      });
   }

   @Test
   public void save() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      assertTrue(categoriaDAO.save(categoria));

      categoriaDAO.delete(categoria);
   }

   @Test
   public void deleteEntityNull() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.delete(null);
      });
   }

   @Test
   public void delete() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      categoriaDAO.save(categoria);

      assertAll(
              () -> assertTrue(categoriaDAO.delete(categoria)),
              () -> assertNull(categoriaDAO.getById(categoria.getIdCategoria()))
      );
   }

   @Test
   public void updateNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.update(null);
      });
   }

   @Test
   public void updateEmptyObject() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      categoriaDAO.save(categoria);

      assertThrows(RuntimeException.class, () -> {
         categoria.setNome(null);
         categoriaDAO.update(categoria);
      });

      categoriaDAO.delete(categoria);
   }

   @Test
   public void update() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      categoriaDAO.save(categoria);
      categoria.setNome("---");

      assertTrue(categoriaDAO.update(categoria));

      categoriaDAO.delete(categoria);
   }

   @Test
   public void updatePartialObject() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      categoriaDAO.save(categoria);

      assertThrows(RuntimeException.class, () -> {
         categoria.setNome(null);
         categoriaDAO.update(categoria);
      });

      categoriaDAO.delete(categoria);
   }

   @AfterClass
   public static void closeConnection() {
      ConPool.getInstance().closeDataSource();
   }
}
