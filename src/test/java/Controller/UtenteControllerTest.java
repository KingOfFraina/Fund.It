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
import java.time.LocalDate;

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

    @Test
    public void testCasePromuoviUtenteNotAuthorized() throws IOException, ServletException {
        when(mockRequest.getPathInfo())
                .thenReturn("/promuoviDeclassaUtente");
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.isAdmin())
                .thenReturn(false);

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce())
                .sendError(anyInt(), anyString());
    }

    @Test
    public void testPromuoviUtenteAuthorized() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/promuoviDeclassaUtente");
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.isAdmin())
                .thenReturn(true);
        when(mockRequest.getParameter("utentemod"))
                .thenReturn("1");
        when(mockService.visualizzaDashboardUtente(anyInt()))
                .thenReturn(utente);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);
        when(mockService.promuoviDeclassaUtente(utente, utente))
                .thenReturn(true);

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getParameter("utentemod");
        verify(mockService, atLeastOnce())
                .promuoviDeclassaUtente(utente, utente);
        verify(mockDispatcher, atLeastOnce()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testPromuoviUtenteRequestParamNull() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/promuoviDeclassaUtente");
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.isAdmin())
                .thenReturn(true);
        when(mockRequest.getParameter("utentemod"))
                .thenReturn(null);

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getParameter("utentemod");
        verify(mockResponse, atMostOnce()).sendError(anyInt(), anyString());
    }

    @Test
    public void testPromuoviUtenteReportError() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/promuoviDeclassaUtente");
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(utente.isAdmin())
                .thenReturn(true);
        when(mockRequest.getParameter("utentemod"))
                .thenReturn("1");
        when(mockService.visualizzaDashboardUtente(anyInt()))
                .thenReturn(utente);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);
        when(mockService.promuoviDeclassaUtente(utente, utente))
                .thenReturn(false);

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getParameter("utentemod");
        verify(mockService, atLeastOnce())
                .promuoviDeclassaUtente(utente, utente);
        verify(mockDispatcher, atLeastOnce()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testPostDefaultCase() throws IOException, ServletException {
        when(mockRequest.getPathInfo())
                .thenReturn("sesso");

        utenteController.doPost(mockRequest, mockResponse);
        verify(mockResponse, atLeastOnce())
                .sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testPostModificaProfiloNotValid() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/modificaProfilo");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void testPostModificaProfilo() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/modificaProfilo");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockRequest.getParameter("password"))
                .thenReturn("password");
        when(mockRequest.getParameter("confermaPassword"))
                .thenReturn("password");
        when(mockRequest.getParameter("email"))
                .thenReturn("email");
        when(mockRequest.getParameter("confermaEmail"))
                .thenReturn("email");

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce())
                .getParameter(anyString());
        verify(mockResponse, atMostOnce())
                .sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testModificaProfiloTrue() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/modificaProfilo");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);
        when(mockRequest.getParameter("password"))
                .thenReturn("WerBn12m90.");
        when(mockRequest.getParameter("confermaPassword"))
                .thenReturn("WerBn12m90.");
        when(mockRequest.getParameter("email"))
                .thenReturn("emailprova@gmail.com");
        when(mockRequest.getParameter("confermaEmail"))
                .thenReturn("emailprova@gmail.com");
        when(mockRequest.getParameter("nome"))
                .thenReturn("nome");
        when(mockRequest.getParameter("cognome"))
                .thenReturn("cognome");
        when(mockRequest.getParameter("indirizzo"))
                .thenReturn("via sesti, 23");
        when(mockRequest.getParameter("cf"))
                .thenReturn("PKKKKK80A01F205C");
        when(mockRequest.getParameter("cap"))
                .thenReturn("89301");
        when(mockRequest.getParameter("citta"))
                .thenReturn("Milano");
        when(mockRequest.getParameter("telefono"))
                .thenReturn("3782212789");
        when(mockRequest.getParameter("dataDiNascita"))
                .thenReturn(LocalDate.now().toString());

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce())
                .getParameter(anyString());
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockDispatcher, atMostOnce()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testGetParamNull() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/modificaProfilo");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getParameter("password"))
                .thenReturn("password1");
        when(mockRequest.getParameter("confermaPassword"))
                .thenReturn("password2");

        utenteController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce())
                .getParameter(anyString());
        verify(mockResponse, atMostOnce())
                .sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
