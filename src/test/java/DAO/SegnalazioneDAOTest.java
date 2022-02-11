package DAO;

import model.DAO.CampagnaDAO;
import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.DAO.SegnalazioneDAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.Segnalazione;
import model.beans.StatoCampagna;
import model.beans.StatoSegnalazione;
import model.beans.Utente;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SegnalazioneDAOTest {
    static DAO<Segnalazione> dao;
    static DAO<Utente> utenteDAO;
    static DAO<Campagna> campagnaDAO;
    static DAO<Categoria> categoriaDAO;
    static Segnalazione s1;
    static Utente segnalatore;
    static Campagna campagna;
    static Categoria categoria;


    @BeforeClass
    public static void setUp() {
        dao = new SegnalazioneDAO();
        campagnaDAO = new CampagnaDAO();
        utenteDAO = new UtenteDAO();
        categoriaDAO = new CategoriaDAO();
        segnalatore = new Utente();
        campagna = new Campagna();
        categoria = new Categoria();
        s1 = new Segnalazione();

        categoria.setNome("cat");
        categoriaDAO.save(categoria);

        segnalatore.setCf("We");
        segnalatore.setAdmin(true);
        segnalatore.setDataBan(LocalDateTime.now());
        segnalatore.setNome("strunz");
        segnalatore.setDataDiNascita(LocalDate.now());
        segnalatore.setCognome("lollo");
        segnalatore.setEmail("emailbella");
        segnalatore.setFotoProfilo("foto");
        segnalatore.setCap("cap");
        segnalatore.setCitta("city");
        segnalatore.setStrada("strada");
        segnalatore.setTelefono("sexo");
        segnalatore.setPassword("ldfoefer");
        utenteDAO.save(segnalatore);

        campagna.setStato(StatoCampagna.ATTIVA);
        campagna.setSommaRaccolta(100.0);
        campagna.setSommaTarget(900.0);
        campagna.setCategoria(categoria);
        campagna.setTitolo("titolo");
        campagna.setDescrizione("desc");
        campagna.setUtente(segnalatore);
        campagnaDAO.save(campagna);

        s1.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
        s1.setDescrizione("descrizione segnalazione");
        s1.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        s1.setSegnalatore(segnalatore);
        s1.setSegnalato(segnalatore);
        s1.setCampagnaSegnalata(campagna);
        dao.save(s1);
    }

    @Test
    public void testGetById() {
        int id = -1;

        assertThrows(IllegalArgumentException.class,
                () -> dao.getById(id));

        Segnalazione s3 = dao.getById(s1.getIdSegnalazione());
        assertNotNull(s3);
        assertEquals(s1.getIdSegnalazione(), s3.getIdSegnalazione());
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
        Segnalazione s2 = new Segnalazione();
        s2.setStatoSegnalazione(StatoSegnalazione.ARCHIVIATA);
        Utente utente = new Utente();
        utente.setIdUtente(1);
        s2.setSegnalato(segnalatore);
        s2.setSegnalatore(segnalatore);
        s2.setDescrizione("descrizione");
        Campagna c1 = new Campagna();
        c1.setIdCampagna(1);
        s2.setCampagnaSegnalata(campagna);
        s2.setDataOra(LocalDateTime.now());
        assertTrue(dao.save(s2));
        assertTrue(dao.delete(s2));
    }

    @Test
    public void testUpdateNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.update(null));
    }

    @Test
    public void testUpdateEntity() {
        Segnalazione s2 = dao.getById(s1.getIdSegnalazione());
        assertNotNull(s2);
        s2.setStatoSegnalazione(StatoSegnalazione.RISOLTA);
        assertTrue(dao.update(s2));
        assertEquals(s2.getStatoSegnalazione(), dao.getById(s1.getIdSegnalazione()).getStatoSegnalazione());
    }

    @Test
    public void testDeleteNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.delete(null));
    }

    @Test
    public void testGetByIdUtente() {
        SegnalazioneDAO segnalazioneDAO = (SegnalazioneDAO) dao;
        List<Segnalazione> segnalazioni = segnalazioneDAO.getByIdUtente(segnalatore.getIdUtente());
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
        List<Segnalazione> segnalazioni = segnalazioneDAO.getByIdCampagna(s1.getCampagna().getIdCampagna());
        assertNotNull(segnalazioni);
        assertTrue(segnalazioni.size() > 0);
    }

    @AfterClass
    public static void clearAll() {
        categoriaDAO.delete(categoria);
        utenteDAO.delete(segnalatore);
        campagnaDAO.delete(campagna);
        dao.delete(s1);
        ConPool.getInstance().closeDataSource();
    }
}
