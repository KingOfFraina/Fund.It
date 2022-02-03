import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CampagnaDAOTest {
   DAO<Utente> utenteDAO;
   DAO<Campagna> campagnaDAO;
   Utente utente;

   @BeforeClass public static void openConnection() throws SQLException {
      ConPool.getInstance().getConnection();
   }

   @Before
   public void setup() {
      utenteDAO = new UtenteDAO();
      utente = new Utente();

      utente.setAdmin(true);
      utente.setCap("cap");
      utente.setCf("cf");
      utente.setCitta("città");
      utente.setCognome("cognome");
      utente.setDataBan(LocalDateTime.now());
      utente.setDataDiNascita(LocalDate.now());
      utente.setEmail("email");
      utente.setFotoProfilo("fotoProfilo");
      utente.setNome("nome");
      utente.setPassword("passwordhash");
      utente.setStrada("strada");
      utente.setTelefono("telefono");
      utente.setCampagne(null);
      utente.setDonazioni(null);
      utente.setSegnalazioni(null);
   }

   @AfterClass public static void closeConnection() {
      ConPool.getInstance().closeDataSource();
   }
}
/*
} catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Id <= 0");
      }
 */