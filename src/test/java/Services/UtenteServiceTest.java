package Services;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.services.UtenteService;
import model.services.UtenteServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class UtenteServiceTest {
   @Test
   public void visualizzaDashboardUtente1() {
      UtenteService utenteService = new UtenteServiceImpl(Mockito.mock(UtenteDAO.class));

      assertThrows(IllegalArgumentException.class, () -> utenteService.visualizzaDashboardUtente(0));
   }

   @Test
   public void visualizzaDashboardUtente2() {
      DAO<Utente> utenteDAO = Mockito.mock(UtenteDAO.class);
      Mockito.when(utenteDAO.getById(1)).thenReturn(null);
      UtenteService utenteService = new UtenteServiceImpl(utenteDAO);

      assertNull(utenteService.visualizzaDashboardUtente(1));
   }

   @Test
   public void visualizzaDashboardUtente3() {
      DAO<Utente> utenteDAO = Mockito.mock(UtenteDAO.class);
      Mockito.when(utenteDAO.getById(1)).thenReturn(new Utente());
      UtenteService utenteService = new UtenteServiceImpl(utenteDAO);

      assertNotNull(utenteService.visualizzaDashboardUtente(1));
   }
}
