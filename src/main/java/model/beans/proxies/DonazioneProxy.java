package model.beans.proxies;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.beans.proxyInterfaces.DonazioneInterface;

public final class DonazioneProxy implements DonazioneInterface {
   /**
    * donazione.
    */
   private Donazione donazione;

   /**
    * costruttore.
    * @param newDonazione donazione.
    */
   public DonazioneProxy(final Donazione newDonazione) {
      this.donazione = newDonazione;
   }

   @Override
   public Campagna getCampagna() {
      if (donazione.getCampagna().getCategoria() == null) {
         DAO campagnaDAO = new CampagnaDAO();
         donazione.setCampagna((Campagna)
                 campagnaDAO.getById(donazione.getCampagna().getIdCampagna()));
      }

      return donazione.getCampagna();
   }

   @Override
   public Utente getUtente() {
      if (donazione.getUtente().getCf().isEmpty()) {
         DAO uetnteDAO = new UtenteDAO();
         donazione.setUtente((Utente)
                 uetnteDAO.getById(donazione.getUtente().getIdUtente()));
      }

      return donazione.getUtente();
   }
}
