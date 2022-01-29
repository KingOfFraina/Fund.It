package model.beans.proxyInterfaces;

import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;

public interface DonazioneInterface {
    /**
     * @return campagna relativa alla donazione.
     */
    Campagna getCampagna();

    /**
     * @return utente donatore.
     */
    Utente getUtente();
}
