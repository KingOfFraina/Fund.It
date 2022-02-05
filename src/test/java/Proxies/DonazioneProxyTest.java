package Proxies;

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
    static DAO<Utente> utenteDAO;
    static DAO<Campagna> campagnaDAO;
    static DAO<Categoria> categoriaDAO;
    static Donazione d;
    static Utente utente;
    static Campagna campagna;
    static Categoria categoria;

    @BeforeClass
    public static void setUp() {
        campagnaDAO = new CampagnaDAO();
        utenteDAO = new UtenteDAO();
        categoriaDAO = new CategoriaDAO();
        utente = new Utente();

        campagna = new Campagna();
        categoria = new Categoria();
        d = new Donazione();
        categoria.setNome("cat");
        categoriaDAO.save(categoria);

        utente.setCf("we");
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
    public void testGetCampagnaNullTitolo() {
        String temp = d.getCampagna().getTitolo();
        d.getCampagna().setTitolo(null);
        assertEquals(temp, proxy.getCampagna().getTitolo());
        d.getCampagna().setTitolo(temp);
    }

    @Test
    public void testGetNullCampagna() {
        d.setCampagna(null);
        assertThrows(IllegalArgumentException.class,
                () -> proxy.getCampagna());

        d.setCampagna(campagna);
    }

    @Test
    public void testGetCampagnaNullCategoria() {
        campagna.setCategoria(null);
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
        Utente temp = d.getUtente();
        d.setUtente(null);
        assertThrows(IllegalArgumentException.class,
                () -> proxy.getUtente());
        d.setUtente(temp);
    }

    @Test
    public void testGetUtenteCfNull() {
        String temp = utente.getCf();
        utente.setCf(null);
        assertEquals(utente.getIdUtente(), proxy.getUtente().getIdUtente());
        utente.setCf(temp);
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
