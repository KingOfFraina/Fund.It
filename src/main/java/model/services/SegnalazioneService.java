package model.services;

import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.Utente;

import java.util.List;

public interface SegnalazioneService {
    /**
     * @return una collezione di tutte le segnalazioni effettuate
     */
    List<Segnalazione> trovaSegnalazioni();


    /**
     * @param s Istanza di Segnalazione da risolvere
     * @return true se la segnalazione è stata
     * risolta senza errori, false altrimenti
     */
    boolean risolviSegnalazione(Segnalazione s);

    /**
     * @param campagna    Istanza di Campagna da segnalare
     * @param segnalatore Istanza di Utente che segnala la campagna
     * @return true se la segnalazione è stata inserita, false altrimenti
     */
    boolean segnalaCampagna(Campagna campagna, Utente segnalatore);
}
