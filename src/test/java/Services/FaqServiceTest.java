package Services;

import model.DAO.DAO;
import model.DAO.FaqDAO;
import model.beans.FAQ;
import model.beans.Utente;
import model.services.FaqService;
import model.services.FaqServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FaqServiceTest {
   FaqService faqService;
   DAO<FAQ> faqDAO;
   static FAQ faq;

   @BeforeAll
   public static void setup() {
      faq = new FAQ();
      faq.setIdFaq(1);
      faq.setDomanda("");
      faq.setRisposta("");
      faq.setUtenteCreatore(new Utente());
   }

   @Test
   public void inserisciFaqFalse() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.save(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);
      assertFalse(faqService.inserisciFaq(faq));
   }

   @Test
   public void inserisciFaqTrue() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.save(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);
      assertTrue(faqService.inserisciFaq(faq));
   }

   @Test
   public void cancellaFaqFalse() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.delete(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);
      assertFalse(faqService.cancellaFaq(faq));
   }

   @Test
   public void cancellaFaqTrue() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.delete(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);
      assertTrue(faqService.cancellaFaq(faq));
   }

   @Test
   public void modificaFaqFalse() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.update(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);
      assertFalse(faqService.modificaFaq(faq));
   }

   @Test
   public void modificaFaqTrue() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.update(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);
      assertTrue(faqService.modificaFaq(faq));
   }
}
