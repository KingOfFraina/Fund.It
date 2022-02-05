package model.services;

import model.DAO.DAO;
import model.beans.FAQ;

import java.util.List;

public final class FaqServiceImpl implements FaqService {
   /**
    * Il DAO usato per eseguire le operazioni.
    */
   private DAO<FAQ> dao;

   /**
    * Il costruttore per la classe FAQService.
    *
    * @param newDAO il DAO da utilizzare per eseguire le operazioni richieste.
    */
   public FaqServiceImpl(final DAO<FAQ> newDAO) {
      this.dao = newDAO;
   }

   @Override
   public boolean inserisciFaq(final FAQ faq) {
      if (faq.getUtenteCreatore().isAdmin()) {
         return dao.save(faq);
      } else {
         throw new IllegalCallerException("Only admin");
      }
   }

   @Override
   public boolean cancellaFaq(final FAQ faq) {
      if (faq.getUtenteCreatore().isAdmin()) {
         return dao.delete(faq);
      } else {
         throw new IllegalCallerException("Only admin");
      }
   }

   @Override
   public boolean modificaFaq(final FAQ faq) {
      if (faq.getUtenteCreatore().isAdmin()) {
         return dao.update(faq);
      } else {
         throw new IllegalCallerException("Only admin");
      }
   }

   @Override
   public List<FAQ> visualizzaFaq() {
      return dao.getAll();
   }

   @Override
   public FAQ visualizzaFaq(final int idFaq) {
      return dao.getById(idFaq);
   }
}
