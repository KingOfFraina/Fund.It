import model.DAO.*;
import model.beans.*;
import model.beans.proxies.UtenteProxy;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class utenteProxyTest {
   static DAO<Utente> utenteDAO;
   static DAO<Campagna> campagnaDAO;
   static DAO<Categoria> categoriaDAO;
   static DAO<Donazione> donazioneDAO;
   static DAO<Segnalazione> segnalazioneDAO;
   static UtenteProxy utenteProxy;
   static Utente utente;
   static Categoria categoria;
   static Campagna campagna;
   static Donazione donazione;
   static Segnalazione segnalazione;

   @BeforeClass
   public static void setup() throws SQLException {
      ConPool.getInstance().getConnection();

      utenteDAO = new UtenteDAO();
      categoriaDAO = new CategoriaDAO();
      campagnaDAO = new CampagnaDAO();
      donazioneDAO = new DonazioneDAO();
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

      donazione = new Donazione();
      donazione.setDataOra(LocalDateTime.now());
      donazione.setRicevuta("ricevuta");
      donazione.setSommaDonata(1f);
      donazione.setCommento("commento");
      donazione.setAnonimo(true);
      donazione.setUtente(utente);
      donazione.setCampagna(campagna);

      segnalazione = new Segnalazione();
      segnalazione.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
      segnalazione.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
      segnalazione.setSegnalato(utente);
      segnalazione.setSegnalatore(utente);
      segnalazione.setDescrizione("descrizione");
      segnalazione.setCampagnaSegnalata(campagna);

      utenteProxy = new UtenteProxy(utente);
   }

   @Test
   public void getCampagneLazy() {
      campagnaDAO.save(campagna);

      utente.setCampagne(null);
      List<Campagna> campagnaList = utenteProxy.getCampagne();

      assertAll(
              () -> assertNotNull(campagnaList),
              () -> assertEquals(1, campagnaList.size()),
              () -> assertEquals(campagna.getStato(), campagnaList.get(0).getStato()),
              () -> assertEquals(campagna.getTitolo(), campagnaList.get(0).getTitolo()),
              () -> assertEquals(campagna.getDescrizione(), campagnaList.get(0).getDescrizione()),
              () -> assertEquals(campagna.getSommaRaccolta(), campagnaList.get(0).getSommaRaccolta()),
              () -> assertEquals(campagna.getSommaTarget(), campagnaList.get(0).getSommaTarget()),
              () -> assertEquals(campagna.getCategoria().getIdCategoria(), campagnaList.get(0).getCategoria().getIdCategoria()),
              () -> assertEquals(campagna.getUtente().getIdUtente(), campagnaList.get(0).getUtente().getIdUtente())

      );

      campagnaDAO.delete(campagna);
   }

   @Test
   public void getCampagneEmptySet() {
      utente.setCampagne(null);
      List<Campagna> campagnaList = utenteProxy.getCampagne();

      assertAll(
              () -> assertNotNull(campagnaList),
              () -> assertEquals(0, campagnaList.size())
      );
   }

   @Test
   public void getCampagne() {
      utente.setCampagne(new ArrayList<>());
      List<Campagna> campagnaList = utenteProxy.getCampagne();

      assertAll(
              () -> assertNotNull(campagnaList),
              () -> assertEquals(0, campagnaList.size())
      );
   }

   @Test
   public void getDonazioneLazy() {
      campagnaDAO.save(campagna);
      donazioneDAO.save(donazione);

      utente.setDonazioni(null);
      List<Donazione> donazioneList = utenteProxy.getDonazioni();

      assertAll(
              () -> assertNotNull(donazioneList),
              () -> assertEquals(1, donazioneList.size()),
              () -> assertEquals(donazione.getDataOra(), donazioneList.get(0).getDataOra()),
              () -> assertEquals(donazione.getRicevuta(), donazioneList.get(0).getRicevuta()),
              () -> assertEquals(donazione.getSommaDonata(), donazioneList.get(0).getSommaDonata()),
              () -> assertEquals(donazione.getCommento(), donazioneList.get(0).getCommento()),
              () -> assertEquals(donazione.isAnonimo(), donazioneList.get(0).isAnonimo()),
              () -> assertEquals(donazione.getUtente().getIdUtente(), donazioneList.get(0).getUtente().getIdUtente()),
              () -> assertEquals(donazione.getCampagna().getIdCampagna(), donazioneList.get(0).getCampagna().getIdCampagna())

      );

      donazioneDAO.delete(donazione);
      campagnaDAO.delete(campagna);
   }

   @Test
   public void getDonazioniEmptySet() {
      utente.setDonazioni(null);
      List<Donazione> donazioneList = utenteProxy.getDonazioni();

      assertAll(
              () -> assertNotNull(donazioneList),
              () -> assertEquals(0, donazioneList.size())
      );
   }

   @Test
   public void getDonazioni() {
      utente.setDonazioni(new ArrayList<>());
      List<Donazione> donazioneList = utenteProxy.getDonazioni();

      assertAll(
              () -> assertNotNull(donazioneList),
              () -> assertEquals(0, donazioneList.size())
      );
   }

   @Test
   public void getSegnalazioniLazy() {
      campagnaDAO.save(campagna);
      segnalazioneDAO.save(segnalazione);

      utente.setSegnalazioni(null);
      List<Segnalazione> segnalazioneList = utenteProxy.getSegnalazioni();

      assertAll(
              () -> assertNotNull(segnalazioneList),
              () -> assertEquals(1, segnalazioneList.size()),
              () -> assertEquals(segnalazione.getDataOra(), segnalazioneList.get(0).getDataOra()),
              () -> assertEquals(segnalazione.getStatoSegnalazione(), segnalazioneList.get(0).getStatoSegnalazione()),
              () -> assertEquals(segnalazione.getSegnalato().getIdUtente(), segnalazioneList.get(0).getSegnalato().getIdUtente()),
              () -> assertEquals(segnalazione.getSegnalatore().getIdUtente(), segnalazioneList.get(0).getSegnalatore().getIdUtente()),
              () -> assertEquals(segnalazione.getDescrizione(), segnalazioneList.get(0).getDescrizione()),
              () -> assertEquals(segnalazione.getCampagnaSegnalata().getIdCampagna(), segnalazioneList.get(0).getCampagnaSegnalata().getIdCampagna())

      );

      segnalazioneDAO.delete(segnalazione);
      campagnaDAO.delete(campagna);
   }

   @Test
   public void getSegnalazioniEmptySet() {
      utente.setSegnalazioni(null);
      List<Segnalazione> segnalazioneList = utenteProxy.getSegnalazioni();

      assertAll(
              () -> assertNotNull(segnalazioneList),
              () -> assertEquals(0, segnalazioneList.size())
      );
   }

   @Test
   public void getSegnalazioni() {
      utente.setSegnalazioni(new ArrayList<>());
      List<Segnalazione> segnalazioneList = utenteProxy.getSegnalazioni();

      assertAll(
              () -> assertNotNull(segnalazioneList),
              () -> assertEquals(0, segnalazioneList.size())
      );
   }

   @AfterClass
   public static void clear() {
      categoriaDAO.delete(categoria);
      utenteDAO.delete(utente);
      ConPool.getInstance().closeDataSource();
   }
}
