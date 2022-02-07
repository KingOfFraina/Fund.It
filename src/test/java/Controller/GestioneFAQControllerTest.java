package Controller;

import controller.GestioneFAQController;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GestioneFAQControllerTest {
   @Test
   public void visualizzaFAQ() throws ServletException, IOException {
      HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
      HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
      RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
      Mockito.when(request.getPathInfo()).thenReturn("/visualizzaFAQ");
      new GestioneFAQController().doGet(request, response);
      assertEquals(response, null);
   }
}
