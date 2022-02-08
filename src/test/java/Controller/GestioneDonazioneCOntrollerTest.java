package Controller;

import controller.AutenticazioneController;
import controller.GestioneDonazioneController;
import model.services.AutenticazioneService;
import model.services.CampagnaService;
import model.services.DonazioniService;
import org.junit.Before;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;

public class GestioneDonazioneCOntrollerTest {
   HttpServletRequest request;
   HttpServletResponse response;
   HttpSession session;
   RequestDispatcher dispatcher;
   ServletContext servletContext;
   GestioneDonazioneController donazioneController;
   DonazioniService donazioniService;
   CampagnaService campagnaService;

   @Before
   public void setup() {
      request = mock(HttpServletRequest.class);
      response = mock(HttpServletResponse.class);
      dispatcher = mock(RequestDispatcher.class);
      session = mock(HttpSession.class);
      servletContext = mock(ServletContext.class);
      donazioniService = mock(DonazioniService.class);
      campagnaService = mock(CampagnaService.class);

      donazioneController = new GestioneDonazioneController(donazioniService, campagnaService);
   }
}
