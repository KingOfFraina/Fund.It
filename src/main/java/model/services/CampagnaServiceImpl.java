package model.services;

import model.DAO.CampagnaDAO;
import model.beans.Campagna;

import java.util.List;

public final class CampagnaServiceImpl implements CampagnaService {
   @Override
   public boolean creazioneCampagna(final Campagna campagna) {
      return new CampagnaDAO().save(campagna);
   }

   @Override
   public boolean modificaCampagna(final Campagna campagna) {
      return new CampagnaDAO().update(campagna);
   }

   @Override
   public String condividiCampagna(final int idCampagna) {
      Campagna campagna = new CampagnaDAO().getById(idCampagna);

      if (campagna != null) {
         return campagna.getTitolo();
      }

      return null;
   }

   @Override
   public List<Campagna> ricercaCampagna(final String text) {
      return new CampagnaDAO().getByKeyword(text);
   }

   @Override
   public List<Campagna> visualizzaCampagne(final int size, final int offset) {
      return new CampagnaDAO().getBySizeOffset(size, offset);
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
