package model.services;

import model.beans.FAQ;

import java.util.List;

public interface FaqService {
   /**
    * Inserisce una nuova FAQ.
    *
    * @param faq bean che contiene informazioni sulle faq
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean inserisciFaq(FAQ faq);

   /**
    * Modifica la FAQ esistente.
    *
    * @param faq bean che contiene informazioni sulle faq
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean cancellaFaq(FAQ faq);

   /**
    * Elimina una FAQ esistente.
    *
    * @param faq bean che contiene informazioni sulle faq
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean modificaFaq(FAQ faq);

   /**
    * La funzione permette il recupero di tutte le FAQ.
    *
    * @return la lista delle FAQ presenti nel database
    */
   List<FAQ> visualizzaFaq();

   /**
    * La funzione permette il recupero di una singola pagina di FAQ.
    *
    * @param idFaq l'id della FAQ
    * @return la pagina di FAQ presa dallo storage
    */
   FAQ visualizzaFaq(int idFaq);
}
