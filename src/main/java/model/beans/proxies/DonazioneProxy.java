package model.beans.proxies;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.beans.proxyInterfaces.DonazioneInterface;

public class DonazioneProxy implements DonazioneInterface {

   private Donazione donazione;

   public DonazioneProxy(Donazione donazione) {
      this.donazione = donazione;
   }

   @Override
   public Campagna getCampagna() {
      if(donazione.getCampagna().getCategoria() == null) {
         DAO campagnaDAO = new CampagnaDAO();
         donazione.setCampagna((Campagna) campagnaDAO.getById(donazione.getCampagna().getIdCampagna()));
      }

      return donazione.getCampagna();
   }

   @Override
   public Utente getUtente() {
      if(donazione.getUtente().getCf().isEmpty()) {
         DAO uetnteDAO = new UtenteDAO();
         donazione.setUtente((Utente) uetnteDAO.getById(donazione.getUtente().getIdUtente()));
      }

      return donazione.getUtente();
   }
}
