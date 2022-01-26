package model.beans.proxyInterfaces;

import model.beans.Donazione;
import model.beans.Immagine;
import model.beans.Segnalazione;
import model.beans.Utente;

import java.util.List;

public interface CampagnaInterface {
    /**
     * @return lista delle segnalazioni di una campagna.
     */
    List<Segnalazione> getSegnalazioni();

    /**
     * @return lista immagini di una campagna.
     */
    List<Immagine> getImmagini();

    /**
     * @return lista donazioni relativa ad una campagna.
     */
    List<Donazione> getDonazioni();

    /**
     * @return istanza dell'utente creatore della campagna.
     */
    Utente getUtente();
}
