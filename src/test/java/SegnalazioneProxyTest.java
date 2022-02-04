import model.DAO.*;
import model.beans.*;
import model.beans.proxies.SegnalazioneProxy;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SegnalazioneProxyTest {
   static DAO<Utente> utenteDAO;
   static DAO<Campagna> campagnaDAO;
   static DAO<Categoria> categoriaDAO;
   static DAO<Segnalazione> segnalazioneDAO;
   static SegnalazioneProxy segnalazioneProxy;
   static Utente utente;
   static Categoria categoria;
   static Campagna campagna;
   static Segnalazione segnalazione;

   @BeforeClass
   public static void setup() throws SQLException {
      ConPool.getInstance().getConnection();

      utenteDAO = new UtenteDAO();
      categoriaDAO = new CategoriaDAO();
      campagnaDAO = new CampagnaDAO();
      segnalazioneDAO = new SegnalazioneDAO();

      utente = new Utente();
      categoria = new Categoria();
      campagna = new Campagna();

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

      utenteDAO.save(utente);

      categoria.setNome("");

      categoriaDAO.save(categoria);

      campagna.setStato(StatoCampagna.ATTIVA);
      campagna.setTitolo("titolo");
      campagna.setDescrizione("descrizione");
      campagna.setSommaRaccolta(15d);
      campagna.setSommaTarget(15d);
      campagna.setCategoria(categoria);
      campagna.setUtente(utente);

      campagnaDAO.save(campagna);

      segnalazione = new Segnalazione();
      segnalazione.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
      segnalazione.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
      segnalazione.setSegnalato(utente);
      segnalazione.setSegnalatore(utente);
      segnalazione.setDescrizione("descrizione");
      segnalazione.setCampagnaSegnalata(campagna);

      segnalazioneProxy = new SegnalazioneProxy(segnalazione);
      segnalazioneDAO.save(segnalazione);
   }

   @Test
   public void getSegnalatoreLazy() {
      utente.setCf(null);
      campagna.setUtente(utente);
      Utente utente1 = segnalazioneProxy.getSegnalatore();
      utente.setCf("cf");

      assertAll(
              () -> assertNotNull(utente1),
              () -> assertEquals(utente.isAdmin(), utente1.isAdmin()),
              () -> assertEquals(utente.getCap(), utente1.getCap()),
              () -> assertEquals(utente.getCf(), utente1.getCf()),
              () -> assertEquals(utente.getCitta(), utente1.getCitta()),
              () -> assertEquals(utente.getCognome(), utente1.getCognome()),
              () -> assertEquals(utente.getDataBan().truncatedTo(ChronoUnit.MINUTES), utente1.getDataBan().truncatedTo(ChronoUnit.MINUTES)),
              () -> assertEquals(utente.getDataDiNascita(), utente1.getDataDiNascita()),
              () -> assertEquals(utente.getEmail(), utente1.getEmail()),
              () -> assertEquals(utente.getFotoProfilo(), utente1.getFotoProfilo()),
              () -> assertEquals(utente.getNome(), utente1.getNome()),
              () -> assertEquals(utente.getPassword(), utente1.getPassword()),
              () -> assertEquals(utente.getStrada(), utente1.getStrada()),
              () -> assertEquals(utente.getTelefono(), utente1.getTelefono())
      );
   }

   @Test
   public void getSegnalato() {
      campagna.setUtente(utente);
      assertNotNull(segnalazioneProxy.getSegnalato());
   }

   @Test
   public void getSegnalatoLazy() {
      utente.setCf(null);
      campagna.setUtente(utente);
      Utente utente1 = segnalazioneProxy.getSegnalato();
      utente.setCf("cf");

      assertAll(
              () -> assertNotNull(utente1),
              () -> assertEquals(utente.isAdmin(), utente1.isAdmin()),
              () -> assertEquals(utente.getCap(), utente1.getCap()),
              () -> assertEquals(utente.getCf(), utente1.getCf()),
              () -> assertEquals(utente.getCitta(), utente1.getCitta()),
              () -> assertEquals(utente.getCognome(), utente1.getCognome()),
              () -> assertEquals(utente.getDataBan().truncatedTo(ChronoUnit.MINUTES), utente1.getDataBan().truncatedTo(ChronoUnit.MINUTES)),
              () -> assertEquals(utente.getDataDiNascita(), utente1.getDataDiNascita()),
              () -> assertEquals(utente.getEmail(), utente1.getEmail()),
              () -> assertEquals(utente.getFotoProfilo(), utente1.getFotoProfilo()),
              () -> assertEquals(utente.getNome(), utente1.getNome()),
              () -> assertEquals(utente.getPassword(), utente1.getPassword()),
              () -> assertEquals(utente.getStrada(), utente1.getStrada()),
              () -> assertEquals(utente.getTelefono(), utente1.getTelefono())
      );
   }

   @Test
   public void getSegnalatore() {
      campagna.setUtente(utente);
      assertNotNull(segnalazioneProxy.getSegnalatore());
   }

   @Test
   public void getCampagnaLazy() {
      segnalazione.getCampagnaSegnalata().setTitolo(null);
      Campagna campagna1 = segnalazioneProxy.getCampagna();
      campagna.setTitolo("titolo");
      assertAll(
              () -> assertNotNull(campagna),
              () -> assertEquals(campagna.getStato(), campagna1.getStato()),
              () -> assertEquals(campagna.getTitolo(), campagna1.getTitolo()),
              () -> assertEquals(campagna.getDescrizione(), campagna1.getDescrizione()),
              () -> assertEquals(campagna.getSommaRaccolta(), campagna1.getSommaRaccolta()),
              () -> assertEquals(campagna.getSommaTarget(), campagna1.getSommaTarget()),
              () -> assertEquals(campagna.getCategoria().getIdCategoria(), campagna1.getCategoria().getIdCategoria()),
              () -> assertEquals(campagna.getUtente().getIdUtente(), campagna1.getUtente().getIdUtente())

      );
   }

   @Test
   public void getCampagne() {
      Campagna c = new Campagna();
      c.setTitolo("");
      segnalazione.setCampagnaSegnalata(c);
      assertNotNull(segnalazioneProxy.getCampagna());
   }

   @AfterClass
   public static void clear() {
      segnalazioneDAO.delete(segnalazione);
      campagnaDAO.delete(campagna);
      categoriaDAO.delete(categoria);
      utenteDAO.delete(utente);
      ConPool.getInstance().closeDataSource();
   }
}
