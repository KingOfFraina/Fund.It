package Controller.utils;

import controller.utils.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
   public void test1() {
      String test = "test";
      assertTrue(validator.isValidBean(String.class, test));
   }

   @Test
   public void test2() {
      assertFalse(validator.isValidBean(String.class, null));
   }

   @After
   public void clean() {

   }
}
