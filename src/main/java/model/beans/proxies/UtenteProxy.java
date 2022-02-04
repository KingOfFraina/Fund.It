package model.beans.proxies;


import model.DAO.SegnalazioneDAO;
import model.DAO.DonazioneDAO;
import model.DAO.CampagnaDAO;
import model.DAO.UtenteDAO;
import model.DAO.DAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Segnalazione;
import model.beans.Utente;
import model.beans.proxyInterfaces.UtenteInterface;

import java.util.List;

public final class UtenteProxy implements UtenteInterface {
   /**
    * Istanza di utente.
    */
   private Utente utente;

   /**
    * @param u istanza di Utente
    */
   public UtenteProxy(final Utente u) {
      this.utente = u;
   }

   @Override
   public List<Segnalazione> getSegnalazioni() {
      if (utente.getSegnalazioni() == null) {
         DAO<Segnalazione> dao = new SegnalazioneDAO();
         DAO<Utente> dao2 = new UtenteDAO();
         List<Segnalazione> segnalazioni = ((SegnalazioneDAO) dao)
                 .getByIdUtente(utente.getIdUtente());
         segnalazioni.forEach(it -> {
            it.setSegnalatore(utente);
            it.setSegnalato(dao2.getById(it.getSegnalato().getIdUtente()));
         });

         utente.setSegnalazioni(segnalazioni);
         return segnalazioni;
      } else {
         return utente.getSegnalazioni();
      }
   }

   @Override
   public List<Donazione> getDonazioni() {
      if (utente.getDonazioni() == null) {
         DAO<Donazione> dao = new DonazioneDAO();
         List<Donazione> donazioni = ((DonazioneDAO) dao)
                 .getAllByUtente(utente.getIdUtente());
         donazioni.forEach((it) -> it.setUtente(utente));
         utente.setDonazioni(donazioni);

         return donazioni;
      } else {
         return utente.getDonazioni();
      }
   }

   @Override
   public List<Campagna> getCampagne() {

      if (utente.getCampagne() == null) {
         DAO<Campagna> dao = new CampagnaDAO();
         List<Campagna> campagne = ((CampagnaDAO) dao)
                 .getByIdUtente(utente.getIdUtente());
         campagne.forEach((it) -> it.setUtente(utente));
         utente.setCampagne(campagne);
         return campagne;
      } else {
         return utente.getCampagne();
      }
   }
}
