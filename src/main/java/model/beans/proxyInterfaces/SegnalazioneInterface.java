package model.beans.proxyInterfaces;

import model.beans.Campagna;
import model.beans.Utente;

public interface SegnalazioneInterface {

    /**
     * @return istanza dell'utente segnalatore
     */
    Utente getSegnalatore();

    /**
     * @return istanza dell'utente segnalato
     */
    Utente getSegnalato();

    /**
     * @return istanza della campagna segnalata
     */
    Campagna getCampagna();
}
