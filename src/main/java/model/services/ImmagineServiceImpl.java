package model.services;

import model.DAO.ImmagineDAO;
import model.beans.Immagine;

public final class ImmagineServiceImpl implements ImmagineService {

   @Override
   public boolean salvaImmagine(Immagine immagine) {
      return new ImmagineDAO().save(immagine);
   }
}
