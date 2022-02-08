package Controller;

import controller.GestioneUtenteController;
import model.beans.Utente;
import org.junit.BeforeClass;
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
    static HttpServletRequest mockRequest;
    static HttpServletResponse mockResponse;
    static GestioneUtenteController utenteController;
    static HttpSession mockSession;
    static ServletContext mockContext;
    static RequestDispatcher mockDispatcher;
    static final String CONTEXT_PATH = "/FundIt-1.0-SNAPSHOT";

    @BeforeClass
    public static void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockSession = Mockito.mock(HttpSession.class);
        mockContext = Mockito.mock(ServletContext.class);
        mockDispatcher = Mockito.mock(RequestDispatcher.class);
        utenteController = new GestioneUtenteController();
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
        Utente utente = Mockito.mock(Utente.class);
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
        Utente utente = Mockito.mock(Utente.class);
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
    public void testCaseVisualizzaDashboard() throws IOException {
        Utente utente = Mockito.mock(Utente.class);
        when(mockRequest.getPathInfo())
                .thenReturn("/visualizzaDashboard");
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);

        verify(mockResponse, atMostOnce())
                .sendRedirect(anyString());
    }

}
