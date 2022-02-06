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

import java.time.LocalDateTime;

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
        campagna.setUtente(utente);
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
        Segnalazione segnalazione1 = new Segnalazione();
        segnalazione1.setSegnalatore(null);
        segnalazione1.setCampagnaSegnalata(campagna);
        segnalazione1.setDescrizione("");

        Segnalazione segnalazione2 = new Segnalazione();
        segnalazione2.setSegnalatore(utente);
        segnalazione2.setCampagnaSegnalata(null);
        segnalazione2.setDescrizione("");

        Segnalazione segnalazione3 = new Segnalazione();
        segnalazione3.setSegnalatore(utente);
        segnalazione3.setCampagnaSegnalata(campagna);
        segnalazione3.setDescrizione(null);

        Segnalazione segnalazione4 = new Segnalazione();
        segnalazione4.setDescrizione("");
        segnalazione4.setSegnalatore(utente);
        segnalazione4.setCampagnaSegnalata(campagna);
        segnalazione4.setSegnalato(null);
        assertAll(() -> assertThrows(IllegalArgumentException.class,
                        () -> segnalazioniService
                                .segnalaCampagna(null)),
                () ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(segnalazione1)),
                () ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(segnalazione2)),
                () ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(segnalazione3)),
                () ->
                        assertThrows(IllegalArgumentException.class,
                                () -> segnalazioniService
                                        .segnalaCampagna(segnalazione4)));
    }

    @Test
    public void testSegnalaCampagna() {
        Mockito.when(mockDao.save(segnalazione)).thenReturn(true);
        segnalazione.setCampagnaSegnalata(campagna);
        segnalazione.setSegnalatore(utente);
        segnalazione.setSegnalato(campagna.getUtente());
        segnalazione.setDataOra(LocalDateTime.now());
        segnalazione.setDescrizione("descrizione");
        assertTrue(segnalazioniService.segnalaCampagna(segnalazione));
    }

}
