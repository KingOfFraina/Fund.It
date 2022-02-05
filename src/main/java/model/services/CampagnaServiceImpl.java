package model.services;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.StatoCampagna;
import model.beans.proxyInterfaces.CampagnaInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CampagnaServiceImpl implements CampagnaService {
   /**
    * Wrapper di campagna DAO.
    */
   private final DAO<Campagna> dao;

   /**
    * @param campagnaDAO Istanza di campagna DAO
    */
   public CampagnaServiceImpl(final DAO<Campagna> campagnaDAO) {
      this.dao = campagnaDAO;
   }

   @Override
   public boolean creazioneCampagna(final Campagna campagna) {
      if (campagna == null) {
         throw new IllegalArgumentException("Null Object");
      } else {
         return dao.save(campagna);
      }
   }

   @Override
   public boolean modificaCampagna(final Campagna campagna) {
      if (campagna == null) {
         throw new IllegalArgumentException("Null Object");
      } else {
         return dao.update(campagna);
      }
   }

   @Override
   public Map<String, String> condividiCampagna(final int idCampagna,
                                                final HttpServletRequest req) {
      Campagna campagna = dao.getById(idCampagna);

      if (campagna == null) {
         throw new IllegalArgumentException("Null Object");
      } else {
         if (req == null) {
            throw new IllegalArgumentException("Null Object");
         } else {
            HashMap<String, String> link = new HashMap<>();
            String path = "http://" + req.getServerName() + ":"
                    + req.getServerPort() + req.getContextPath()
                    + "/GestioneCampagnaController/campagna?idCampagna="
                    + campagna.getIdCampagna();
            String subject = "Dona a questa campagna presente su Fund.It ";

            link.put("mail", "mailto:?body=" + path + "&amp;subject= Titolo: "
                    + subject + campagna.getTitolo());
            link.put("whatsapp", "https://wa.me/?text=" + subject + path);
            link.put("facebook", "https://www.facebook.com/sharer/sharer.php?u="
                    + path);
            link.put("twitter", "https://twitter.com/share?text=" + subject
                    + "&amp;url=" + path + "/&amp;via=Fund.It");
            link.put("link", path);

            return link;
         }
      }
   }

   /**
    * Esegue i rimborsi delle eventuali donazioni
    * effettuate sulla campagna.
    *
    * @param campagna istanza di Campagna
    * @param proxy    proxy di Campagna per trovare le donazioni della campagna
    * @return true se l'operazione è andata a buon fine, false altrimenti
    */
   @Override
   public boolean rimborsaDonazioni(final Campagna campagna,
                                    final CampagnaInterface proxy) {
      if (campagna == null || proxy == null) {
         throw new IllegalArgumentException("Invalid argument");
      } else {
         List<Donazione> donazioni = proxy.getDonazioni();
         DAO<Donazione> daoDonazione = new DonazioneDAO();
         donazioni.forEach(d -> d.setSommaDonata(-d.getSommaDonata()));
         boolean flag = donazioni.stream().allMatch(daoDonazione::update);

         campagna.setDonazioni(donazioni);
         return flag;
      }
   }

   @Override
   public List<Campagna> ricercaCampagna(final String text) {
      return ((CampagnaDAO) dao).getByKeyword(text);
   }

   @Override
   public List<Campagna> ricercaCampagnaPerCategoria(final String text) {
      return ((CampagnaDAO) dao).getByCategory(text);
   }

   @Override
   public List<Campagna> visualizzaCampagne(final int size, final int offset) {
      return ((CampagnaDAO) dao).getBySizeOffset(size, offset);
   }

   /**
    * @param idCampagna id della campagna da cercare
    * @return istanza di Campagna avente come id idCampagna, null altrimenti
    */
   @Override
   public Campagna trovaCampagna(final int idCampagna) {
      return dao.getById(idCampagna);
   }

   @Override
   public boolean chiudiCampagna(final Campagna campagna) {
      campagna.setStato(StatoCampagna.CHIUSA);
      return modificaCampagna(campagna);
   }

   @Override
   public boolean cancellaCampagna(final Campagna campagna) {
      campagna.setStato(StatoCampagna.CANCELLATA);
      return modificaCampagna(campagna);
   }
}
