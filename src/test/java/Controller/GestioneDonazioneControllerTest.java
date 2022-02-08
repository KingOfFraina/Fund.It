package Controller;

import controller.GestioneDonazioneController;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.services.CampagnaService;
import model.services.DonazioniService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class GestioneDonazioneControllerTest {
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

   @Test
   public void doGetFalseBranch() throws ServletException, IOException {
      when(session.getAttribute("utente")).thenReturn(null);
      when(session.getAttribute("donazione")).thenReturn(null);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registrazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      donazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doGetTrueBranchDonazioniList() throws ServletException, IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(session.getAttribute("donazione")).thenReturn(new Donazione());
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/donazioni");
      when(request.getRequestDispatcher("/WEB-INF/results/visualizzaDonazioni.jsp")).thenReturn(dispatcher);
      when(donazioniService.visualizzaDonazioni(new Utente())).thenReturn(new ArrayList<>());

      donazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), any());
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doGetTrueBranchScriviCommento() throws ServletException, IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(session.getAttribute("donazione")).thenReturn(new Donazione());
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/scriviCommento");
      when(request.getRequestDispatcher("/WEB-INF/results/commentoDonazione.jsp")).thenReturn(dispatcher);

      donazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doGetTrueBranchScriviCommentoError() throws ServletException, IOException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(session.getAttribute("donazione")).thenReturn(null);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/scriviCommento");

      donazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostFalseBranch() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/hahahha");
      int id = 1;
      when(campagnaService.trovaCampagna(id)).thenReturn(null);

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchDefaultBranch() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/hahahha");
      int id = 1;
      when(campagnaService.trovaCampagna(id)).thenReturn(new Campagna());

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchRegistraDonazioneBranch1() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registraDonazione");
      int id = 1;
      when(campagnaService.trovaCampagna(id)).thenReturn(new Campagna());

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchRegistraDonazioneBranch2() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("sommaDonata")).thenReturn("1");
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("ricevuta")).thenReturn("ricevuta");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registraDonazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(request.getRequestDispatcher("/WEB-INF/results/commentoDonazione.jsp")).thenReturn(dispatcher);
      int id = 1;
      when(campagnaService.trovaCampagna(id)).thenReturn(new Campagna());
      when(donazioniService.effettuaDonazione(any())).thenReturn(true);

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), any());
      verify(session, atLeastOnce()).setAttribute(anyString(), any());
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doPostTrueBranchRegistraDonazioneBranch3() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getParameter("sommaDonata")).thenReturn("1");
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("ricevuta")).thenReturn("ricevuta");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registraDonazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(request.getRequestDispatcher("/WEB-INF/results/commentoDonazione.jsp")).thenReturn(dispatcher);
      int id = 1;
      when(campagnaService.trovaCampagna(id)).thenReturn(new Campagna());
      when(donazioniService.effettuaDonazione(any())).thenReturn(false);

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), any());
      verify(session, atLeastOnce()).setAttribute(anyString(), any());
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doPostTrueBranchScriviCommentoBranch1() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(session.getAttribute("donazione")).thenReturn(null);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/scriviCommento");

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchScriviCommentoBranch2() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      Donazione donazione = new Donazione();
      donazione.setSommaDonata(10);
      when(session.getAttribute("donazione")).thenReturn(donazione);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("commento")).thenReturn("commento");
      when(request.getParameter("anonimo")).thenReturn(null);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/scriviCommento");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(donazioniService.commenta(any(Donazione.class))).thenReturn(true);
      int id = 1;
      Campagna campagna = new Campagna();
      campagna.setSommaRaccolta(10d);
      when(campagnaService.trovaCampagna(id)).thenReturn(campagna);
      when(donazioniService.effettuaDonazione(any())).thenReturn(true);

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(session, atLeastOnce()).removeAttribute(anyString());
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostTrueBranchScriviCommentoBranch3() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      Donazione donazione = new Donazione();
      donazione.setSommaDonata(10);
      when(session.getAttribute("donazione")).thenReturn(donazione);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("commento")).thenReturn("commento");
      when(request.getParameter("anonimo")).thenReturn("true");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/scriviCommento");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(donazioniService.commenta(any(Donazione.class))).thenReturn(false);
      int id = 1;
      Campagna campagna = new Campagna();
      campagna.setSommaRaccolta(10d);
      when(campagnaService.trovaCampagna(id)).thenReturn(campagna);
      when(donazioniService.effettuaDonazione(any())).thenReturn(true);

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchScriviCommentoBranch4() throws IOException, ServletException {
      when(session.getAttribute("utente")).thenReturn(new Utente());
      Donazione donazione = new Donazione();
      donazione.setSommaDonata(10);
      when(session.getAttribute("donazione")).thenReturn(null);
      when(request.getParameter("idCampagna")).thenReturn("1");
      when(request.getParameter("commento")).thenReturn("commento");
      when(request.getParameter("anonimo")).thenReturn("true");
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/scriviCommento");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(donazioniService.commenta(any(Donazione.class))).thenReturn(false);
      int id = 1;
      Campagna campagna = new Campagna();
      campagna.setSommaRaccolta(10d);
      when(campagnaService.trovaCampagna(id)).thenReturn(campagna);
      when(donazioniService.effettuaDonazione(any())).thenReturn(true);

      donazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }
}
