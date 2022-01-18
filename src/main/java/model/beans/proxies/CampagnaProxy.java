package model.beans.proxies;


import model.DAO.DAO;
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
         DAO immagineDAO = new ImmagineDAO();
         List<Immagine> immagineList = immagineDAO.getAll();

         immagineList.forEach(i -> i.setCampagna(campagna));

         campagna.setImmagini(immagineList);
      }

      return campagna.getImmagini();
   }

   @Override
   public List<Donazione> getDonazioni() {
      if (campagna.getDonazioni() == null) {
         DAO donazioneDAO = new DonazioneDAO();
         List<Donazione> donazioneList = donazioneDAO.getAll();

         donazioneList.forEach(d -> d.setCampagna(campagna));

         campagna.setDonazioni(donazioneList);
      }

      return campagna.getDonazioni();
   }

   @Override
   public List<Segnalazione> getSegnalazioni() {
      if (campagna.getSegnalazioni() == null) {
         DAO segnalazioneDAO = new SegnalazioneDAO();
         List<Segnalazione> segnalazioneList = segnalazioneDAO.getAll();

         segnalazioneList.forEach(s -> s.setCampagnaSegnalata(campagna));

         campagna.setSegnalazioni(segnalazioneDAO.getAll());
      }

      return campagna.getSegnalazioni();
   }
}
