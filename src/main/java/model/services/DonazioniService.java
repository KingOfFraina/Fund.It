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
     * @param commento testo del commento.
     * @param anonimo true il commento viene pubblicato come anonimo,
     *                false altrimenti.
     * @param u utente che decide di commentare.
     * @return esito dell'inserimento del commento.
     */
    boolean commenta(String commento, boolean anonimo, Utente u);
}
