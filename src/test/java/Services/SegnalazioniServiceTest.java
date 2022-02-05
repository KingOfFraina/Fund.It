package Services;

import model.DAO.DAO;
import model.DAO.SegnalazioneDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;
import model.services.SegnalazioniService;
import model.services.SegnalazioniServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class SegnalazioniServiceTest {
    static DAO<Segnalazione> mockDao;
    static Segnalazione segnalazione;
    static SegnalazioniService segnalazioniService;
    static Campagna campagna;
    static Utente utente;


    @BeforeClass
    public static void setUp() {
        mockDao = Mockito.mock(SegnalazioneDAO.class);
        segnalazioniService = new SegnalazioniServiceImpl(mockDao);
        segnalazione = new Segnalazione();
        utente = new Utente();
        campagna = new Campagna();
        segnalazione.setIdSegnalazione(10);
    }

    @Test
    public void testTrovaSegnalazioneIdLessThanZero() {
        assertThrows(IllegalArgumentException.class,
                () -> segnalazioniService.trovaSegnalazione(-1));
    }

    @Test
    public void testTrovaSegnalazioneIdGreaterThanZero() {
        Mockito.when(mockDao.getById(segnalazione.getIdSegnalazione())).thenReturn(segnalazione);
        Segnalazione temp = segnalazioniService.trovaSegnalazione(segnalazione.getIdSegnalazione());
        assertNotNull(temp);
        assertEquals(segnalazione.getIdSegnalazione(), temp.getIdSegnalazione());
    }

    @Test
    public void testRisolviSegnalazioneIdLessThanZero() {
        assertThrows(IllegalArgumentException.class,
                () -> segnalazioniService.risolviSegnalazione(0,
                        StatoSegnalazione.ARCHIVIATA));
    }

    @Test
    public void testRisolviSegnalazioneIdGreaterThanZero() {
        Mockito.when(mockDao.update(segnalazione)).thenReturn(true);
        Mockito.when(mockDao.getById(segnalazione.getIdSegnalazione())).thenReturn(segnalazione);
        assertTrue(segnalazioniService.risolviSegnalazione(segnalazione.getIdSegnalazione(),
                StatoSegnalazione.RISOLTA));
    }

    @Test
    public void testRisolviSegnalazioneEntityNotFound() {
        assertThrows(RuntimeException.class,
                () -> segnalazioniService
                        .risolviSegnalazione(90, StatoSegnalazione.RISOLTA));
    }

    @Test
    public void testSegnalaCampagnaNullArguments() {
        assertAll(() ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(campagna, utente, null)),
                () ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(null, utente, "")),
                () ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(campagna, null, "")));
    }

   /* @Test
    public void testSegnalaCampagna() {
        assertTrue(segnalazioniService.segnalaCampagna(campagna, utente, "We"));
    }*/

}
