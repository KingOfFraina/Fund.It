package Controller;

import controller.AutenticazioneController;
import model.beans.Utente;
import model.services.AutenticazioneService;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class AutenticazioneControllerTest {
   HttpServletRequest request;
   HttpServletResponse response;
   HttpSession session;
   RequestDispatcher dispatcher;
   ServletContext servletContext;
   AutenticazioneController autenticazioneController;
   AutenticazioneService service;

   @Before
   public void setup() {
      request = mock(HttpServletRequest.class);
      response = mock(HttpServletResponse.class);
      dispatcher = mock(RequestDispatcher.class);
      session = mock(HttpSession.class);
      servletContext = mock(ServletContext.class);
      service = mock(AutenticazioneService.class);

      autenticazioneController = new AutenticazioneController(service);
   }

   @Test
   public void doGetTrueBranchLogin() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getPathInfo()).thenReturn("/login");
      when(request.getRequestDispatcher("/WEB-INF/results/login.jsp")).thenReturn(dispatcher);

      autenticazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doGetTrueBranchRegistrazione() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getPathInfo()).thenReturn("/registrazione");
      when(request.getRequestDispatcher("/WEB-INF/results/registrazione.jsp")).thenReturn(dispatcher);

      autenticazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doGetTrueBranchErrore() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getPathInfo()).thenReturn("/re");

      autenticazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doGetFalseBranchLogout() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getPathInfo()).thenReturn("/logout");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(service.logout(any(HttpSession.class))).thenReturn(true);

      autenticazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doGetFalseBranchError() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(new Utente());
      when(request.getPathInfo()).thenReturn("/re");

      autenticazioneController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostDefaultBranch() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/log");

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostLoginBranch1() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setIdUtente(1);

      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/login");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(service.login(any(Utente.class))).thenReturn(utente);

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostLoginBranch2() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setIdUtente(-1);
      utente.setDataBan(LocalDateTime.now());

      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/login");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(service.login(any(Utente.class))).thenReturn(utente);

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostLoginBranch3() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/login");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(service.login(any(Utente.class))).thenReturn(null);

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRegistrazioneBranch1() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registrazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(request.getParameter("confermaEmail")).thenReturn("email");
      when(request.getParameter("confermaPassword")).thenReturn("");

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRegistrazioneBranch2() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registrazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(request.getParameter("confermaEmail")).thenReturn("email");
      when(request.getParameter("confermaPassword")).thenReturn("password");
      when(request.getParameter("password")).thenReturn("");
      when(request.getParameter("email")).thenReturn("");
      when(request.getParameter("nome")).thenReturn("");
      when(request.getParameter("cognome")).thenReturn("");
      when(request.getParameter("dataDiNascita")).thenReturn(LocalDate.now().toString());
      when(request.getParameter("telefono")).thenReturn("");
      when(request.getParameter("indirizzo")).thenReturn("");
      when(request.getParameter("citta")).thenReturn("");
      when(request.getParameter("cap")).thenReturn("");
      when(request.getParameter("cf")).thenReturn("");
      when(service.registrazione(any(Utente.class))).thenReturn(true);

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRegistrazioneBranch4() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registrazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(request.getParameter("confermaEmail")).thenReturn("email");
      when(request.getParameter("confermaPassword")).thenReturn("password");
      when(request.getParameter("password")).thenReturn("");
      when(request.getParameter("email")).thenReturn("");
      when(request.getParameter("nome")).thenReturn("");
      when(request.getParameter("cognome")).thenReturn("");
      when(request.getParameter("dataDiNascita")).thenReturn(LocalDate.now().toString());
      when(request.getParameter("telefono")).thenReturn("");
      when(request.getParameter("indirizzo")).thenReturn("");
      when(request.getParameter("citta")).thenReturn("");
      when(request.getParameter("cap")).thenReturn("");
      when(request.getParameter("cf")).thenReturn("");
      when(service.registrazione(any(Utente.class))).thenReturn(true);

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostRegistrazioneBranch5() throws ServletException, IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/registrazione");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("email")).thenReturn("email");
      when(request.getParameter("password")).thenReturn("password");
      when(request.getParameter("confermaEmail")).thenReturn("email");
      when(request.getParameter("confermaPassword")).thenReturn("password");
      when(request.getParameter("password")).thenReturn("");
      when(request.getParameter("email")).thenReturn("");
      when(request.getParameter("nome")).thenReturn("");
      when(request.getParameter("cognome")).thenReturn("");
      when(request.getParameter("dataDiNascita")).thenReturn(LocalDate.now().toString());
      when(request.getParameter("telefono")).thenReturn("");
      when(request.getParameter("indirizzo")).thenReturn("");
      when(request.getParameter("citta")).thenReturn("");
      when(request.getParameter("cap")).thenReturn("");
      when(request.getParameter("cf")).thenReturn("");
      when(service.registrazione(any(Utente.class))).thenReturn(false);

      autenticazioneController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }
}
