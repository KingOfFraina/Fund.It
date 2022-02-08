package model.services;

import model.beans.Utente;

import javax.servlet.http.HttpSession;

public interface AutenticazioneService {

    /**
     * Esegue il login dell'utente passato in input.
     *
     * @param utente Istanza di Utente che desidera fare il login
     * @return istanza di utente se l'operazione è andata a buon fine,
     * null altrimenti
     */
    Utente login(Utente utente);

    /**
     * Permette la registrazione dell'utente passato in input.
     *
     * @param utente Istanza di Utente che desidera fare la registrazione
     * @return true se l'operazione va a buon fine, false altrimenti
     */
    boolean registrazione(Utente utente);

    /**
     * Esegue il logout dell'utente presente in sessione.
     * @param session La sessione su cui effettuare il logout
     * @return l'esito dell'invocazione del metodo
     */
    boolean logout(HttpSession session);
}
