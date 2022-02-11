package model.beans.proxies;

import model.DAO.CampagnaDAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.Utente;
import model.beans.proxyInterfaces.SegnalazioneInterface;


public final class SegnalazioneProxy implements SegnalazioneInterface {
   /**
    * Variabile che mantiene un oggetto di tipo Segnalazione.
    */
   private Segnalazione segnalazione;

   /**
    * @param s Istanza di segnalazione da wrappare
    */
   public SegnalazioneProxy(final Segnalazione s) {
      this.segnalazione = s;
   }

   @Override
   public Utente getSegnalatore() {
      if (segnalazione.getSegnalatore().getCf() == null) {
         UtenteDAO dao = new UtenteDAO();
         Utente u = dao.getById(segnalazione.getSegnalatore().getIdUtente());
         segnalazione.setSegnalatore(u);
         return u;
      } else {
         return segnalazione.getSegnalatore();
      }
   }

   @Override
   public Utente getSegnalato() {
      if (segnalazione.getSegnalato().getCf() == null) {
         UtenteDAO dao = new UtenteDAO();
         Utente u = dao.getById(segnalazione.getSegnalato().getIdUtente());
         segnalazione.setSegnalato(u);
         return u;
      } else {
         return segnalazione.getSegnalato();
      }
   }

   @Override
   public Campagna getCampagna() {
      if (segnalazione.getCampagna().getTitolo() == null) {
         CampagnaDAO dao = new CampagnaDAO();
         Campagna c = dao.getById(segnalazione.
                 getCampagna().getIdCampagna());
         segnalazione.setCampagnaSegnalata(c);
         return c;
      } else {
         return segnalazione.getCampagna();
      }
   }
}
