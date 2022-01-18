package model.beans;

import model.beans.proxyInterfaces.DonazioneInterface;

import java.util.Date;

public final class Donazione implements DonazioneInterface {
   /**
    * rappresenta l'ID della donazione.
    */
   private int idDonazione;
   /**
    * rappresenta la stringa che il PaymentProcessor rilascia
    * all'atto di un pagamento.
    */
   private String ricevuta;
   /**
    * rappresenta il commento scritto durante la fase di donazione.
    */
   private String commento;
   /**
    * rappresenta l'importo della donazione.
    */
   private double sommaDonata;
   /**
    * rappresenta il consenso dell'utente a rendere visibile il
    * proprio nome e cognome.
    */
   private boolean anonimo;
   /**
    * rappresenta l'utente che ha effettuato la donazione.
    */
   private Utente utente;
   /**
    * rappresenta la campagna su cui l'utente ha donato.
    */
   private Campagna campagna;
   /**
    * rappresenta la data e l'ora in cui viene effettuata la donazione.
    */
   private Date dataOra;

   /**
    * @return l'identificativo della donazione
    */
   public int getIdDonazione() {
      return idDonazione;
   }

   /**
    * @param newIdDonazione rappresenta l'ID della donazione
    */
   public void setIdDonazione(final int newIdDonazione) {
      this.idDonazione = newIdDonazione;
   }

   /**
    * @return la stringa per la tracciabilità del pagamento
    * per il PaymentProcessor
    */
   public String getRicevuta() {
      return ricevuta;
   }

   /**
    * @param newRicevuta prende la stringa rilasciata dal PaymentProcessor
    */
   public void setRicevuta(final String newRicevuta) {
      this.ricevuta = newRicevuta;
   }

   /**
    * @return la striga rappresentante il commento
    */
   public String getCommento() {
      return commento;
   }

   /**
    * @param newCommento prende il commento da inserire nella donazione
    */
   public void setCommento(final String newCommento) {
      this.commento = newCommento;
   }

   /**
    * @return la somma donata
    */
   public double getSommaDonata() {
      return sommaDonata;
   }

   /**
    * @param newSommaDonata prende la somma donata
    */
   public void setSommaDonata(final double newSommaDonata) {
      this.sommaDonata = newSommaDonata;
   }

   /**
    * @return lo stato del flag anonimo: true --> anonimo, false --> pubblico
    */
   public boolean isAnonimo() {
      return anonimo;
   }

   /**
    * @param newAnonimo prende la preferenza dell'utente circa l'anonimato
    */
   public void setAnonimo(final boolean newAnonimo) {
      this.anonimo = newAnonimo;
   }

   /**
    * @return l'utente che ha effettuato la donazione
    */
   public Utente getUtente() {
      return utente;
   }

   /**
    * @param newUtente prende l'utente che ha effettuato la donazione
    */
   public void setUtente(final Utente newUtente) {
      this.utente = newUtente;
   }

   /**
    * @return la campagna su cui è stata effettuata la donazione
    */
   public Campagna getCampagna() {
      return campagna;
   }

   /**
    * @param newCampagna prende la campagna su cui è stata
    * effettuata la donazione
    */
   public void setCampagna(final Campagna newCampagna) {
      this.campagna = newCampagna;
   }

   /**
    * @return la data e l'ora in cui è stata effettuata la donazione
    */
   public Date getDataOra() {
      return dataOra;
   }

   /**
    * @param newDataOra prende la data e l'ora in cui è stato
    * effettuata la donazione
    */
   public void setDataOra(final Date newDataOra) {
      this.dataOra = newDataOra;
   }

   @Override
   public String toString() {
      return "Donazione{"
              + "idDonazione=" + idDonazione
              + ", ricevuta='" + ricevuta + '\''
              + ", commento='" + commento + '\''
              + ", sommaDonata=" + sommaDonata
              + ", anonimo=" + anonimo
              + ", utente=" + utente
              + ", campagna=" + campagna.getIdCampagna()
              + ", dataOra=" + dataOra
              + '}';
   }
}
