import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtenteDAOTest {
   DAO<Utente> utenteDAO;

   @Before
   public void setup() {
      utenteDAO = new UtenteDAO();
   }

   @Test
   public void saveNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         utenteDAO.extract(null);
      });
   }

   @After
   public void clean() {

   }
}
