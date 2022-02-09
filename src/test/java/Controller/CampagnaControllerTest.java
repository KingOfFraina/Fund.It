package Controller;

import controller.GestioneCampagnaController;
import model.beans.Campagna;
import model.beans.Utente;
import model.services.CampagnaService;
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

public class CampagnaControllerTest {
    Campagna campagna;
    Utente utente;
    GestioneCampagnaController campagnaController;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    HttpSession mockSession;
    ServletContext mockContext;
    CampagnaService mockService;
    RequestDispatcher mockDispatcher;
    final String CONTEXT_PATH = "/FundIt-1.0-SNAPSHOT";


    @Before
    public void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockSession = Mockito.mock(HttpSession.class);
        mockContext = Mockito.mock(ServletContext.class);
        mockDispatcher = Mockito.mock(RequestDispatcher.class);
        mockService = Mockito.mock(CampagnaService.class);
        campagnaController = new GestioneCampagnaController(mockService);
        campagna = Mockito.mock(Campagna.class);
        utente = Mockito.mock(Utente.class);
    }

    @Test
    public void testGetNotValidCreazioneCampagna() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/creaCampagna");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);


        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getSession();
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void testGetValidCreazioneCampagna() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/creaCampagna");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);

        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockRequest, atLeastOnce()).setAttribute(anyString(), anyList());
        verify(mockDispatcher, atLeastOnce()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testCasePathNotFound() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("ciao");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);

        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce())
                .getPathInfo();
        verify(mockResponse, atLeastOnce())
                .sendError(anyInt(), anyString());
    }

    @Test
    public void testGetMain() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/main");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);

        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce())
                .getPathInfo();
        verify(mockRequest, atLeastOnce())
                .getSession();
        verify(mockSession, atLeastOnce())
                .getAttribute("utente");
        verify(mockDispatcher, atLeastOnce())
                .forward(mockRequest, mockResponse);
    }

    @Test
    public void testGetNotValidModificaCampagna() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/modificaCampagna");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);


        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockRequest, atLeastOnce()).getSession();
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void testGetValidModificaCampagna() throws ServletException, IOException {
        Utente utente1 = Mockito.mock(Utente.class);
        when(mockRequest.getPathInfo())
                .thenReturn("/modificaCampagna");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(utente);
        when(mockRequest.getServletContext())
                .thenReturn(mockContext);
        when(mockRequest.getParameter("idCampagna"))
                .thenReturn("2");
        when(mockService.trovaCampagna(anyInt()))
                .thenReturn(campagna);
        when(campagna.getUtente())
                .thenReturn(utente1);
        when(utente1.getIdUtente())
                .thenReturn(1);
        when(utente.getIdUtente())
                .thenReturn(2);
        when(mockContext.getContextPath())
                .thenReturn(CONTEXT_PATH);


        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getSession();
        verify(mockSession, atLeastOnce()).getAttribute("utente");
        verify(mockRequest, atLeastOnce()).getParameter("idCampagna");
        verify(mockService, atLeastOnce()).trovaCampagna(anyInt());
        verify(mockResponse, atLeastOnce()).sendError(anyInt(), anyString());
    }
}
