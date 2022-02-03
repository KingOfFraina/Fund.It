package DAO;

import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DonazioneDAOTest {

    static DAO<Donazione> dao;
    static Donazione d1;
    static Donazione d2;
    static Donazione d3;

    @BeforeClass
    public static void setUp() {
        dao = new DonazioneDAO();
        d1 = new Donazione();
        d2 = new Donazione();
        d3 = new Donazione();
    }

    @Test
    public void testGetByIdLessEqualThanZero() {
        int id = -1;
        assertThrows(IllegalArgumentException.class,
                () -> dao.getById(id));
    }

    @Test
    public void testGetByIdGreaterThanZero() {
        int id = 90;
        d1.setIdDonazione(id);
        d1.setAnonimo(true);
        Utente utente = new Utente();
        utente.setIdUtente(90);
        d1.setUtente(utente);
        d1.setAnonimo(true);
        d1.setSommaDonata(90);
        Campagna campagna = new Campagna();
        campagna.setIdCampagna(90);
        d1.setCampagna(campagna);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        d1.setDataOra(now);
        d1.setCommento("wewe");
        d1.setRicevuta("ricevuta190");
        assertTrue(dao.save(d1));
        Donazione d4 = dao.getById(d1.getIdDonazione());
        assertNotNull(d4);
        assertAll(() -> assertEquals(d1.getUtente().getIdUtente(), d4.getUtente().getIdUtente()),
                () -> assertEquals(d1.getSommaDonata(), d4.getSommaDonata()),
                () -> assertEquals(d1.getCampagna().getIdCampagna(), d4.getCampagna().getIdCampagna()),
                () -> assertEquals(d1.getCommento(), d4.getCommento()),
                () -> assertEquals(d1.getDataOra(), d4.getDataOra()),
                () -> assertEquals(d1.getRicevuta(), d4.getRicevuta()),
                () -> assertEquals(d1.isAnonimo(), d4.isAnonimo()));
    }

    @Test
    public void testGetAll() {
        assertNotNull(dao.getAll());
    }

    @Test
    public void testGetByUtente() {
        DonazioneDAO donazioneDAO = (DonazioneDAO) dao;

        assertNotNull(donazioneDAO.getAllByUtente(1));
    }

    @Test
    public void testGetByIdCampagnaLessThanZero() {
        int idCampagna = -1;
        DonazioneDAO donazioneDAO = (DonazioneDAO) dao;

        assertThrows(IllegalArgumentException.class,
                () -> donazioneDAO.getByIdCampagna(idCampagna));
    }

    @Test
    public void testGetByIdUtenteLessThanZero() {
        int idUtente = -1;

        DonazioneDAO donazioneDAO = (DonazioneDAO) dao;
        assertThrows(IllegalArgumentException.class,
                () -> donazioneDAO.getAllByUtente(idUtente));
    }

    @Test
    public void testThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.delete(null));
        assertThrows(IllegalArgumentException.class,
                () -> dao.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> dao.save(null));
    }

    @Test
    public void testFalseUpdate() {
        d1.setUtente(null);
        d1.setCommento(null);
        assertFalse(dao.update(d1));
    }

    @Test
    public void testTrueUpdate() {
        d3.setIdDonazione(20);
        d3.setCommento("sesso");
        assertTrue(dao.update(d3));
    }

    @Test
    public void testGetByIdCampagna() {
        DonazioneDAO donazioneDAO = (DonazioneDAO) dao;
        assertNotNull(donazioneDAO.getByIdCampagna(1));
    }

    @Test
    public void testThrowsRuntimeException() {
        d1 = new Donazione();
        Utente utente = new Utente();
        utente.setIdUtente(999999);
        d1.setIdDonazione(9999999);
        d1.setUtente(utente);
        assertThrows(RuntimeException.class,
                () -> dao.save(d1));
    }

    @Test
    public void testExtractDonazione() {
        try {
            assertNull(dao.extract(null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void clearAll() {
        dao.delete(d1);
        ConPool.getInstance().closeDataSource();
    }
}
