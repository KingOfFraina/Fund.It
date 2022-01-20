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
   List<Utente> visualizzaUtenti(int richiedente);

   /**
    * @param richiedente idUtente di chi ha ordinato l'operazione.
    * @param soggetto    idUtente di cui va modificato lo stato.
    * @return esito dell'operazione.
    */
   boolean promuoviDeclassaUtente(int richiedente, int soggetto);

   /**
    * @param idCattivone da bannare.
    * @return esito operazione.
    */
   boolean sospensioneUtente(int idCattivone);

}
