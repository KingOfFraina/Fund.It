package Controller;

import controller.GestioneUtenteController;
import model.beans.Utente;
import model.services.UtenteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class UtenteControllerTest {
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    GestioneUtenteController utenteController;
    HttpSession mockSession;
    ServletContext mockContext;
    RequestDispatcher mockDispatcher;
    UtenteService mockService;
    Utente utente;
    final String CONTEXT_PATH = "/FundIt-1.0-SNAPSHOT";

    @Before
    public void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockSession = Mockito.mock(HttpSession.class);
        mockContext = Mockito.mock(ServletContext.class);
        mockDispatcher = Mockito.mock(RequestDispatcher.class);
        mockService = Mockito.mock(UtenteService.class);
        utente = Mockito.mock(Utente.class);
        utenteController = new GestioneUtenteController(mockService);
    }

    @Test
    public void primoTest() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn(null);
        utenteController.doGet(mockRequest, mockResponse);
        verify(mockResponse, atMostOnce()).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void secondoTest() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/visualizzaDashboardAdmin");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.isAdmin())
                .thenReturn(true);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);

        utenteController.doGet(mockRequest, mockResponse);
        verify(mockRequest, atLeastOnce()).getSession();
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(utente, atLeastOnce()).isAdmin();
        verify(mockDispatcher, atLeastOnce()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testUserNotAuthorized() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/visualizzaDashboardAdmin");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.isAdmin())
                .thenReturn(false);

        utenteController.doGet(mockRequest, mockResponse);
        verify(mockRequest, atLeastOnce()).getSession();
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(utente, atLeastOnce()).isAdmin();
        verify(mockResponse, atMostOnce()).sendError(anyInt(), anyString());

    }

    @Test
    public void testCaseVisualizzaDashboard() throws IOException, ServletException {
        when(mockRequest.getPathInfo())
                .thenReturn("/visualizzaDashboard");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.getIdUtente())
                .thenReturn(2);
        when(utente.getCf())
                .thenReturn("sex");
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockService.visualizzaDashboardUtente(utente.getIdUtente()))
                .thenReturn(utente);

        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);

        utenteController.doGet(mockRequest, mockResponse);
        verify(mockSession, atLeastOnce())
                .getAttribute("utente");
        verify(mockDispatcher, atLeastOnce())
                .forward(mockRequest, mockResponse);
    }

    @Test
    public void testNotValidDashboardUtente() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/visualizzaDashboard");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);

        utenteController.doGet(mockRequest, mockResponse);
        verify(mockResponse, atMostOnce())
                .sendRedirect(CONTEXT_PATH + "/autenticazione/login");
    }

    @Test
    public void testNotValidDashboardAdmin()
            throws ServletException, IOException {

        when(mockRequest.getPathInfo())
                .thenReturn("/visualizzaDashboardAdmin");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);

        utenteController.doGet(mockRequest, mockResponse);
        verify(mockResponse, atMostOnce())
                .sendRedirect(CONTEXT_PATH + "/autenticazione/login");
    }

    @Test
    public void testPostCasePromuoviUtente() throws IOException, ServletException {
        when(mockRequest.getPathInfo())
                .thenReturn("/promuoviDeclassaUtente");
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);

        utenteController.doPost(mockRequest, mockResponse);
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockResponse, atMostOnce()).sendRedirect(anyString());
    }
}
