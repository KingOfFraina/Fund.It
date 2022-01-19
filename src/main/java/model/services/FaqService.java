package model.services;

import model.beans.FAQ;

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
}
