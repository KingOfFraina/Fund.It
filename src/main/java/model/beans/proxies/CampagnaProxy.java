package model.beans.proxies;


import model.DAO.DonazioneDAO;
import model.DAO.ImmagineDAO;
import model.DAO.SegnalazioneDAO;
import model.beans.Campagna;
import model.beans.proxyInterfaces.CampagnaInterface;
import model.beans.Immagine;
import model.beans.Segnalazione;
import model.beans.Donazione;
import java.util.List;

public final class CampagnaProxy implements CampagnaInterface {

   private Campagna campagna;

   public CampagnaProxy(final Campagna newCampagna) {
      this.campagna = newCampagna;
   }

   @Override
   public List<Immagine> getImmagini() {

      if (campagna.getImmagini() == null) {
         ImmagineDAO immagineDAO = new ImmagineDAO();
         campagna.setImmagini(immagineDAO.getAll());
      }

      return campagna.getImmagini();
   }

   @Override
   public List<Donazione> getDonazioni() {
      if (campagna.getDonazioni() == null) {
         DonazioneDAO donazioneDAO = new DonazioneDAO();
         campagna.setDonazioni(donazioneDAO.getAll());
      }

      return campagna.getDonazioni();
   }

   @Override
   public List<Segnalazione> getSegnalazioni() {
      if (campagna.getSegnalazioni() == null) {
         SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
         campagna.setSegnalazioni(segnalazioneDAO.getAll());
      }

      return campagna.getSegnalazioni();
   }
}
