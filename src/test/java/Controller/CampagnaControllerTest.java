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
    public void testGetCreazioneCampagna() throws ServletException, IOException {
        when(mockRequest.getPathInfo())
                .thenReturn("/creaCampagna");
        when(mockRequest.getSession())
                .thenReturn(mockSession);
        when(mockSession.getAttribute("utente"))
                .thenReturn(null);


        campagnaController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeastOnce()).getSession();
        verify(mockSession, atLeastOnce()).getAttribute("utente");
    }
}
