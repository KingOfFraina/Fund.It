import model.DAO.CampagnaDAO;
import model.DAO.CategoriaDAO;
import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.*;
import model.beans.proxies.DonazioneProxy;
import model.beans.proxyInterfaces.DonazioneInterface;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DonazioneProxyTest {
    static DonazioneInterface proxy;
    static DAO<Donazione> donazioneDAO;
    static DAO<Utente> utenteDAO;
    static DAO<Campagna> campagnaDAO;
    static DAO<Categoria> categoriaDAO;
    static Donazione d;
    static Utente utente;
    static Campagna campagna;
    static Categoria categoria;

    @BeforeClass
    public static void setUp() {
        d = new Donazione();
        campagnaDAO = new CampagnaDAO();
        utenteDAO = new UtenteDAO();
        categoriaDAO = new CategoriaDAO();
        utente = new Utente();
        campagna = new Campagna();
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
        d.setCampagna(campagna);
        d.setUtente(utente);
        proxy = new DonazioneProxy(d);
    }

    @Test
    public void testGetCampagnaAndCategoria() {
        campagna = proxy.getCampagna();
        assertNotNull(campagna);
        assertNotNull(campagna.getCategoria());
        assertNotNull(campagna.getCategoria().getNome());
        assertEquals(campagna.getIdCampagna(), d.getCampagna().getIdCampagna());
    }

    @Test
    public void testGetNullCampagna() {
        d.setCampagna(null);
        assertThrows(IllegalArgumentException.class,
                () -> proxy.getCampagna());
    }

    @Test
    public void testGetCampagnaNullCategoria() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNome("krkrkg");
        Campagna campagna = new Campagna();
        campagna.setIdCampagna(99);
        campagna.setCategoria(categoria);
        d.setCampagna(campagna);
        assertEquals(campagna.getIdCampagna(), proxy.getCampagna().getIdCampagna());
    }

    @Test
    public void testGetCampagnaWithInvalidId() {
        Campagna campagna = new Campagna();
        campagna.setIdCampagna(-1);
        d.setCampagna(campagna);
        assertThrows(RuntimeException.class,
                () -> proxy.getCampagna());
    }

    @Test
    public void testGetUtenteNull() {
        d.setUtente(null);
        assertThrows(IllegalArgumentException.class,
                () -> proxy.getUtente());
    }

    @Test
    public void testGetUtenteCfNull() {
        Utente utente = new Utente();
        utente.setIdUtente(1);
        utente.setCf(null);
        d.setUtente(utente);
        assertEquals(utente.getIdUtente(), proxy.getUtente().getIdUtente());
    }

    @Test
    public void testGetUtenteCf() {
        assertEquals(utente.getIdUtente(), proxy.getUtente().getIdUtente());
    }

    @AfterClass
    public static void clear() {
        categoriaDAO.delete(categoria);
        utenteDAO.delete(utente);
        campagnaDAO.delete(campagna);

        ConPool.getInstance().closeDataSource();
    }
}
