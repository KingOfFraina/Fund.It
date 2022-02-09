package Controller;

import controller.GestioneCampagnaController;
import model.beans.Campagna;
import model.beans.Utente;
import model.services.CampagnaService;
import model.services.CategoriaService;
import org.checkerframework.checker.units.qual.C;
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

public class CaTest {
    Campagna campagna;
    Utente utente;
    GestioneCampagnaController campagnaController;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    HttpSession mockSession;
    ServletContext mockContext;
    CampagnaService mockService;
    CategoriaService mockCategoriaService;
    RequestDispatcher mockDispatcher;


    @Before
    public void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockSession = Mockito.mock(HttpSession.class);
        mockContext = Mockito.mock(ServletContext.class);
        mockDispatcher = Mockito.mock(RequestDispatcher.class);
        mockService = Mockito.mock(CampagnaService.class);
        mockCategoriaService = Mockito.mock(CategoriaService.class);
        campagnaController = new GestioneCampagnaController(mockService, mockCategoriaService);
        campagna = Mockito.mock(Campagna.class);
        utente = Mockito.mock(Utente.class);
    }

    @Test
    public void doPostTrueBranch() throws ServletException, IOException {
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(null);
        when(mockRequest.getPathInfo()).thenReturn("/chiudiCampagna");
        when(mockRequest.getServletContext()).thenReturn(mockContext);
        when(mockContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void doPostFalseBranchDefault() throws ServletException, IOException {
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(new Utente());
        when(mockRequest.getPathInfo()).thenReturn("/x");

        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockResponse, atLeastOnce()).sendError(anyInt());
    }

    @Test
    public void doPostFalseBranchChiudiCampagna1() throws ServletException, IOException {
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(new Utente());
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/chiudiCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        when(mockService.chiudiCampagna(any())).thenReturn(false);

        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockResponse, atLeastOnce()).sendError(anyInt());
    }

    @Test
    public void doPostFalseBranchChiudiCampagna2() throws ServletException, IOException {
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(new Utente());
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/chiudiCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        when(mockService.chiudiCampagna(any())).thenReturn(true);
        when(mockRequest.getServletContext()).thenReturn(mockContext);
        when(mockContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void doPostFalseBranchCancellaCampagna1() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/cancellaCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        Utente utente2 = new Utente();
        utente2.setIdUtente(2);
        Campagna campagna = new Campagna();
        campagna.setUtente(utente2);
        when(mockService.trovaCampagna(anyInt())).thenReturn(campagna);
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockResponse, atLeastOnce()).sendError(anyInt());
    }

    @Test
    public void doPostFalseBranchCancellaCampagna2() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/cancellaCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        Campagna campagna = new Campagna();
        campagna.setUtente(utente);
        when(mockService.trovaCampagna(anyInt())).thenReturn(campagna);
        when(mockService.cancellaCampagna(any())).thenReturn(false);
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockService, atLeastOnce()).cancellaCampagna(any());
        verify(mockService, atLeastOnce()).trovaCampagna(anyInt());
    }

    @Test
    public void doPostFalseBranchCancellaCampagna3() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/cancellaCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        Campagna campagna = new Campagna();
        campagna.setUtente(utente);
        when(mockService.trovaCampagna(anyInt())).thenReturn(campagna);
        when(mockService.cancellaCampagna(any())).thenReturn(true);
        when(mockService.rimborsaDonazioni(any(), any())).thenReturn(true);
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockService, atLeastOnce()).cancellaCampagna(any());
        verify(mockService, atLeastOnce()).trovaCampagna(anyInt());
        verify(mockService, atLeastOnce()).rimborsaDonazioni(any(), any());
    }

    @Test
    public void doPostFalseBranchCancellaCampagna4() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/cancellaCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        Campagna campagna = new Campagna();
        campagna.setUtente(utente);
        when(mockService.trovaCampagna(anyInt())).thenReturn(campagna);
        when(mockService.cancellaCampagna(any())).thenReturn(true);
        when(mockService.rimborsaDonazioni(any(), any())).thenReturn(false);
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockService, atLeastOnce()).cancellaCampagna(any());
        verify(mockService, atLeastOnce()).trovaCampagna(anyInt());
        verify(mockService, atLeastOnce()).rimborsaDonazioni(any(), any());
    }

    @Test
    public void doPostFalseBranchCreaCampagna1() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/creaCampagna");
        when(mockRequest.getParameter("idCategoria")).thenReturn("1");
        when(mockRequest.getParameter("sommaTarget")).thenReturn("12");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        when(mockService.creazioneCampagna(any())).thenReturn(true);
        when(mockRequest.getServletContext()).thenReturn(mockContext);
        when(mockContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockRequest, atLeastOnce()).getParts();
        verify(mockService, atLeastOnce()).creazioneCampagna(any());
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void doPostFalseBranchCreaCampagna2() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("1");
        when(mockRequest.getPathInfo()).thenReturn("/creaCampagna");
        when(mockRequest.getParameter("idCategoria")).thenReturn("1");
        when(mockRequest.getParameter("sommaTarget")).thenReturn("12");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        when(mockService.creazioneCampagna(any())).thenReturn(false);
        when(mockRequest.getServletContext()).thenReturn(mockContext);
        when(mockContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockService, atLeastOnce()).creazioneCampagna(any());
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void doPostFalseBranchModificaCampagna1() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn(null);
        when(mockRequest.getPathInfo()).thenReturn("/modificaCampagna");
        when(mockService.trovaCampagna(anyInt())).thenReturn(new Campagna());
        when(mockRequest.getServletContext()).thenReturn(mockContext);
        when(mockContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");
        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
    }

    @Test
    public void doPostFalseBranchModificaCampagna2() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("2");
        when(mockRequest.getParameter("idCategoria")).thenReturn("1");
        when(mockRequest.getParameter("sommaTarget")).thenReturn("12");
        when(mockRequest.getPathInfo()).thenReturn("/modificaCampagna");
        Utente utente2 = new Utente();
        utente2.setIdUtente(2);
        Campagna campagna = new Campagna();
        campagna.setUtente(utente2);
        when(mockService.trovaCampagna(anyInt())).thenReturn(campagna);
        when(mockService.modificaCampagna(any())).thenReturn(true);
        when(mockRequest.getServletContext()).thenReturn(mockContext);
        when(mockContext.getContextPath()).thenReturn("/FundIt-1.0-SNAPSHOT");

        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockService, atLeastOnce()).trovaCampagna(anyInt());
        verify(mockResponse, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void doPostFalseBranchModificaCampagna3() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("utente")).thenReturn(utente);
        when(mockRequest.getParameter("idCampagna")).thenReturn("2");
        when(mockRequest.getParameter("idCategoria")).thenReturn("1");
        when(mockRequest.getParameter("sommaTarget")).thenReturn("12");
        when(mockRequest.getPathInfo()).thenReturn("/modificaCampagna");
        Utente utente2 = new Utente();
        utente2.setIdUtente(2);
        Campagna campagna = new Campagna();
        campagna.setUtente(utente2);
        when(mockService.trovaCampagna(anyInt())).thenReturn(campagna);
        when(mockService.modificaCampagna(any())).thenReturn(false);
        when(mockRequest.getRequestDispatcher(anyString()))
                .thenReturn(mockDispatcher);

        campagnaController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getPathInfo();
        verify(mockService, atLeastOnce()).trovaCampagna(anyInt());
        verify(mockDispatcher, atLeastOnce()).forward(mockRequest, mockResponse);
    }
}
