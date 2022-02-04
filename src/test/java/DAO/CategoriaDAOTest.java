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
   static DAO<Categoria> categoriaDAO;
   static Categoria categoria;

   @BeforeClass
   public static void setup() throws SQLException {
      ConPool.getInstance().getConnection();

      categoriaDAO = new CategoriaDAO();

      categoria = new Categoria();
      categoria.setNome("nomeCategoria");

      categoriaDAO.save(categoria);
   }

   @Test
   public void getByIdError() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.getById(0);
      });
   }

   @Test
   public void getById() {
      categoria.setNome("nomeCategoria");
      categoriaDAO.update(categoria);

      Categoria categoriaDB = categoriaDAO.getById(categoria.getIdCategoria());

      assertAll(
              () -> assertNotNull(categoriaDB),
              () -> assertEquals(categoria.getNome(), categoriaDB.getNome()),
              () -> assertEquals(categoria.getIdCategoria(), categoriaDB.getIdCategoria())
      );
   }

   @Test
   public void getAll() {
      List<Categoria> categoriaList = categoriaDAO.getAll();

      assertAll(
              () -> assertNotNull(categoriaList),
              () -> assertTrue(categoriaList.size() > 0)
      );
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
     assertTrue(categoriaDAO.delete(categoria));

      categoriaDAO.save(categoria);
   }

   @Test
   public void updateNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         categoriaDAO.update(null);
      });
   }

   @Test
   public void updateEmptyObject() {

      assertThrows(RuntimeException.class, () -> {
         categoria.setNome(null);
         categoriaDAO.update(categoria);
      });

      categoria.setNome("nomeCategoria");
   }

   @Test
   public void update() {
      categoria.setNome("---");

      assertTrue(categoriaDAO.update(categoria));

      categoria.setNome("nomeCategoria");
   }

   @Test
   public void updatePartialObject() {

      assertThrows(RuntimeException.class, () -> {
         categoria.setNome(null);
         categoriaDAO.update(categoria);
      });

      categoria.setNome("nomeCategoria");
   }

   @AfterClass
   public static void clear() {
      categoriaDAO.delete(categoria);
      ConPool.getInstance().closeDataSource();
   }
}
