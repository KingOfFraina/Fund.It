package Controller;

import controller.GestioneDonazioneController;
import controller.GestioneSegnalazioneController;
import model.beans.Utente;
import model.services.CampagnaService;
import model.services.DonazioniService;
import model.services.SegnalazioniService;
import model.services.UtenteService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class GestioneSegnalazioneTest {
   HttpServletRequest request;
   HttpServletResponse response;
   HttpSession session;
   RequestDispatcher dispatcher;
   ServletContext servletContext;
   GestioneSegnalazioneController segnalazioneController;
   CampagnaService campagnaService;
   SegnalazioniService segnalazioniService;
   UtenteService utenteService;

   @Before
   public void setup() {
      request = mock(HttpServletRequest.class);
      response = mock(HttpServletResponse.class);
      dispatcher = mock(RequestDispatcher.class);
      session = mock(HttpSession.class);
      servletContext = mock(ServletContext.class);
      segnalazioniService = mock(SegnalazioniService.class);
      campagnaService = mock(CampagnaService.class);
      utenteService = mock(UtenteService.class);

      segnalazioneController = new GestioneSegnalazioneController(campagnaService, segnalazioniService, utenteService);
   }

   @Test
   public void doGetTrueBranch() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);

      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(segnalazioniService.trovaSegnalazioni()).thenReturn(new ArrayList<>());

      segnalazioneController.doGet(request, response);

      verify(request, atLeastOnce()).setAttribute(anyString(), anyList());
   }

   @Test
   public void doGetFalseBranch() throws IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getSession()).thenReturn(session);

      segnalazioneController.doGet(request, response);

      verify(response, atLeastOnce()).sendError(anyInt());
   }
}
