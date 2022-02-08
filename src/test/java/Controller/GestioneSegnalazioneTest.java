package Controller;

import controller.GestioneSegnalazioneController;
import model.beans.Campagna;
import model.beans.Utente;
import model.services.CampagnaService;
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

   @Test
   public void doPostErrorBranch() throws IOException {
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getSession()).thenReturn(session);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostSegnalaBranch1() throws IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(campagnaService.trovaCampagna(anyInt())).thenReturn(new Campagna());
      when(segnalazioniService.segnalaCampagna(any())).thenReturn(true);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/segnala");

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostSegnalaBranch2() throws IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(campagnaService.trovaCampagna(anyInt())).thenReturn(new Campagna());
      when(segnalazioniService.segnalaCampagna(any())).thenReturn(false);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/segnala");

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRisolviBranch1() throws IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/risolvi");

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostRisolviBranch2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      utente.setIdUtente(1);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(request.getParameter("sceltaSegnalazione")).thenReturn("Risolvi");
      when(request.getParameter("idSegnalazione")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/risolvi");
      Campagna campagna = new Campagna();
      campagna.setUtente(utente);
      when(campagnaService.trovaCampagna(anyInt())).thenReturn(campagna);
      when(segnalazioniService.risolviSegnalazione(anyInt(), any())).thenReturn(true);
      when(utenteService.visualizzaDashboardUtente(anyInt())).thenReturn(new Utente());
      when(utenteService.sospensioneUtente(any())).thenReturn(true);
      when(campagnaService.cancellaCampagna(any())).thenReturn(true);
      when(campagnaService.rimborsaDonazioni(any(), any())).thenReturn(true);

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRisolviBranch3() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      utente.setIdUtente(1);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(request.getParameter("sceltaSegnalazione")).thenReturn("Risolvi");
      when(request.getParameter("idSegnalazione")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/risolvi");
      Campagna campagna = new Campagna();
      campagna.setUtente(utente);
      when(campagnaService.trovaCampagna(anyInt())).thenReturn(campagna);
      when(segnalazioniService.risolviSegnalazione(anyInt(), any())).thenReturn(true);
      when(utenteService.visualizzaDashboardUtente(anyInt())).thenReturn(new Utente());
      when(utenteService.sospensioneUtente(any())).thenReturn(true);
      when(campagnaService.cancellaCampagna(any())).thenReturn(true);
      when(campagnaService.rimborsaDonazioni(any(), any())).thenReturn(false);

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRisolviBranch4() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      utente.setIdUtente(1);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(request.getParameter("sceltaSegnalazione")).thenReturn("Altro");
      when(request.getParameter("idSegnalazione")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/risolvi");
      when(segnalazioniService.risolviSegnalazione(anyInt(), any())).thenReturn(true);

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRisolviBranch5() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      utente.setIdUtente(1);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("descrizione")).thenReturn("descrizione");
      when(request.getParameter("idUtente")).thenReturn("1");
      when(request.getParameter("sceltaSegnalazione")).thenReturn("Altro");
      when(request.getParameter("idSegnalazione")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/risolvi");
      when(segnalazioniService.risolviSegnalazione(anyInt(), any())).thenReturn(false);

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostDefaultBranch() throws IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/hahah");

      segnalazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }
}
