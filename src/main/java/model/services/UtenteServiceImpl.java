package model.services;

import model.DAO.DAO;
import model.beans.Utente;

import java.time.LocalDateTime;
import java.util.List;

public class UtenteServiceImpl implements UtenteService {
   /**
    * Il DAO usato per eseguire le operazioni.
    */
   private final DAO<Utente> dao;

   /**
    * Il costruttore per la classe FAQService.
    *
    * @param utenteDAO il DAO da utilizzare
    *                  per eseguire le operazioni richieste.
    */
   public UtenteServiceImpl(final DAO<Utente> utenteDAO) {
      this.dao = utenteDAO;
   }

   /**
    * @param id idUtente.
    * @return l'istanza di utente presente nel database
    */
   @Override
   public Utente visualizzaDashboardUtente(final int id) {
      if (id > 0) {
         return dao.getById(id);
      } else {
         throw new IllegalArgumentException("Null Object");
      }
   }

   /**
    * @param utente da modificare.
    * @return esito operazione.
    */
   @Override
   public boolean modificaProfilo(final Utente utente) {
      if (utente != null) {
         return dao.update(utente);
      } else {
         throw new IllegalArgumentException("Null Object");
      }
   }

   /**
    * @param richiedente della lista completa utenti.
    * @return null se il richiedente non è admin. List di Utente se è admin.
    */
   @Override
   public List<Utente> visualizzaUtenti(final Utente richiedente) {
      if (richiedente != null) {
         if (!richiedente.isAdmin()) {
            throw new IllegalCallerException("Only Admin");
         } else {
            return dao.getAll();
         }
      } else {
         throw new IllegalArgumentException("Null Object");
      }
   }

   /**
    * @param richiedente Istanza di Utente di chi ha ordinato l'operazione.
    * @param soggetto    Istanza di Utente di cui va modificato lo stato.
    * @return esito dell'operazione.
    */
   @Override
   public boolean promuoviDeclassaUtente(final Utente richiedente,
                                         final Utente soggetto) {
      if (richiedente != null) {
         if (soggetto == null) {
            throw new IllegalArgumentException("Null Object");
         } else {
            if (!richiedente.isAdmin()) {
               throw new IllegalCallerException("Only Admin");
            } else {
               soggetto.setAdmin(!soggetto.isAdmin());
               return dao.update(soggetto);
            }
         }
      } else {
         throw new IllegalArgumentException("Null Object");
      }
   }

   /**
    * @param utente istanza di Utente da sospendere
    * @return true se l'utente è stato sospeso, false altrimenti
    */
   @Override
   public boolean sospensioneUtente(final Utente utente) {
      if (utente == null) {
         throw new IllegalArgumentException("Null Object");
      } else {
         if (utente.getDataBan() != null) {
            utente.setDataBan(null);
         } else {
            final long giorniBan = 5;
            utente.setDataBan(LocalDateTime.now()
                    .plusDays(giorniBan));
         }
         return dao.update(utente);
      }
   }
}
