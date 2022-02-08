package Services;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import model.services.AutenticazioneService;
import model.services.AutenticazioneServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AutenticazioneServiceTest {
    static AutenticazioneService autenticazioneService;
    static HttpSession mockSession;
    static DAO<Utente> mockDao;
    static Utente utente;

    @BeforeClass
    public static void setUp() {
        mockDao = Mockito.mock(UtenteDAO.class);
        autenticazioneService = new AutenticazioneServiceImpl(mockDao);
        mockSession = Mockito.mock(HttpSession.class);
        utente = new Utente();
    }

    @Test
    public void testLoginNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> autenticazioneService.login(null));
    }

    @Test
    public void testLogin() {
        UtenteDAO mockUtenteDAO = (UtenteDAO) mockDao;
        Mockito.when(mockUtenteDAO.doLogin(utente)).thenReturn(utente);
        assertNotNull(autenticazioneService.login(utente));
        assertEquals(utente.getIdUtente(), autenticazioneService.login(utente).getIdUtente());
    }

    @Test
    public void testLoginNotFound() {
        Utente utente = new Utente();
        utente.setIdUtente(-1);
        UtenteDAO mockUtenteDAO = (UtenteDAO) mockDao;
        Mockito.when(mockUtenteDAO.doLogin(utente)).thenReturn(null);

        assertNull(autenticazioneService.login(utente));
    }

    @Test
    public void testLoginDataBan() {
        Utente temp = new Utente();
        Utente expected = new Utente();
        expected.setIdUtente(-1);
        temp.setDataBan(LocalDateTime.now().plusDays(5));
        UtenteDAO mockUtenteDAO = (UtenteDAO) mockDao;
        Mockito.when(mockUtenteDAO.doLogin(temp)).thenReturn(temp);
        Mockito.when(mockUtenteDAO.update(temp)).thenReturn(true);
        assertEquals(expected.getIdUtente(), autenticazioneService.login(temp).getIdUtente());
    }

    @Test
    public void testRegistrazioneNullEntity() {
        assertThrows(IllegalArgumentException.class,
                () -> autenticazioneService.registrazione(null));
    }

    @Test
    public void testRegistrazione() {
        Mockito.when(mockDao.save(utente)).thenReturn(true);
        assertTrue(autenticazioneService.registrazione(utente));
    }

    @Test
    public void logoutNull() {
        assertFalse(autenticazioneService.logout(null));
    }

    @Test
    public void logout() {
        assertTrue(autenticazioneService.logout(mockSession));
    }
}
