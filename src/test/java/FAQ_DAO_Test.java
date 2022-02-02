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
   public void getById() {
      FAQ faq = new FAQ();
      faq.setDomanda("Domanda");
      faq.setRisposta("Risposta");
      faq.setUtenteCreatore(utente);


   }

   @After
   public void clean() {
      ConPool.getInstance().closeDataSource();
   }
}
