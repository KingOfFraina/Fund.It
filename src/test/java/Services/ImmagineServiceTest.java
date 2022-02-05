package Services;

import model.DAO.DAO;
import model.DAO.ImmagineDAO;
import model.beans.Campagna;
import model.beans.Immagine;
import model.services.ImmagineService;
import model.services.ImmagineServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImmagineServiceTest {
   Immagine immagine;

   @Before
   public void setup() {
      immagine = new Immagine();
      immagine.setPath("Path");
      immagine.setId(1);
      immagine.setCampagna(new Campagna());
   }

   @Test
   public void salvaImmagine1() {
      DAO<Immagine> immagineDAO = Mockito.mock(ImmagineDAO.class);
      Mockito.when(immagineDAO.save(immagine)).thenReturn(false);
      ImmagineService immagineService = new ImmagineServiceImpl(immagineDAO);

      assertFalse(immagineService.salvaImmagine(immagine));
   }

   @Test
   public void salvaImmagine2() {
      DAO<Immagine> immagineDAO = Mockito.mock(ImmagineDAO.class);
      Mockito.when(immagineDAO.save(immagine)).thenReturn(true);
      ImmagineService immagineService = new ImmagineServiceImpl(immagineDAO);

      assertTrue(immagineService.salvaImmagine(immagine));
   }

   @Test
   public void eliminaImmaginiCampagna1() {
      DAO<Immagine> immagineDAO = Mockito.mock(ImmagineDAO.class);
      Mockito.when(((ImmagineDAO) immagineDAO)
              .deleteByIdCampagna(immagine.getCampagna().getIdCampagna())).thenReturn(false);
      ImmagineService immagineService = new ImmagineServiceImpl(immagineDAO);

      assertFalse(immagineService.eliminaImmaginiCampagna(immagine.getCampagna().getIdCampagna()));
   }

   @Test
   public void eliminaImmaginiCampagna2() {
      DAO<Immagine> immagineDAO = Mockito.mock(ImmagineDAO.class);
      Mockito.when(((ImmagineDAO) immagineDAO)
              .deleteByIdCampagna(immagine.getCampagna().getIdCampagna())).thenReturn(true);
      ImmagineService immagineService = new ImmagineServiceImpl(immagineDAO);

      assertTrue(immagineService.eliminaImmaginiCampagna(immagine.getCampagna().getIdCampagna()));
   }
}
