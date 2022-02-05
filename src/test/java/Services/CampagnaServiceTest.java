package Services;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.StatoCampagna;
import model.beans.Utente;
import model.beans.proxies.CampagnaProxy;
import model.services.CampagnaService;
import model.services.CampagnaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

public class CampagnaServiceTest {
   Campagna campagna;
   Categoria categoria;
   Utente utente;

   @Before
   public void setup() {
      utente = new Utente();
      utente.setIdUtente(1);

      categoria = new Categoria();
      categoria.setIdCategoria(1);
      categoria.setNome("Nome Categoria");

      campagna = new Campagna();
      campagna.setIdCampagna(1);
      campagna.setStato(StatoCampagna.ATTIVA);
      campagna.setTitolo("titolo");
      campagna.setDescrizione("descrizione");
      campagna.setSommaRaccolta(15d);
      campagna.setSommaTarget(15d);
      campagna.setCategoria(categoria);
      campagna.setUtente(utente);
   }

   @Test
   public void creazioneCampagna1() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.creazioneCampagna(null));
   }

   @Test
   public void creazioneCampagna2() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.save(campagna)).thenReturn(false);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertFalse(campagnaService.creazioneCampagna(campagna));
   }

   @Test
   public void creazioneCampagna3() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.save(campagna)).thenReturn(true);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertTrue(campagnaService.creazioneCampagna(campagna));
   }

   @Test
   public void modificaCampagna1() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.modificaCampagna(null));
   }

   @Test
   public void modificaCampagna2() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.update(campagna)).thenReturn(false);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertFalse(campagnaService.modificaCampagna(campagna));
   }

   @Test
   public void modificaCampagna3() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.update(campagna)).thenReturn(true);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertTrue(campagnaService.modificaCampagna(campagna));
   }

   @Test
   public void condividiCampagna1() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.getById(campagna.getIdCampagna())).thenReturn(campagna);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.condividiCampagna(1, null));
   }

   @Test
   public void condividiCampagna2() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.getById(campagna.getIdCampagna())).thenReturn(null);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.condividiCampagna(campagna.getIdCampagna(), Mockito.mock(HttpServletRequest.class)));
   }

   @Test
   public void condividiCampagna3() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      Mockito.when(campagnaDAO.getById(campagna.getIdCampagna())).thenReturn(campagna);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertNotNull(campagnaService.condividiCampagna(campagna.getIdCampagna(), Mockito.mock(HttpServletRequest.class)));
   }

   @Test
   public void rimborsaDonazioni1() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.rimborsaDonazioni(null, null));
   }

   @Test
   public void rimborsaDonazioni2() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.rimborsaDonazioni(new Campagna(), null));
   }

   @Test
   public void rimborsaDonazioni3() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertThrows(IllegalArgumentException.class, () -> campagnaService.rimborsaDonazioni(null, new CampagnaProxy(campagna)));
   }

   @Test
   public void rimborsaDonazioni6() {
      DAO<Campagna> campagnaDAO = Mockito.mock(CampagnaDAO.class);
      CampagnaService campagnaService = new CampagnaServiceImpl(campagnaDAO);

      assertTrue(campagnaService.rimborsaDonazioni(campagna, new CampagnaProxy(campagna)));
   }
}
