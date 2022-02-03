import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteDAOTest {
   DAO<Utente> utenteDAO;
   Utente utente;

   @Before
   public void setup() throws SQLException {
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
      utenteDAO.save(utente);

      assertTrue(utenteDAO.delete(utente));
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
      utenteDAO.delete(utente);
   }

   @Test
   public void save() {
      assertTrue(utenteDAO.save(utente));
      utenteDAO.delete(utente);
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
      utenteDAO.save(utente);
      utente.setDataDiNascita(null);
      assertThrows(RuntimeException.class, () -> {
         utenteDAO.update(utente);
      });
      utenteDAO.delete(utente);
   }

   @Test
   public void update() {
      utenteDAO.save(utente);
      utente.setDataBan(LocalDateTime.now());
      assertTrue(utenteDAO.update(utente));
      utenteDAO.delete(utente);
   }

   @Test
   public void getAll() {
      utenteDAO.save(utente);

      List<Utente> utenteList = utenteDAO.getAll();

      assertAll(
              () -> assertNotNull(utenteList),
              () ->assertTrue(utenteList.size() > 0)
      );

      utenteDAO.delete(utente);
   }

   @Test
   public void getByIdInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.getById(-1);
      });
   }

   @Test
   public void getById() {
      utenteDAO.save(utente);

      assertNotNull(utenteDAO.getById(utente.getIdUtente()));

      utenteDAO.delete(utente);
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
      utenteDAO.save(utente);

      assertNotNull(((UtenteDAO)utenteDAO).doLogin(utente));

      utenteDAO.delete(utente);
   }

   @After
   public void clean() {
      ConPool.getInstance().closeDataSource();
   }
}
