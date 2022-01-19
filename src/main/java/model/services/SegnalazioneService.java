package model.services;

import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.Utente;

import java.util.List;

public interface SegnalazioneService {
    /**
     * @param richiedente id del richiedente della lista segnalazioni.
     * @return una collezione di tutte le segnalazioni effettuate.
     */
    List<Segnalazione> trovaSegnalazioni(int richiedente);


    /**
     * @param richiedente idUtente del richiedente del servizio.
     * @param s Istanza di Segnalazione da risolvere.
     * @return true se la segnalazione è stata
     * risolta senza errori, false altrimenti.
     */
    boolean risolviSegnalazione(int richiedente, Segnalazione s);

    /**
     * @param campagna    Istanza di Campagna da segnalare.
     * @param segnalatore Id di Utente che segnala la campagna.
     * @return true se la segnalazione è stata inserita, false altrimenti.
     */
    boolean segnalaCampagna(Campagna campagna, int segnalatore);
}
