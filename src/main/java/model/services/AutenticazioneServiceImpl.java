package model.services;

import model.beans.Utente;


import javax.servlet.http.HttpSession;

public class AutenticazioneServiceImpl implements AutenticazioneService {

    private final HttpSession sessionWrapper;

    public AutenticazioneServiceImpl(HttpSession session) {
        this.sessionWrapper = session;
    }

    /**
     * @param utente Istanza di Utente che desidera fare il login
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean login(Utente utente) {
        return false;
    }

    /**
     * @param utente Istanza di Utente che desidera fare la registrazione
     * @return true se l'operazione va a buon fine, false altrimenti
     */
    @Override
    public boolean registrazione(Utente utente) {
        return false;
    }

    /**
     * @param utente Istanza di Utente che desidera fare il logout
     * @return true se l'operazione va a buon fine, false altrimenti
     */
    @Override
    public boolean logout(Utente utente) {
        sessionWrapper.removeAttribute("");
        return false;
    }
}
