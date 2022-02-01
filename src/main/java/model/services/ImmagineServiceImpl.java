package model.services;

import model.DAO.ImmagineDAO;
import model.beans.Immagine;

public final class ImmagineServiceImpl implements ImmagineService {

   @Override
   public boolean salvaImmagine(final Immagine immagine) {
      return new ImmagineDAO().save(immagine);
   }

   @Override
   public boolean eliminaImmagini(final int idCampagna) {
      return new ImmagineDAO().deleteByIdCampagna(idCampagna);
   }
}
