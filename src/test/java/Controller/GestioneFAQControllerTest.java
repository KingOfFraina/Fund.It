package Controller;

import controller.GestioneFAQController;
import model.beans.FAQ;
import model.beans.Utente;
import model.services.FaqService;
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

public class GestioneFAQControllerTest {
   HttpServletRequest request;
   HttpServletResponse response;
   HttpSession session;
   RequestDispatcher dispatcher;
   ServletContext servletContext;
   GestioneFAQController gestioneFAQController;
   FaqService service;

   @Before
   public void setup() {
      request = mock(HttpServletRequest.class);
      response = mock(HttpServletResponse.class);
      dispatcher = mock(RequestDispatcher.class);
      session = mock(HttpSession.class);
      servletContext = mock(ServletContext.class);
      service = mock(FaqService.class);

      gestioneFAQController = new GestioneFAQController(service);
   }

   @Test
   public void doGetVisualizzaFAQ() throws ServletException, IOException {
      when(request.getPathInfo()).thenReturn("/visualizzaFAQ");
      when(request.getRequestDispatcher("/WEB-INF/results/visualizzaFAQ.jsp")).thenReturn(dispatcher);
      when(service.visualizzaFaq()).thenReturn(new ArrayList<>());

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), anyList());
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doGetTrueBranch() throws ServletException, IOException {
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession()).thenReturn(null);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doGetTrueBranchUnauthorized() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setAdmin(false);
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt(), anyString());
   }

   @Test
   public void doGetTrueBranchModificaFAQ() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getRequestDispatcher("/WEB-INF/results/formFAQ.jsp")).thenReturn(dispatcher);
      when(request.getParameter("idFaq")).thenReturn("1");
      when(service.visualizzaFaq(Integer.parseInt(request.getParameter("idFaq")))).thenReturn(new FAQ());

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), any(FAQ.class));
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void doGetTrueBranchError() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modicaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getRequestDispatcher("/WEB-INF/results/formFAQ.jsp")).thenReturn(dispatcher);
      when(request.getParameter("idFaq")).thenReturn("1");

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostFalseBranch() throws IOException {
      when(request.getPathInfo()).thenReturn("/inserisciFAQ");
      when(request.getSession(false)).thenReturn(null);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn(null);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchDefaultBranch() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/i");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchInserisciFAQBranch1() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/inserisciFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn(null);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchInserisciFAQBranch1_1() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/inserisciFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn("Risposta");
      when(service.inserisciFaq(any(FAQ.class))).thenReturn(true);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostTrueBranchInserisciFAQBranch1_2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/inserisciFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn("Risposta");
      when(service.inserisciFaq(any(FAQ.class))).thenReturn(false);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostTrueBranchModificaFAQBranch1() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn(null);
      when(request.getParameter("idFaq")).thenReturn("1");

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostTrueBranchModificaFAQBranch1_1() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn("Risposta");
      when(request.getParameter("idFaq")).thenReturn("1");
      when(service.modificaFaq(any(FAQ.class))).thenReturn(true);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostTrueBranchModificaFAQBranch1_2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn("Risposta");
      when(request.getParameter("idFaq")).thenReturn("1");
      when(service.modificaFaq(any(FAQ.class))).thenReturn(false);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostTrueBranchEliminaFAQBranch1() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/eliminaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("idFaq")).thenReturn("1");
      when(service.cancellaFaq(any(FAQ.class))).thenReturn(true);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostTrueBranchEliminaFAQBranch2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/eliminaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("idFaq")).thenReturn("1");
      when(service.cancellaFaq(any(FAQ.class))).thenReturn(false);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }
}
