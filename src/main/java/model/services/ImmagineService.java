package model.services;

import model.beans.Immagine;

public interface ImmagineService {

   /**
    * Permette il salvataggio in database di un bean immagine.
    * @param immagine il bean da salvare
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean salvaImmagine(Immagine immagine);

   /**
    * Permette la cancellazione di tutte le foto collegate di una campagna.
    * @param idCampagna l'idCampagna della campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean eliminaImmagini(int idCampagna);
}
