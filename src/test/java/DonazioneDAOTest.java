import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.storage.ConPool;
import net.sf.saxon.expr.parser.Loc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DonazioneDAOTest {

    DAO<Donazione> dao;
    Donazione d1, d2, d3;

    @Before
    public void setUp() {
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

    @After
    public void clearAll() {
        dao.delete(d1);
        ConPool.getInstance().closeDataSource();
    }
}
