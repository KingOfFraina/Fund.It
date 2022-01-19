package model.services;

import model.beans.Donazione;
import model.beans.Utente;

import java.util.List;

public interface DonazioniService {
    /**
     * @param d donazione da effettuare.
     * @return esito della donazione.
     */
    boolean effettuaDonazione(Donazione d);

    /**
     * @param u utente che ha effettuato le donazioni.
     * @return lista delle donazioni.
     */
    List<Donazione> visualizzaDonazioni(Utente u);

    /**
     * @param d donazione aggiornata con il commento
     * @return esito dell'inserimento del commento.
     */
    boolean commenta(Donazione d);
}
