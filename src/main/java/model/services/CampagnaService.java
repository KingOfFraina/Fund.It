package model.services;

import model.beans.Campagna;

import java.util.List;

public interface CampagnaService {

   /**
    * Permette la ricerca di campagne.
    *
    * @param text una stringa per effettuare la ricerca
    * @return la lista di campagne che soddisfano il parametro passato
    */
   List<Campagna> ricercaCampagna(String text);

   /**
    * Permette la creazione di una nuova campagna.
    *
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean creazioneCampagna(Campagna campagna);

   /**
    * Permette la modifica della campagna esistente.
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean modificaCampagna(Campagna campagna);

   /**
    * Permette la condivisione di una campagna esistente.
    *
    * @param idCampagna l'id della campagna che si vuole condividere
    * @return la stringa per la condivisione
    */
   String condividiCampagna(int idCampagna);

   /**
    * Permette la chiusura della campagna esistente.
    *
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean chiudiCampagna(Campagna campagna);

   /**
    * Visualizza tutte le campagne presenti in piattaforma.
    *
    * @param size   il numero di campagne da recuperare
    * @param offset il punto di partenza per il recupero
    * @return la lista delle campagne
    */
   List<Campagna> visualizzaCampagne(int size, int offset);

   /**
    * Permette la cancellazione della campagna esistente.
    *
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean cancellaCampagna(Campagna campagna);
}
