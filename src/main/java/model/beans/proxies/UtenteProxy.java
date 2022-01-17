package model.beans.proxies;


import model.DAO.CampagnaDAO;
import model.DAO.DonazioneDAO;
import model.DAO.SegnalazioneDAO;
import model.beans.*;
import model.beans.proxyInterfaces.UtenteInterface;

import java.util.List;

public class UtenteProxy implements UtenteInterface {
   private Utente utente;

   public UtenteProxy(Utente u) {
      this.utente = u;
   }

   @Override
   public List<Segnalazione> getSegnalazioni() {
      List<Segnalazione> segnalazioni = utente.getSegnalazioni();

      if (segnalazioni == null) {
         SegnalazioneDAO dao = new SegnalazioneDAO();
         utente.setSegnalazioni(dao.getAll());
      }

      return utente.getSegnalazioni();
   }

   @Override
   public List<Donazione> getDonazioni() {
      List<Donazione> donazioni = utente.getDonazioni();

      if (donazioni == null) {
         DonazioneDAO dao = new DonazioneDAO();
         utente.setDonazioni(dao.getAll());
      }

      return utente.getDonazioni();
   }

   @Override
   public List<Campagna> getCampagne() {
      List<Campagna> campagne = utente.getCampagne();

      if (campagne == null) {
         CampagnaDAO dao = new CampagnaDAO();
         utente.setCampagne(dao.getAll());
      }

      return utente.getCampagne();
   }
}
