package model.services;

import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;

import java.util.List;

public interface SegnalazioniService {
   /**
    * @return lista delle segnalazioni effettuate dall'utente
    */
   List<Segnalazione> trovaSegnalazioni();

   /**
    * @param idSegnalazione id della segnalazione interessata
    * @return istanza di segnalazione
    */
   Segnalazione trovaSegnalazione(int idSegnalazione);

   /**
    * @param idSegnalazione intero che rappresenta l'id della segnalazione
    * @param stato          Il nuovo stato della segnalazione
    * @return true se l'operazione è andata a buon fine, false altrimenti
    */
   boolean risolviSegnalazione(int idSegnalazione, StatoSegnalazione stato);

   /**
    * @param segnalazione istanza di Segnalazione che esprime una
    *                     segnalazione fatta da un utente verso una campagna
    * @return true se l'operazione è andata a buon fine, false altrimenti
    */
   boolean segnalaCampagna(Segnalazione segnalazione);
}
