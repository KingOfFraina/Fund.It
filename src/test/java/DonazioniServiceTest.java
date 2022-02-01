import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.beans.Donazione;
import model.beans.Utente;
import model.services.DonazioniService;
import model.services.DonazioniServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DonazioniServiceTest {
    DonazioniService service;
    DAO<Donazione> dao;

    @Before
    public void setUp() {
        dao = Mockito.mock(DonazioneDAO.class);
        service = new DonazioniServiceImpl(dao);
    }

    @Test
    public void testInsertDonazioneNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.effettuaDonazione(null));
    }

    @Test
    public void testInsertValidDonazione() {
        Donazione donazione = new Donazione();
        when(dao.save(donazione)).thenReturn(true);
        assertTrue(service.effettuaDonazione(donazione));
    }
}
