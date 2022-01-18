package model.services;

import model.beans.FAQ;

public interface FaqService {
   boolean inserisciFaq(FAQ faq);
   boolean cancellaFaq(FAQ faq);
   boolean modificaFaq(FAQ faq);
}
