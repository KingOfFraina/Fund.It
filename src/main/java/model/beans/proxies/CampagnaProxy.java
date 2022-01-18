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

   /**
    * riferimento alla campagna.
    */
   private Campagna campagna;

   /**
    * costruttore.
    *
    * @param newCampagna campagna
    */
   public CampagnaProxy(final Campagna newCampagna) {
      this.campagna = newCampagna;
   }

   @Override
   public List<Immagine> getImmagini() {

      if (campagna.getImmagini() == null) {
         ImmagineDAO immagineDAO = new ImmagineDAO();
         List<Immagine> immagineList =
                 immagineDAO.getByIdCampagna(campagna.getIdCampagna());

         immagineList.forEach(i -> i.setCampagna(campagna));

         campagna.setImmagini(immagineList);
      }

      return campagna.getImmagini();
   }

   @Override
   public List<Donazione> getDonazioni() {
      if (campagna.getDonazioni() == null) {
         DonazioneDAO donazioneDAO = new DonazioneDAO();
         List<Donazione> donazioneList =
                 donazioneDAO.getByIdCampagna(campagna.getIdCampagna());

         donazioneList.forEach(d -> d.setCampagna(campagna));

         campagna.setDonazioni(donazioneList);
      }

      return campagna.getDonazioni();
   }

   @Override
   public List<Segnalazione> getSegnalazioni() {
      if (campagna.getSegnalazioni() == null) {
         SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
         List<Segnalazione> segnalazioneList =
                 segnalazioneDAO.getByIdCampagna(campagna.getIdCampagna());

         segnalazioneList.forEach(s -> s.setCampagnaSegnalata(campagna));

         campagna.setSegnalazioni(segnalazioneDAO.getAll());
      }

      return campagna.getSegnalazioni();
   }
}
