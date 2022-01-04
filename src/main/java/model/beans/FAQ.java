package model.beans;

public final class FAQ {
   /**
    * Rappresenta l'ID della faq.
    */
   private int idFaq;
   /**
    * Rappresenta la domanda della faq.
    */
   private String domanda;
   /**
    * Rappresenta la risposta della faq.
    */
   private String risposta;
   /**
    * Rappresenta l'utente che crea la faq.
    */
   private Utente utenteCreatore;

   /**
    * @return l'ID della faq.
    */
   public int getIdFaq() {
      return idFaq;
   }

   /**
    * @param id rappresenta l'ID della faq
    */
   public void setIdFaq(final int id) {
      this.idFaq = id;
   }

   /**
    * @return la domanda relativa alla faq
    */
   public String getDomanda() {
      return domanda;
   }

   /**
    * @param newDomanda rappresenta la domanda della faq
    */
   public void setDomanda(final String newDomanda) {
      this.domanda = newDomanda;
   }

   /**
    * @return la risposta relativa alla faq
    */
   public String getRisposta() {
      return risposta;
   }

   /**
    * @param newRisposta rappresenta la risposta della faq
    */
   public void setRisposta(final String newRisposta) {
      this.risposta = newRisposta;
   }

   /**
    * @return l'utente che ha creato la FAQ
    */
   public Utente getUtenteCreatore() {
      return utenteCreatore;
   }

   /**
    * @param newUtenteCreatore rappresenta l'utente che ha creato la FAQ
    */
   public void setUtenteCreatore(final Utente newUtenteCreatore) {
      this.utenteCreatore = newUtenteCreatore;
   }

   @Override
   public String toString() {
      return "FAQ{"
              + "IDFaq=" + idFaq
              + ", domanda='" + domanda + '\''
              + ", Risposta='" + risposta + '\''
              + ", utente=" + utente
              + '}';
   }
}
