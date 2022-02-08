package Controller;

import controller.GestioneCategorieController;
import model.beans.Categoria;
import model.beans.Utente;
import model.services.CategoriaService;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class GestioneCategorieControllerTest {
   HttpServletRequest request;
   HttpServletResponse response;
   HttpSession session;
   RequestDispatcher dispatcher;
   ServletContext servletContext;
   GestioneCategorieController categorieController;
   CategoriaService categoriaService;

   @Before
   public void setup() {
      request = mock(HttpServletRequest.class);
      response = mock(HttpServletResponse.class);
      dispatcher = mock(RequestDispatcher.class);
      session = mock(HttpSession.class);
      servletContext = mock(ServletContext.class);
      categoriaService = mock(CategoriaService.class);

      categorieController = new GestioneCategorieController(categoriaService);
   }

   @Test
   public void doPostDefaultBranch() throws IOException {
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/hahah");

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostInserisciCategoriaBranch1() throws IOException {
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/inserisciCategoria");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostInserisciCategoriaBranch2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(false);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/inserisciCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostInserisciCategoriaBranch3() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/inserisciCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(categoriaService.inserisciCategoria(any())).thenReturn(false);

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostInserisciCategoriaBranch4() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/inserisciCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("Categoria");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(categoriaService.inserisciCategoria(any())).thenReturn(false);

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostInserisciCategoriaBranch5() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/inserisciCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("Categoria");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(categoriaService.inserisciCategoria(any())).thenReturn(true);

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), anyList());
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostModificaCategoriaBranch1() throws IOException {
      when(session.getAttribute("utente")).thenReturn(null);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/modificaCategoria");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostModificaCategoriaBranch2() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(false);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/modificaCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendError(anyInt());
   }

   @Test
   public void doPostModificaCategoriaBranch3() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/modificaCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("Categoria");
      when(request.getParameter("idCategoria")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(categoriaService.visualizzaCategoria(any())).thenReturn(new Categoria());
      when(categoriaService.modificaCategoria(any())).thenReturn(false);

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }

   @Test
   public void doPostModificaCategoriaBranch4() throws IOException {
      Utente utente = new Utente();
      utente.setAdmin(true);
      when(session.getAttribute("utente")).thenReturn(utente);
      when(request.getSession()).thenReturn(session);
      when(request.getPathInfo()).thenReturn("/modificaCategoria");
      when(request.getParameter("nomeCategoria")).thenReturn("Categoria");
      when(request.getParameter("idCategoria")).thenReturn("1");
      when(request.getServletContext()).thenReturn(servletContext);
      when(servletContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
      when(categoriaService.visualizzaCategoria(any())).thenReturn(new Categoria());
      when(categoriaService.modificaCategoria(any())).thenReturn(true);

      categorieController.doPost(request, response);

      verify(request, atLeastOnce()).getPathInfo();
      verify(request, atLeastOnce()).setAttribute(anyString(), anyList());
      verify(response, atLeastOnce()).sendRedirect(anyString());
   }
}
