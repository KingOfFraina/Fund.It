package DAO;

import model.DAO.*;
import model.beans.*;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DonazioneDAOTest {

    static DAO<Donazione> dao;
    static DAO<Campagna> campagnaDAO;
    static DAO<Utente> utenteDAO;
    static DAO<Categoria> categoriaDAO;
    static Donazione d1;
    static Campagna campagna;
    static Utente utente;
    static Categoria categoria;

    @BeforeClass
    public static void setUp() {
        dao = new DonazioneDAO();
        campagnaDAO = new CampagnaDAO();
        utenteDAO = new UtenteDAO();
        categoriaDAO = new CategoriaDAO();
        campagna = new Campagna();
        utente = new Utente();
        d1 = new Donazione();
        categoria = new Categoria();
        categoria.setNome("cat");
        categoriaDAO.save(categoria);

        utente.setCf("We");
        utente.setAdmin(true);
        utente.setDataBan(LocalDateTime.now());
        utente.setNome("strunz");
        utente.setDataDiNascita(LocalDate.now());
        utente.setCognome("lollo");
        utente.setEmail("emailbella");
        utente.setFotoProfilo("foto");
        utente.setCap("cap");
        utente.setCitta("city");
        utente.setStrada("strada");
        utente.setTelefono("sexo");
        utente.setPassword("ldfoefer");
        utenteDAO.save(utente);

        campagna.setStato(StatoCampagna.ATTIVA);
        campagna.setSommaRaccolta(100.0);
        campagna.setSommaTarget(900.0);
        campagna.setCategoria(categoria);
        campagna.setTitolo("titolo");
        campagna.setDescrizione("desc");
        campagna.setUtente(utente);
        campagnaDAO.save(campagna);

        d1.setCampagna(campagna);
        d1.setUtente(utente);
        d1.setSommaDonata(90);
        d1.setCommento("wella");
        d1.setRicevuta("uhu");
        d1.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        dao.save(d1);

    }

    @Test
    public void testGetByIdLessEqualThanZero() {
        int id = -1;
        assertThrows(IllegalArgumentException.class,
                () -> dao.getById(id));
    }

    @Test
    public void testGetByIdGreaterThanZero() {
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
    public void testTrueUpdate() {
        d1.setCommento("null");
        assertTrue(dao.update(d1));
    }


    @Test
    public void testGetByIdCampagna() {
        DonazioneDAO donazioneDAO = (DonazioneDAO) dao;
        assertNotNull(donazioneDAO.getByIdCampagna(1));
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
        categoriaDAO.delete(categoria);
        utenteDAO.delete(utente);
        campagnaDAO.delete(campagna);
        dao.delete(d1);
        ConPool.getInstance().closeDataSource();
    }
}
