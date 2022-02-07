package Controller;

import controller.GestioneFAQController;
import model.beans.FAQ;
import model.beans.Utente;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import static org.mockito.Mockito.*;

public class GestioneFAQControllerTest {
   static HttpServletRequest request;
   static HttpServletResponse response;
   static HttpSession session;
   static RequestDispatcher dispatcher;
   static ServletContext servletContext;
   static GestioneFAQController gestioneFAQController;

   @BeforeClass
   public static void setup() {
      request = mock(HttpServletRequest.class);
      response = mock(HttpServletResponse.class);
      dispatcher = mock(RequestDispatcher.class);
      session = mock(HttpSession.class);
      servletContext = mock(ServletContext.class);

      gestioneFAQController = new GestioneFAQController();
   }

   @Test
   public void visualizzaFAQ() throws ServletException, IOException {
      when(request.getPathInfo()).thenReturn("/visualizzaFAQ");
      when(request.getRequestDispatcher("/WEB-INF/results/visualizzaFAQ.jsp")).thenReturn(dispatcher);
      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), any(List.class));
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void modifica1() throws ServletException, IOException {
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void modifica2() throws ServletException, IOException {
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
   public void modifica3() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modificaFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getRequestDispatcher("/WEB-INF/results/formFAQ.jsp")).thenReturn(dispatcher);
      when(request.getParameter("idFaq")).thenReturn("1");

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), any(FAQ.class));
      verify(dispatcher, atLeastOnce()).forward(request, response);
   }

   @Test
   public void modifica4() throws ServletException, IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/modifica");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("idFaq")).thenReturn("1");

      gestioneFAQController.doGet(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void inserisci1() throws IOException {
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void inserisciFAQ1() throws IOException {
      Utente utente = new Utente();
      when(request.getPathInfo()).thenReturn("/inserisciFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn(null);

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt(), anyString());
   }

   /*@Test
   public void inserisciFAQ2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(request.getPathInfo()).thenReturn("/inserisciFAQ");
      when(request.getSession(false)).thenReturn(session);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(request.getParameter("domanda")).thenReturn("Domanda");
      when(request.getParameter("risposta")).thenReturn("Risposta");

      gestioneFAQController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
   }*/
}
