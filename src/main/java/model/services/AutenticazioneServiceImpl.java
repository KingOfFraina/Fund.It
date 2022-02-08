package model.services;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;


import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AutenticazioneServiceImpl implements AutenticazioneService {
    /**
     * Wrapper di UtenteDAO.
     */
    private final DAO<Utente> dao;

    /**
     * Costruttore di AutenticazioneService.
     * @param utenteDAO istanza di UtenteDAO
     */
    public AutenticazioneServiceImpl(final DAO<Utente> utenteDAO) {
        this.dao = utenteDAO;
    }

    /**
     * Costruttore di AutenticazioneService.
     */
    public AutenticazioneServiceImpl() {
        this.dao = new UtenteDAO();
    }

    /**
     * @param utente Istanza di Utente che desidera fare il login
     * @return un'instanza della classe utente
     */
    @Override
    public Utente login(final Utente utente) {
        if (utente == null) {
            throw new IllegalArgumentException("Argument must be not null");
        } else {
            UtenteDAO userDao = (UtenteDAO) dao;
            Utente ut = userDao.doLogin(utente);
            if (ut == null) {
                return null;
            } else {
                if (ut.getDataBan() != null) {
                    if (ut.getDataBan().isBefore(LocalDateTime.now()
                            .truncatedTo(ChronoUnit.MINUTES))) {
                        ut.setDataBan(null);
                        userDao.update(ut);
                    } else {
                        ut.setIdUtente(-1);
                    }
                    return ut;
                } else {
                    return ut;
                }
            }
        }
    }

    /**
     * @param utente Istanza di Utente che desidera fare la registrazione
     * @return true se l'operazione va a buon fine, false altrimenti
     */
    @Override
    public boolean registrazione(final Utente utente) {
        if (utente == null) {
            throw new IllegalArgumentException("Argument must be not null");
        } else {
            return dao.save(utente);
        }
    }

    /**
     * Esegue il logout dell'utente presente in sessione.
     * @param session la sessione da invalidare
     */
    @Override
    public boolean logout(HttpSession session) {
        if(session == null) {
            return false;
        } else {
            session.invalidate();
            return true;
        }
    }
}
