package model.services;

import model.DAO.CampagnaDAO;
import model.beans.Campagna;

import java.util.List;

public final class CampagnaServiceImpl implements CampagnaService {

   /**
    * Contiene il riferimento al dao di campagna.
    */
   private static CampagnaDAO dao = new CampagnaDAO();

   @Override
   public boolean creazioneCampagna(final Campagna campagna) {
      return dao.save(campagna);
   }

   @Override
   public boolean modificaCampagna(final Campagna campagna) {
      return dao.update(campagna);
   }

   @Override
   public String condividiCampagna(final int idCampagna) {
      Campagna campagna = dao.getById(idCampagna);

      if (campagna != null) {
         return campagna.getTitolo();
      }

      return null;
   }

   @Override
   public List<Campagna> ricercaCampagna(final String text) {
      return dao.getByKeyword(text);
   }

   @Override
   public List<Campagna> visualizzaCampagne(final int size, final int offset) {
      return dao.getBySizeOffset(size, offset);
   }

   @Override
   public boolean chiudiCampagna(final Campagna campagna) {
      return modificaCampagna(campagna);
   }

   @Override
   public boolean cancellaCampagna(final Campagna campagna) {
      return modificaCampagna(campagna);
   }
}
