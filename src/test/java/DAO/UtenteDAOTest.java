package DAO;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteDAOTest {
   static DAO<Utente> utenteDAO;
   static Utente utente;

   @BeforeClass
   public static void setup() throws SQLException {
      ConPool.getInstance().getConnection();

      utenteDAO = new UtenteDAO();
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
   }

   @Test
   public void extractNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.extract(null);
      });
   }

   @Test
   public void deleteNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.delete(null);
      });
   }

   @Test
   public void deleteEmptyObject() {
      assertFalse(utenteDAO.delete(new Utente()));
   }

   @Test
   public void delete() {
      assertTrue(utenteDAO.delete(utente));

      utenteDAO.save(utente);
   }

   @Test
   public void saveNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.save(null);
      });
   }

   @Test
   public void saveEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         utenteDAO.save(new Utente());
      });
   }

   @Test
   public void savePartialObject() {
      utente.setDataDiNascita(null);
      assertThrows(RuntimeException.class, () -> {
         utenteDAO.save(utente);
      });

      utente.setDataDiNascita(LocalDate.now());
   }

   @Test
   public void save() {
      utenteDAO.delete(utente);

      assertTrue(utenteDAO.save(utente));
   }

   @Test
   public void updateNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.save(null);
      });
   }

   @Test
   public void updateEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         utenteDAO.update(new Utente());
      });
   }

   @Test
   public void updatePartialObject() {
      utente.setDataDiNascita(null);
      assertThrows(RuntimeException.class, () -> {
         utenteDAO.update(utente);
      });

      utente.setDataDiNascita(LocalDate.now());
   }

   @Test
   public void update() {
      utente.setDataBan(LocalDateTime.now());
      assertTrue(utenteDAO.update(utente));
   }

   @Test
   public void getAll() {
      List<Utente> utenteList = utenteDAO.getAll();

      assertAll(
              () -> assertNotNull(utenteList),
              () ->assertTrue(utenteList.size() > 0)
      );
   }

   @Test
   public void getByIdInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.getById(-1);
      });
   }

   @Test
   public void getById() {
      assertNotNull(utenteDAO.getById(utente.getIdUtente()));
   }

   @Test
   public void doLoginNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         ((UtenteDAO)utenteDAO).doLogin(null);
      });
   }

   @Test
   public void doLoginNotExistUser() {
      Utente utente = new Utente();
      utente.setEmail("");
      utente.setPassword("");

      assertNull(((UtenteDAO)utenteDAO).doLogin(utente));
   }

   @Test
   public void doLoginExistUser() {
      assertNotNull(((UtenteDAO)utenteDAO).doLogin(utente));
   }

   @Test
   public void fillPreparedStatementEmptyObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         ((UtenteDAO)utenteDAO).fillPreparedStatement(null, null);
      });
   }

   @AfterClass
   public static void closeConnection() {
      utenteDAO.delete(utente);
      ConPool.getInstance().closeDataSource();
   }
}
