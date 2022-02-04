package DAO;

import model.DAO.DAO;
import model.DAO.FaqDAO;
import model.DAO.UtenteDAO;
import model.beans.FAQ;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FaqDAOTest {
   static DAO<FAQ> faqDAO;
   static DAO<Utente> utenteDAO;
   static Utente utente;
   static FAQ faq;

   @BeforeClass
   public static void openConnection() throws SQLException {
      ConPool.getInstance().getConnection();

      faqDAO = new FaqDAO();
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

      faq = new FAQ();
      faq.setDomanda("");
      faq.setRisposta("");
      faq.setUtenteCreatore(utente);

      faqDAO.save(faq);
   }

   @Test
   public void saveNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.save(null);
      });
   }

   @Test
   public void saveEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         faqDAO.save(new FAQ());
      });
   }

   @Test
   public void savePartialObject() {
      faq.setRisposta(null);

      assertThrows(RuntimeException.class, () -> {
         faqDAO.save(faq);
      });

      faq.setRisposta("");
   }

   @Test
   public void saveObject() {
      faqDAO.delete(faq);

      assertTrue(faqDAO.save(faq));
   }

   @Test
   public void getByIdInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.getById(-1);
      });
   }

   @Test
   public void getById() {
      FAQ faqDB = faqDAO.getById(faq.getIdFaq());

      assertAll(
              () -> assertNotNull(faqDB),
              () -> assertEquals(faq.getIdFaq(), faqDB.getIdFaq()),
              () -> assertEquals(faq.getDomanda(), faqDB.getDomanda()),
              () -> assertEquals(faq.getRisposta(), faqDB.getRisposta()),
              () -> assertEquals(faq.getUtenteCreatore().getIdUtente(),
                      faqDB.getUtenteCreatore().getIdUtente())
      );
   }

   @Test
   public void updateNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.update(null);
      });
   }

   @Test
   public void updateEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         faqDAO.update(new FAQ());
      });
   }

   @Test
   public void update() {
      faq.setDomanda("DomandaCambiata");
      faq.setRisposta("RispostaCambiata");

      assertTrue(faqDAO.update(faq));

      faq.setDomanda("");
      faq.setRisposta("");

      assertTrue(faqDAO.update(faq));
   }


   @Test
   public void updatePartialObject() {
      faq.setRisposta(null);

      assertThrows(RuntimeException.class, () -> {
         faqDAO.update(faq);
      });

      faq.setRisposta("");
   }

   @Test
   public void getAll() {
      List<FAQ> faqList = faqDAO.getAll();

      assertAll(
              () -> assertNotNull(faqList),
              () -> assertTrue(faqList.size() > 0)
      );
   }

   @Test
   public void deleteEntityNull() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.delete(null);
      });
   }

   @Test
   public void delete() {
      assertTrue(faqDAO.delete(faq));

      faqDAO.save(faq);
   }

   @Test
   public void extractNullResultSet() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.extract(null);
      });
   }

   @AfterClass
   public static void clear() {
      faqDAO.delete(faq);
      utenteDAO.delete(utente);
      ConPool.getInstance().closeDataSource();
   }
}
