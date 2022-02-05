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
    * Il costruttore per la classe FAQ.
    * @param newDAO il DAO da utilizzare per eseguire le operazioni richieste.
    */
   public FaqServiceImpl(final DAO<FAQ> newDAO) {
      this.dao = newDAO;
   }

   @Override
   public boolean inserisciFaq(final FAQ faq) {
      return dao.save(faq);
   }

   @Override
   public boolean cancellaFaq(final FAQ faq) {
      return dao.delete(faq);
   }

   @Override
   public boolean modificaFaq(final FAQ faq) {
      return dao.update(faq);
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
