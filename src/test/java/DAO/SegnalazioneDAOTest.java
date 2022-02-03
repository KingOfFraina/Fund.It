package DAO;

import model.DAO.DAO;
import model.DAO.SegnalazioneDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SegnalazioneDAOTest {
    static DAO<Segnalazione> dao;
    static Segnalazione s1;

    @BeforeClass
    public static void setUp() {
        dao = new SegnalazioneDAO();
        s1 = new Segnalazione();
    }

    @Test
    public void testGetById() {
        int id = -1;

        assertThrows(IllegalArgumentException.class,
                () -> dao.getById(id));

        Segnalazione s2 = new Segnalazione();
        Utente utente = new Utente();
        utente.setIdUtente(1);
        s2.setSegnalatore(utente);
        s2.setSegnalato(utente);
        s2.setDescrizione("Descrizione");
        s2.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
        s2.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        Campagna c = new Campagna();
        c.setIdCampagna(1);
        s2.setCampagnaSegnalata(c);

        assertTrue(dao.save(s2));
        Segnalazione s3 = dao.getById(s2.getIdSegnalazione());
        assertNotNull(s3);
        assertEquals(s2.getIdSegnalazione(), s3.getIdSegnalazione());
        assertTrue(dao.delete(s2));
    }

    @Test
    public void testGetAll() {
        List<Segnalazione> all = dao.getAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    public void testSaveNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.save(null));
    }

    @Test
    public void testSaveEntity() {
        Segnalazione s1 = new Segnalazione();
        s1.setStatoSegnalazione(StatoSegnalazione.ARCHIVIATA);
        Utente utente = new Utente();
        utente.setIdUtente(1);
        s1.setSegnalato(utente);
        s1.setSegnalatore(utente);
        s1.setDescrizione("descrizione");
        Campagna c1 = new Campagna();
        c1.setIdCampagna(1);
        s1.setCampagnaSegnalata(c1);
        s1.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertTrue(dao.save(s1));
        assertTrue(dao.delete(s1));
    }

    @Test
    public void testUpdateNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.update(null));
    }

    @Test
    public void testUpdateEntity() {
        Segnalazione s1 = dao.getById(1);
        assertNotNull(s1);
        s1.setStatoSegnalazione(StatoSegnalazione.RISOLTA);
        assertTrue(dao.update(s1));
        assertEquals(s1.getStatoSegnalazione(), dao.getById(1).getStatoSegnalazione());
    }

    @Test
    public void testDeleteNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.delete(null));
    }

    @Test
    public void testGetByIdUtente() {
        SegnalazioneDAO segnalazioneDAO = (SegnalazioneDAO) dao;
        List<Segnalazione> segnalazioni = segnalazioneDAO.getByIdUtente(1);
        assertNotNull(segnalazioni);
        assertTrue(segnalazioni.size() > 0);
    }

    @Test
    public void testGetByIdUtenteLessThanZero() {
        SegnalazioneDAO segnalazioneDAO = (SegnalazioneDAO) dao;
        assertThrows(IllegalArgumentException.class,
                () -> segnalazioneDAO.getByIdUtente(-1));
    }

    @Test
    public void testGetByIdCampagnaLessThanZero() {
        SegnalazioneDAO segnalazioneDAO = (SegnalazioneDAO) dao;
        assertThrows(IllegalArgumentException.class,
                () -> segnalazioneDAO.getByIdCampagna(-1));
    }

    @Test
    public void testGetByIdCampagna() {
        SegnalazioneDAO segnalazioneDAO = (SegnalazioneDAO) dao;
        List<Segnalazione> segnalazioni = segnalazioneDAO.getByIdCampagna(1);
        assertNotNull(segnalazioni);
        assertTrue(segnalazioni.size() > 0);
    }

    @AfterClass
    public static void clearAll() {
        ConPool.getInstance().closeDataSource();
    }
}
