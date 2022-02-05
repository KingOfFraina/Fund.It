package Services;

import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.beans.Donazione;
import model.beans.Utente;
import model.services.DonazioniService;
import model.services.DonazioniServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class DonazioniServiceTest {

    static DAO<Donazione> mockDao;
    static DonazioniService donazioniService;
    static Donazione donazione;

    @BeforeClass
    public static void setUp() {
        mockDao = Mockito.mock(DonazioneDAO.class);
        donazioniService = new DonazioniServiceImpl(mockDao);
        donazione = new Donazione();
    }

    @Test
    public void testEffettuaDonazioneNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> donazioniService.effettuaDonazione(null));
    }

    @Test
    public void testEffettuaDonazione() {
        Mockito.when(mockDao.save(donazione)).thenReturn(true);
        donazioniService.effettuaDonazione(donazione);
    }

    @Test
    public void testVisualizzaDonazioniUtente() {
        DonazioneDAO mockDonazioneDao = (DonazioneDAO) mockDao;
        Mockito.when(mockDonazioneDao.getAllByUtente(anyInt())).thenReturn(List.of(new Donazione()));
        assertTrue(donazioniService.visualizzaDonazioni(new Utente()).size() > 0);
    }

    @Test
    public void testVisualizzaDonazioniUtenteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> donazioniService.visualizzaDonazioni(null));
    }

    @Test
    public void testCommentaNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> donazioniService.commenta(null));
    }

    @Test
    public void testCommenta() {
        Mockito.when(mockDao.update(donazione)).thenReturn(true);
        assertTrue(donazioniService.commenta(donazione));
    }
}
