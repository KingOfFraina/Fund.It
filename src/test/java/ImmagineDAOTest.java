import model.DAO.DAO;
import model.DAO.ImmagineDAO;
import model.beans.Immagine;
import model.storage.ConPool;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmagineDAOTest {
   DAO<Immagine> immagineDAO;

   @Before
   public void setup() throws SQLException {
      ConPool.getInstance().getConnection();
      immagineDAO = new ImmagineDAO();
   }

   @Test
   public void getByIdInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         immagineDAO.getById(-1);
      });
   }

   @After
   public void clean() {
      ConPool.getInstance().closeDataSource();
   }
}

/*
} catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
         }
      } else {
         throw new IllegalArgumentException("Null object");
      }
 */
