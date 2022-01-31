package model.services;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;


import javax.servlet.http.HttpSession;

public class AutenticazioneServiceImpl implements AutenticazioneService {

    /**
     * Wrapper di HttpSession.
     */
    private final HttpSession sessionWrapper;
    /**
     * Wrapper di UtenteDAO.
     */
    private final DAO<Utente> dao;

    /**
     * @param session Sessione attuale dell'utente
     * @param utenteDAO istanza di UtenteDAO
     */
    public AutenticazioneServiceImpl(final HttpSession session,
                                     final DAO<Utente> utenteDAO) {
        this.sessionWrapper = session;
        this.dao = utenteDAO;
    }

    /**
     * @param utente Istanza di Utente che desidera fare il login
     * @return un'instanza della classe utente
     */
    @Override
    public Utente login(final Utente utente) {
        if (utente == null) {
            throw new RuntimeException("Utente null");
        }
        UtenteDAO userDao = (UtenteDAO) dao;
        return userDao.doLogin(utente);
    }

    /**
     * @param utente Istanza di Utente che desidera fare la registrazione
     * @return true se l'operazione va a buon fine, false altrimenti
     */
    @Override
    public boolean registrazione(final Utente utente) {
        if (utente == null) {
            throw new RuntimeException("Utente null");
        }
        return dao.save(utente);
    }

    /**
     * @param utente Istanza di Utente che desidera fare il logout
     */
    @Override
    public void logout(final Utente utente) {
        sessionWrapper.invalidate();
    }
}
