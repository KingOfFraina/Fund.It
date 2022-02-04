import model.beans.Campagna;
import model.beans.Categoria;
import model.beans.Donazione;
import model.beans.Utente;
import model.beans.proxies.DonazioneProxy;
import model.beans.proxyInterfaces.DonazioneInterface;
import model.storage.ConPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DonazioneProxyTest {
    static DonazioneInterface proxy;
    static Donazione d;

    @BeforeClass
    public static void setUp() {
        d = new Donazione();
        proxy = new DonazioneProxy(d);
    }

    @Test
    public void testGetCampagnaAndCategoria() {
        Campagna campagna = new Campagna();
        campagna.setIdCampagna(1);
        d.setCampagna(campagna);
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
        Utente utente = new Utente();
        utente.setIdUtente(1);
        utente.setCf("wewe");
        d.setUtente(utente);
        assertEquals(utente.getIdUtente(), proxy.getUtente().getIdUtente());
    }

    @AfterClass
    public static void clear() {
        ConPool.getInstance().closeDataSource();
    }
}
