package DAO;

import model.DAO.CampagnaDAO;
import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.StatoCampagna;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CampagnaDAOTest {
   static DAO<Campagna> campagnaDAO;
   static DAO<Utente> utenteDAO;
   static DAO<Categoria> categoriaDAO;
   static Utente utente;
   static Campagna campagna;
   static Categoria categoria;

   @BeforeClass
   public static void setConnection() throws SQLException {
      ConPool.getInstance().getConnection();

      utenteDAO = new UtenteDAO();
      campagnaDAO = new CampagnaDAO();
      categoriaDAO = new CategoriaDAO();

      utente = new Utente();

      utente.setAdmin(true);
      utente.setCap("cap");
      utente.setCf("cf");
      utente.setCitta("città");
      utente.setCognome("cognome");
      utente.setDataBan(LocalDateTime.now());
      utente.setDataDiNascita(LocalDate.now());
      utente.setEmail("email");
      utente.setFotoProfilo("fotoProfilo");
      utente.setNome("nome");
      utente.setPassword("passwordhash");
      utente.setStrada("strada");
      utente.setTelefono("telefono");
      utente.setCampagne(null);
      utente.setDonazioni(null);
      utente.setSegnalazioni(null);

      utenteDAO.save(utente);

      categoria = new Categoria();
      categoria.setNome("");

      categoriaDAO.save(categoria);

      campagnaDAO = new CampagnaDAO();

      campagna = new Campagna();
      campagna.setStato(StatoCampagna.ATTIVA);
      campagna.setTitolo("titolo");
      campagna.setDescrizione("descrizione");
      campagna.setSommaRaccolta(15d);
      campagna.setSommaTarget(15d);
      campagna.setCategoria(categoria);
      campagna.setUtente(utente);
      campagnaDAO.save(campagna);
   }

   @Test
   public void getByIdInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         campagnaDAO.getById(-1);
      });
   }

   @Test
   public void getById() {
      assertNotNull(campagnaDAO.getById(campagna.getIdCampagna()));
   }

   @Test
   public void getByIdUtenteInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         campagnaDAO.getById(-1);
      });
   }

   @Test
   public void getByIdUtente() {
      List<Campagna> campagnaList = ((CampagnaDAO) campagnaDAO).getByIdUtente(campagna.getUtente().getIdUtente());

      assertAll(
              () -> assertNotNull(campagnaList),
              () -> assertTrue(campagnaList.size() > 0)
      );
   }

   @Test
   public void getAll() {
      List<Campagna> campagnaList = (campagnaDAO.getAll());

      assertAll(
              () -> assertNotNull(campagnaList),
              () -> assertTrue(campagnaList.size() > 0)
      );
   }

   @Test
   public void saveNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         campagnaDAO.save(null);
      });
   }

   @Test
   public void saveEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         campagnaDAO.save(new Campagna());
      });
   }

   @Test
   public void savePartialObject() {
      campagna.setStato(null);
      assertThrows(RuntimeException.class, () -> {
         campagnaDAO.save(campagna);
      });

      campagna.setStato(StatoCampagna.ATTIVA);
   }

   @Test
   public void updateNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         campagnaDAO.save(null);
      });
   }

   @Test
   public void updateEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         campagnaDAO.update(new Campagna());
      });
   }

   @Test
   public void updatePartialObject() {
      campagna.setStato(null);
      assertThrows(RuntimeException.class, () -> {
         campagnaDAO.update(campagna);
      });

      campagna.setStato(StatoCampagna.ATTIVA);
   }

   @Test
   public void update() {
      campagna.setStato(StatoCampagna.CHIUSA);
      assertTrue(campagnaDAO.update(campagna));
   }

   @Test
   public void extractNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         campagnaDAO.extract(null);
      });
   }

   @Test
   public void deleteNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         campagnaDAO.delete(null);
      });
   }

   @Test
   public void deleteEmptyObject() {
      assertFalse(campagnaDAO.delete(new Campagna()));
   }

   @Test
   public void delete() {
      assertTrue(campagnaDAO.delete(campagna));
      campagnaDAO.save(campagna);
   }

   @Test
   public void getBySizeOffsetInvalid() {
      assertThrows(IllegalArgumentException.class, () -> {
         ((CampagnaDAO)campagnaDAO).getBySizeOffset(0, 10);
      });
   }

   @Test
   public void getBySizeOffset() {
      List<Campagna> campagnaList = ((CampagnaDAO)campagnaDAO).getBySizeOffset(1, 0);

      assertAll(
              () -> assertNotNull(campagnaList),
              () ->assertTrue(campagnaList.size() > 0)
      );
   }

   @Test
   public void getByKeywordInvalid() {
      assertThrows(IllegalArgumentException.class, () -> {
         ((CampagnaDAO)campagnaDAO).getByKeyword(null);
      });
   }

   @Test
   public void getByKeyword() {
      List<Campagna> campagnaList = ((CampagnaDAO)campagnaDAO).getByKeyword("");

      assertAll(
              () -> assertNotNull(campagnaList),
              () ->assertTrue(campagnaList.size() > 0)
      );
   }

   @AfterClass
   public static void closeConnection() {
      campagnaDAO.delete(campagna);
      utenteDAO.delete(utente);
      categoriaDAO.delete(categoria);
      ConPool.getInstance().closeDataSource();
   }


}
/*
} catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Id <= 0");
      }
 */