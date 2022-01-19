package model.services;

import model.beans.Utente;

import java.util.List;

public interface UtenteService {
    /**
     *
     * @param id
     * @return TODO
     */
    Utente visualizzaDashboardUtente(int id);

    /**
     *
     * @param modify idUtente da modificare.
     * @return esito operazione.
     */
    boolean modificaProfilo(int modify);

    /**
     *
     * @param richiedente idUtente della lista completa utenti.
     * @return null se il richiedente non è admin. List di Utente se è admin.
     */
    List<Utente> visualizzaUtenti(int richiedente);

    /**
     *
     * @param soggetto idUtente di cui va modificato lo stato.
     * @return esito dell'operazione.
     */
    boolean promuoviDeclassaUtente(int soggetto);

    /**
     *
     * @param idCattivone da bannare.
     * @return esito operazione.
     */
    boolean sospensioneUtente(int idCattivone);

}
