import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.beans.Categoria;
import model.storage.ConPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaDAOTest {
   DAO<Categoria> categoriaDAO;

   @Before
   public void setup() throws SQLException {
      ConPool.getInstance().getConnection();

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

      if (categoriaDAO.save(categoria)) {
         Categoria categoriaDB = categoriaDAO.getById(categoria.getIdCategoria());
         assertAll(
                 () -> assertNotNull(categoria),
                 () -> assertEquals(categoria.getNome(), categoriaDB.getNome()),
                 () -> assertEquals(categoria.getIdCategoria(), categoriaDB.getIdCategoria())
         );
      }
   }

   @Test
   public void getAll() {
      assertNotNull(categoriaDAO.getAll());
   }

   @Test
   public void extractNullResultSet() throws SQLException {
      assertNull(categoriaDAO.extract(null));
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

      assertAll(
              () -> assertTrue(categoriaDAO.save(categoria)),
              () -> assertTrue(categoriaDAO.delete(categoria))
      );
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

      assertAll(
              () -> assertTrue(categoriaDAO.save(categoria)),
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

      assertAll(
              () -> assertTrue(categoriaDAO.save(categoria)),

              () -> assertThrows(RuntimeException.class, () -> {
                 categoria.setNome(null);
                 categoriaDAO.update(categoria);
              }),

              () -> assertTrue(categoriaDAO.delete(categoria))
      );
   }

   @Test
   public void update() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      if (categoriaDAO.save(categoria)) {
         categoria.setNome("---");

         assertTrue(categoriaDAO.update(categoria));
         Categoria categoriaDB = (Categoria) categoriaDAO.getById(categoria.getIdCategoria());

         assertAll(
                 () -> assertNotNull(categoriaDB),
                 () -> assertEquals(categoria.getIdCategoria(), categoria.getIdCategoria()),
                 () -> assertEquals("---", categoriaDB.getNome()),
                 () -> assertTrue(categoriaDAO.delete(categoria))
         );
      }
   }

   @Test
   public void updatePartialObject() {
      Categoria categoria = new Categoria();
      categoria.setNome("");

      assertAll(
              () -> assertTrue(categoriaDAO.save(categoria)),
              () -> assertThrows(RuntimeException.class, () -> {
                 categoria.setNome(null);
                 categoriaDAO.update(categoria);
              }),
              () -> assertTrue(categoriaDAO.delete(categoria))
      );
   }

   @After
   public void clean() {
      ConPool.getInstance().closeDataSource();
   }
}
