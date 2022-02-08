package Controller.utils;

import controller.utils.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {
   Validator validator;
   HttpServletRequest request;

   @Before
   public void setup() {
      request = Mockito.mock(HttpServletRequest.class);
      validator = new Validator(request);
   }

   @Test
   public void testIsValidBean00() {
      assertFalse(validator.isValidBean(String.class, null));
   }

    @Test
    public void testIsValidBean01() {
        assertFalse(validator.isValidBean(null, null));
    }

    @Test
    public void testIsValidBean10() {
       Integer intero = 0;
        assertFalse(validator.isValidBean(String.class, intero));
    }

    @Test
    public void testIsValidBean11() {
        String test = "test";
        assertTrue(validator.isValidBean(String.class, test));
    }
   @Test
   public void testAssertUtente0() {
       assertFalse(validator.assertUtente());
   }

   @Test
   public void testAssertUtente1() {
       Mockito.when(request.getParameter("nome")).thenReturn("Angelo");
       Mockito.when(request.getParameter("cognome")).thenReturn("Meo");
       Mockito.when(request.getParameter("email")).thenReturn("s.genovese@gmail.com");
       Mockito.when(request.getParameter("cf")).thenReturn("QSZBVM83E54F965E");
       Mockito.when(request.getParameter("password")).thenReturn("Bcbc4321!");
       Mockito.when(request.getParameter("cap")).thenReturn("80030");
       Mockito.when(request.getParameter("indirizzo")).thenReturn("piazza garibaldi, 4");
       Mockito.when(request.getParameter("citta")).thenReturn("Bergamo");
       Mockito.when(request.getParameter("telefono")).thenReturn("0123456789");

       assertTrue(validator.assertUtente());
   }
    @Test
    public void testRequired00() {
       assertFalse(validator.required(null));
    }

    @Test
    public void testRequired10() {
       assertFalse(validator.required(""));
    }
    @Test
    public void testRequired11() {
       assertTrue(validator.required("aa"));
    }

   @After
   public void clean() {

   }
}
