package model.services;

import model.beans.Utente;

public interface AutenticazioneService {

   /**
    * @param utente Istanza di Utente che desidera fare il login
    * @return istanza di utente se l'operazione è andata a buon fine,
    * null altrimenti
    */
   Utente login(Utente utente);

   /**
    * @param utente Istanza di Utente che desidera fare la registrazione
    * @return true se l'operazione va a buon fine, false altrimenti
    */
   boolean registrazione(Utente utente);

   /**
    * @param utente Istanza di Utente che desidera fare il logout
    *
    */
   void logout(Utente utente);
}
