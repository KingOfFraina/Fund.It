package model.services;

import model.DAO.FaqDAO;
import model.beans.FAQ;

import java.util.List;

public final class FaqServiceImpl implements FaqService {
   @Override
   public boolean inserisciFaq(final FAQ faq) {
      return new FaqDAO().save(faq);
   }

   @Override
   public boolean cancellaFaq(final FAQ faq) {
      return new FaqDAO().delete(faq);
   }

   @Override
   public boolean modificaFaq(final FAQ faq) {
      return new FaqDAO().update(faq);
   }

   @Override
   public List<FAQ> visualizzaFaq() {
      return new FaqDAO().getAll();
   }

   @Override
   public FAQ visualizzaFaq(final int idFaq) {
      return new FaqDAO().getById(idFaq);
   }
}
