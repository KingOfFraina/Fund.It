package model.services;

import model.beans.Utente;

import java.util.List;

public interface UtenteService {
    /**
     * @param id idUtente.
     * @return l'istanza di utente presente nel database
     */
    Utente visualizzaDashboardUtente(int id);

    /**
     * @param utente da modificare.
     * @return esito operazione.
     */
    boolean modificaProfilo(Utente utente);

    /**
     * @param richiedente della lista completa utenti.
     * @return null se il richiedente non è admin. List di Utente se è admin.
     */
    List<Utente> visualizzaUtenti(Utente richiedente);

    /**
     * @param richiedente Istanza di Utente di chi ha ordinato l'operazione.
     * @param soggetto    Istanza di Utente di cui va modificato lo stato.
     * @return esito dell'operazione.
     */
    boolean promuoviDeclassaUtente(Utente richiedente, Utente soggetto);

    /**
     * @param cattivone da bannare.
     * @return esito operazione.
     */
    boolean sospensioneUtente(Utente cattivone);

}
