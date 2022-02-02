import model.DAO.DAO;
import model.DAO.FaqDAO;
import model.beans.FAQ;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class FAQ_DAO_Test {

   DAO faqDAO;
   Utente utente;

   @Before
   public void setup() throws SQLException {
      ConPool.getInstance().getConnection();
      faqDAO = new FaqDAO();
      utente = new Utente();
      utente.setIdUtente(1);
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
   public void saveObject() {
      FAQ faq = new FAQ();
      faq.setDomanda("");
      faq.setRisposta("");
      faq.setUtenteCreatore(utente);

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
      FAQ faq = new FAQ();
      faq.setDomanda("Domanda");
      faq.setRisposta("Risposta");
      faq.setUtenteCreatore(utente);
      if (faqDAO.save(faq)) {
         FAQ faqDB = (FAQ) faqDAO.getById(faq.getIdFaq());

         assertAll(
                 () -> assertNotNull(faqDB),
                 () -> assertEquals(faq.getIdFaq(), faqDB.getIdFaq()),
                 () -> assertEquals(faq.getDomanda(), faqDB.getDomanda()),
                 () -> assertEquals(faq.getRisposta(), faqDB.getRisposta()),
                 () -> assertEquals(faq.getUtenteCreatore().getIdUtente(),
                         faqDB.getUtenteCreatore().getIdUtente())
         );

         faqDAO.delete(faqDB);
      }
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
      FAQ faq = new FAQ();
      faq.setDomanda("Domanda");
      faq.setRisposta("Risposta");
      faq.setUtenteCreatore(utente);
      if (faqDAO.save(faq)) {
         faq.setDomanda("DomandaCambiata");
         faq.setRisposta("RispostaCambiata");

         assertTrue(faqDAO.update(faq));
         FAQ faqDB = (FAQ) faqDAO.getById(faq.getIdFaq());

         assertAll(
                 () -> assertNotNull(faqDB),
                 () -> assertEquals(faq.getIdFaq(), faqDB.getIdFaq()),
                 () -> assertEquals("DomandaCambiata", faq.getDomanda()),
                 () -> assertEquals("RispostaCambiata", faq.getRisposta()),
                 () -> assertEquals(faq.getUtenteCreatore().getIdUtente(),
                         faqDB.getUtenteCreatore().getIdUtente())
         );

         faqDAO.delete(faqDB);
      }
   }

   @Test
   public void getAll() {
      assertNotNull(faqDAO.getAll());
   }

   @Test
   public void deleteEntityNull() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.delete(null);
      });
   }

   @Test
   public void delete() {
      FAQ faq = new FAQ();
      faq.setDomanda("Domanda");
      faq.setRisposta("Risposta");
      faq.setUtenteCreatore(utente);

      assertAll(
              () -> assertTrue(faqDAO.save(faq)),
              () -> assertNotNull(faqDAO.getById(faq.getIdFaq())),
              () -> assertTrue(faqDAO.delete(faq)),
              () -> assertNull(faqDAO.getById(faq.getIdFaq()))
      );
   }

   @Test
   public void extractNullResultSet() {
      assertThrows(IllegalArgumentException.class, () -> {
         faqDAO.extract(null);
      });
   }

   @After
   public void clean() {
      ConPool.getInstance().closeDataSource();
   }
}
