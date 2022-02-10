package model.services;

import model.DAO.DAO;
import model.DAO.ImmagineDAO;
import model.beans.Immagine;

public final class ImmagineServiceImpl implements ImmagineService {
   /**
    * Il DAO usato per eseguire le operazioni.
    */
   private DAO<Immagine> dao;

   /**
    * Il costruttore per la classe ImmagineService.
    *
    * @param newDAO il DAO da utilizzare per eseguire le operazioni richieste.
    */
   public ImmagineServiceImpl(final DAO<Immagine> newDAO) {
      this.dao = newDAO;
   }

   /**
    * Il costruttore per la classe ImmagineService.
    */
   public ImmagineServiceImpl() {
      dao = new ImmagineDAO();
   }

   @Override
   public boolean salvaImmagine(final Immagine immagine) {
      return dao.save(immagine);
   }

   @Override
   public boolean eliminaImmaginiCampagna(final int idCampagna) {
      return ((ImmagineDAO) dao).deleteByIdCampagna(idCampagna);
   }
}
