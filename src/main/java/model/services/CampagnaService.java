package model.services;

import model.beans.Campagna;
import model.beans.proxyInterfaces.CampagnaInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CampagnaService {

   /**
    * Permette la ricerca di campagne.
    *
    * @param text una stringa per effettuare la ricerca
    * @return la lista di campagne che soddisfano il parametro passato
    */
   List<Campagna> ricercaCampagna(String text);

   /**
    * Permette la ricerca di campagne data una categoria.
    *
    * @param text una categoria
    * @return la lista di campagne che soddisfano il parametro passato
    */
   List<Campagna> ricercaCampagnaPerCategoria(String text);

   /**
    * Permette la creazione di una nuova campagna.
    *
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean creazioneCampagna(Campagna campagna);

   /**
    * Permette la modifica della campagna esistente.
    *
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean modificaCampagna(Campagna campagna);

   /**
    * Permette la condivisione di una campagna esistente.
    *
    * @param idCampagna l'id della campagna che si vuole condividere
    * @param request    request ricevuta
    * @return una Map che contiene come chiave il servizio,
    * il value è il link generato
    */
   Map<String, String> condividiCampagna(int idCampagna,
                                         HttpServletRequest request);

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
    * @param idCampagna id della campagna da cercare
    * @return istanza di Campagna avente come id idCampagna, null altrimenti
    */
   Campagna trovaCampagna(int idCampagna);

   /**
    * Permette la cancellazione della campagna esistente.
    *
    * @param campagna il bean che contiene informazioni sulla campagna
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean cancellaCampagna(Campagna campagna);

   /**
    * Esegue i rimborsi delle eventuali donazioni
    * effettuate sulla campagna.
    *
    * @param campagna istanza di Campagna
    * @param proxy    proxy di Campagna per trovare le donazioni della campagna
    * @return true se l'operazione è andata a buon fine, false altrimenti
    */
   boolean rimborsaDonazioni(Campagna campagna, CampagnaInterface proxy);
}
