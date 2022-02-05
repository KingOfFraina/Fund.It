package Proxies;

import model.DAO.*;
import model.beans.*;
import model.beans.proxies.CampagnaProxy;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampagnaProxyTest {
   static DAO<Utente> utenteDAO;
   static DAO<Campagna> campagnaDAO;
   static DAO<Categoria> categoriaDAO;
   static DAO<Donazione> donazioneDAO;
   static DAO<Segnalazione> segnalazioneDAO;
   static DAO<Immagine> immagineDAO;
   static CampagnaProxy campagnaProxy;
   static Utente utente;
   static Categoria categoria;
   static Campagna campagna;
   static Donazione donazione;
   static Segnalazione segnalazione;
   static Immagine immagine;

   @BeforeClass
   public static void setup() throws SQLException {
      ConPool.getInstance().getConnection();

      utenteDAO = new UtenteDAO();
      categoriaDAO = new CategoriaDAO();
      campagnaDAO = new CampagnaDAO();
      donazioneDAO = new DonazioneDAO();
      segnalazioneDAO = new SegnalazioneDAO();
      immagineDAO = new ImmagineDAO();

      utente = new Utente();
      categoria = new Categoria();
      campagna = new Campagna();
      immagine = new Immagine();

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

      immagine = new Immagine();
      immagine.setCampagna(campagna);
      immagine.setPath("Path");

      campagnaProxy = new CampagnaProxy(campagna);
   }

   @Test
   public void getSegnalazioniLazy() {
      campagnaDAO.save(campagna);
      segnalazioneDAO.save(segnalazione);

      campagna.setSegnalazioni(null);
      List<Segnalazione> segnalazioneList = campagnaProxy.getSegnalazioni();

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
      campagna.setSegnalazioni(null);
      List<Segnalazione> segnalazioneList = campagnaProxy.getSegnalazioni();

      assertAll(
              () -> assertNotNull(segnalazioneList),
              () -> assertEquals(0, segnalazioneList.size())
      );
   }

   @Test
   public void getSegnalazioni() {
      campagnaDAO.save(campagna);

      campagna.setSegnalazioni(new ArrayList<>());
      List<Segnalazione> segnalazioneList = campagnaProxy.getSegnalazioni();

      assertAll(
              () -> assertNotNull(segnalazioneList),
              () -> assertEquals(0, segnalazioneList.size())
      );

      campagnaDAO.delete(campagna);
   }

   @Test
   public void getUtenteLazy() {
      campagnaDAO.save(campagna);

      utente.setCf(null);
      campagna.setUtente(utente);
      Utente utente1 = campagnaProxy.getUtente();
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

      segnalazioneDAO.delete(segnalazione);
      campagnaDAO.delete(campagna);
   }

   @Test
   public void getUtente() {
      campagnaDAO.save(campagna);
      campagna.setUtente(utente);

      assertNotNull(campagnaProxy.getUtente());

      campagnaDAO.delete(campagna);
   }

   @Test
   public void getDonazioneLazy() {
      campagnaDAO.save(campagna);
      donazioneDAO.save(donazione);

      campagna.setDonazioni(null);
      List<Donazione> donazioneList = campagnaProxy.getDonazioni();

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
      campagna.setDonazioni(null);
      List<Donazione> donazioneList = campagnaProxy.getDonazioni();

      assertAll(
              () -> assertNotNull(donazioneList),
              () -> assertEquals(0, donazioneList.size())
      );
   }

   @Test
   public void getDonazioni() {
      campagna.setDonazioni(new ArrayList<>());
      List<Donazione> donazioneList = campagnaProxy.getDonazioni();

      assertAll(
              () -> assertNotNull(donazioneList),
              () -> assertEquals(0, donazioneList.size())
      );
   }

   @Test
   public void getImmagineLazy() {
      campagnaDAO.save(campagna);
      immagineDAO.save(immagine);

      campagna.setImmagini(null);
      List<Immagine> immagineList = campagnaProxy.getImmagini();

      assertAll(
              () -> assertNotNull(immagineList),
              () -> assertEquals(1, immagineList.size()),
              () -> assertEquals(immagine.getId(), immagineList.get(0).getId()),
              () -> assertEquals(immagine.getPath(), immagineList.get(0).getPath()),
              () -> assertEquals(immagine.getCampagna().getIdCampagna(), immagineList.get(0).getCampagna().getIdCampagna())

      );

      immagineDAO.delete(immagine);
      campagnaDAO.delete(campagna);
   }

   @Test
   public void getImmaginiEmptySet() {
      campagna.setImmagini(null);
      campagnaDAO.save(campagna);

      List<Immagine> immagineList = campagnaProxy.getImmagini();

      assertAll(
              () -> assertNotNull(immagineList),
              () -> assertEquals(0, immagineList.size())
      );

      campagnaDAO.delete(campagna);
   }

   @Test
   public void getImmagini() {
      campagna.setImmagini(new ArrayList<>());
      List<Immagine> immagineList = campagnaProxy.getImmagini();

      assertAll(
              () -> assertNotNull(immagineList),
              () -> assertEquals(0, immagineList.size())
      );
   }

   @AfterClass
   public static void clear() {
      categoriaDAO.delete(categoria);
      utenteDAO.delete(utente);
      ConPool.getInstance().closeDataSource();
   }
}
