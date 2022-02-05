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
   Utente utente;

   @Before
   public void setup() {
      faq = new FAQ();
      faq.setIdFaq(1);
      faq.setDomanda("");
      faq.setRisposta("");
      utente = new Utente();
      faq.setUtenteCreatore(utente);
   }

   @Test
   public void inserisciFaq1() {
      utente.setAdmin(true);
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.save(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);

      assertFalse(faqService.inserisciFaq(faq));
   }

   @Test
   public void inserisciFaq2() {
      utente.setAdmin(true);
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.save(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);

      assertTrue(faqService.inserisciFaq(faq));
   }

   @Test
   public void inserisciFaq3() {
      utente.setAdmin(false);
      faqService = new FaqServiceImpl(null);

      assertThrows(IllegalCallerException.class, () -> faqService.inserisciFaq(faq));
   }

   @Test
   public void modificaFaq1() {
      utente.setAdmin(true);
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.update(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);

      assertTrue(faqService.modificaFaq(faq));
   }

   @Test
   public void modificaFaq2() {
      utente.setAdmin(true);
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.update(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);

      assertFalse(faqService.modificaFaq(faq));
   }

   @Test
   public void modificaFaq3() {
      utente.setAdmin(false);
      faqService = new FaqServiceImpl(null);

      assertThrows(IllegalCallerException.class, () -> faqService.modificaFaq(faq));
   }

   @Test
   public void cancellaFaq1() {
      utente.setAdmin(true);
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.delete(faq)).thenReturn(false);
      faqService = new FaqServiceImpl(faqDAO);

      assertFalse(faqService.cancellaFaq(faq));
   }

   @Test
   public void cancellaFaq2() {
      utente.setAdmin(true);
      faqDAO = Mockito.mock(FaqDAO.class);
      Mockito.when(faqDAO.delete(faq)).thenReturn(true);
      faqService = new FaqServiceImpl(faqDAO);

      assertTrue(faqService.cancellaFaq(faq));
   }

   @Test
   public void cancellaFaq3() {
      utente.setAdmin(false);
      faqService = new FaqServiceImpl(null);

      assertThrows(IllegalCallerException.class, () -> faqService.cancellaFaq(faq));
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
