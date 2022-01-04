package model.beans;

public class FAQ
{
   private int IDFaq;
   private String Domanda, Risposta;
   private Utente utente;

   public FAQ(int IDFaq, String domanda, String risposta, Utente utente)
   {
      this.IDFaq = IDFaq;
      Domanda = domanda;
      Risposta = risposta;
      this.utente = utente;
   }

   public FAQ()
   {
   }

   public int getIDFaq()
   {
      return IDFaq;
   }

   public void setIDFaq(int IDFaq)
   {
      this.IDFaq = IDFaq;
   }

   public String getDomanda()
   {
      return Domanda;
   }

   public void setDomanda(String domanda)
   {
      Domanda = domanda;
   }

   public String getRisposta()
   {
      return Risposta;
   }

   public void setRisposta(String risposta)
   {
      Risposta = risposta;
   }

   public Utente getUtente()
   {
      return utente;
   }

   public void setUtente(Utente utente)
   {
      this.utente = utente;
   }

   @Override
   public String toString()
   {
      return "FAQ{" + "IDFaq=" + IDFaq + ", Domanda='" + Domanda + '\'' + ", Risposta='" + Risposta + '\'' + ", utente=" + utente + '}';
   }
}
