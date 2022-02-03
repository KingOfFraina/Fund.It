import model.DAO.*;
import model.beans.*;
import model.storage.ConPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImmagineDAOTest {
   DAO<Immagine> immagineDAO;
   DAO<Campagna> campagnaDAO;
   DAO<Categoria> categoriaDAO;
   DAO<Utente> utenteDAO;
   Immagine immagine;
   Campagna campagna;
   Categoria categoria;
   Utente utente;

   @Before
   public void setup() throws SQLException {
      ConPool.getInstance().getConnection();
      immagineDAO = new ImmagineDAO();
      campagnaDAO = new CampagnaDAO();
      categoriaDAO = new CategoriaDAO();
      utenteDAO = new UtenteDAO();

      categoria = new Categoria();
      categoria.setNome("");

      categoriaDAO.save(categoria);

      utente = new Utente();
      utente.setAdmin(false);
      utente.setCap("");
      utente.setCf("");
      utente.setCitta("");
      utente.setCognome("");
      utente.setDataBan(LocalDateTime.now());
      utente.setDataDiNascita(LocalDate.now());
      utente.setEmail("");
      utente.setFotoProfilo("");
      utente.setNome("");
      utente.setPassword("");
      utente.setStrada("");
      utente.setTelefono("");
      utente.setCampagne(null);
      utente.setDonazioni(null);
      utente.setSegnalazioni(null);
      utenteDAO.save(utente);

      campagna = new Campagna();
      campagna.setTitolo("");
      campagna.setDescrizione("");
      campagna.setStato(StatoCampagna.ATTIVA);
      campagna.setSommaTarget(0d);
      campagna.setSommaRaccolta(0d);
      campagna.setCategoria(categoria);
      campagna.setUtente(utente);
      campagnaDAO.save(campagna);

      immagine = new Immagine();
      immagine.setCampagna(campagna);
      immagine.setPath("");
   }

   @Test
   public void getByIdInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         immagineDAO.getById(-1);
      });
   }

   @Test
   public void getById() {
      immagineDAO.save(immagine);

      assertNotNull(immagineDAO.getById(immagine.getId()));

      immagineDAO.delete(immagine);
   }

   @Test
   public void getAll() {
      immagineDAO.save(immagine);

      List<Immagine> immagineList = immagineDAO.getAll();

      assertAll(
              () -> assertNotNull(immagineList),
              () -> assertTrue(immagineList.size() > 0)
      );

      immagineDAO.delete(immagine);
   }

   @Test
   public void saveNullEntity() {
      assertThrows(IllegalArgumentException.class, () -> {
         immagineDAO.save(null);
      });
   }

   @Test
   public void saveEmptyObject() {
      assertThrows(RuntimeException.class, () -> {
         immagineDAO.save(new Immagine());
      });
   }

   @Test
   public void save() {
      assertTrue(immagineDAO.save(immagine));
      immagineDAO.delete(immagine);
   }

   @Test
   public void updateNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         immagineDAO.update(null);
      });
   }

   @Test
   public void updateEmptyObject() {
      Immagine immagine1 = new Immagine();
      immagine1.setPath(null);

      assertFalse(immagineDAO.update(immagine1));
   }

   @Test
   public void update() {
      immagineDAO.save(immagine);

      Immagine immagine2 = immagine;
      immagine2.setPath("----");
      assertTrue(immagineDAO.update(immagine2));

      immagineDAO.delete(immagine);
   }

   @Test
   public void deleteNullObject() {
      assertThrows(IllegalArgumentException.class, () -> {
         immagineDAO.delete(null);
      });
   }

   @Test
   public void deleteEmptyObject() {
      Immagine immagine1 = new Immagine();
      immagine1.setPath(null);

      assertFalse(immagineDAO.delete(immagine1));
   }

   @Test
   public void delete() {
      immagineDAO.save(immagine);

      assertTrue(immagineDAO.delete(immagine));
   }

   @Test
   public void deleteByIdCampagnaInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         ((ImmagineDAO)immagineDAO).deleteByIdCampagna(-1);
      });
   }

   @Test
   public void deleteByIdCampagna() {
      immagineDAO.save(immagine);

      assertTrue(((ImmagineDAO)immagineDAO).deleteByIdCampagna(immagine.getCampagna().getIdCampagna()));
   }

   @Test
   public void getByIdCampagnaInvalidId() {
      assertThrows(IllegalArgumentException.class, () -> {
         ((ImmagineDAO)immagineDAO).getByIdCampagna(-1);
      });
   }

   @Test
   public void getByIdCampagna() {
      immagineDAO.save(immagine);

      List<Immagine> immagineList = ((ImmagineDAO)immagineDAO).getByIdCampagna(immagine.getCampagna().getIdCampagna());

      assertAll(
              () -> assertNotNull(immagineList),
              () -> assertTrue(immagineList.size() > 0)
      );

      immagineDAO.delete(immagine);
   }

   @Test
   public void extractNullResultSet() {
      assertThrows(IllegalArgumentException.class, () -> {
         immagineDAO.extract(null);
      });
   }

   @After
   public void clean() {
      categoriaDAO.delete(categoria);
      campagnaDAO.delete(campagna);
      utenteDAO.delete(utente);
      ConPool.getInstance().closeDataSource();
   }
}

