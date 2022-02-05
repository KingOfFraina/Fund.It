package Services;

import model.DAO.DAO;
import model.DAO.FaqDAO;
import model.beans.FAQ;
import model.beans.Utente;
import model.services.FaqService;
import model.services.FaqServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FaqServiceTest {
   FaqService faqService;
   DAO<FAQ> faqDAO;
   FAQ faq;

   @Before
   public void setup() {
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
   public void modificaFaqTrue() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.update(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);

      assertTrue(faqService.modificaFaq(faq));
   }

   @Test
   public void modificaFaqFalse() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.update(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);

      assertFalse(faqService.modificaFaq(faq));
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
   public void visualizzaFaqParamNull() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.getById(faq.getIdFaq())).thenReturn(null);
      faqService = new FaqServiceImpl(faqDAO);

      assertNull(faqService.visualizzaFaq(faq.getIdFaq()));
   }

  @Test
   public void visualizzaFaqParam() {
      faqDAO = Mockito.mock(FaqDAO.class);
     Mockito.when(faqDAO.getById(faq.getIdFaq())).thenReturn(faq);
      faqService = new FaqServiceImpl(faqDAO);
      FAQ faqDB = faqService.visualizzaFaq(faq.getIdFaq());

      assertAll(
              () -> assertNotNull(faqDB),
              () -> assertEquals(faqDB.getIdFaq(), faq.getIdFaq()),
              () -> assertEquals(faqDB.getDomanda(), faq.getDomanda()),
              () -> assertEquals(faqDB.getRisposta(), faq.getRisposta())
      );
   }

   @Test
   public void visualizzaFaqEmptySet() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.getAll()).thenReturn(new ArrayList<>());
      faqService = new FaqServiceImpl(faqDAO);
      List<FAQ> faqList = faqService.visualizzaFaq();

      assertAll(
              () -> assertNotNull(faqList),
              () -> assertEquals(0, faqList.size())
      );
   }

   @Test
   public void visualizzaFaq() {
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.getAll()).thenReturn(List.of(faq));
      faqService = new FaqServiceImpl(faqDAO);
      List<FAQ> faqList = faqService.visualizzaFaq();

      assertAll(
              () -> assertNotNull(faqList),
              () -> assertTrue(faqList.size() > 0),
              () -> assertEquals(faq.getIdFaq(), faqList.get(0).getIdFaq()),
              () -> assertEquals(faq.getDomanda(), faqList.get(0).getDomanda()),
              () -> assertEquals(faq.getRisposta(), faqList.get(0).getRisposta())
      );
   }
}
