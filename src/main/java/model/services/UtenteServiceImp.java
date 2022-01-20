package model.services;

import model.DAO.UtenteDAO;
import model.beans.Utente;

import java.util.Date;
import java.util.List;

public class UtenteServiceImp implements UtenteService {
   /**
    * @param id idUtente.
    * @return l'istanza di utente presente nel database
    */
   @Override
   public Utente visualizzaDashboardUtente(final int id) {
      return new UtenteDAO().getById(id);
   }

   /**
    * @param utente da modificare.
    * @return esito operazione.
    */
   @Override
   public boolean modificaProfilo(final Utente utente) {
      UtenteDAO utenteDAO = new UtenteDAO();
      return utenteDAO.update(utente);
   }

   /**
    * @param richiedente della lista completa utenti.
    * @return null se il richiedente non è admin. List di Utente se è admin.
    */
   @Override
   public List<Utente> visualizzaUtenti(final int richiedente) {
      if (richiedente < 0) {
         return null;
      }
      UtenteDAO dao = new UtenteDAO();
      if (!dao.getById(richiedente).isAdmin()) {
         return null;
      }

      List<Utente> list = dao.getAll();

      return list;
   }

   /**
    * @param richiedente idUtente di chi ha ordinato l'operazione.
    * @param soggetto    idUtente di cui va modificato lo stato.
    * @return esito dell'operazione.
    */
   @Override
   public boolean promuoviDeclassaUtente(final int richiedente,
                                         final int soggetto) {
      if (richiedente < 0) {
         return false;
      }
      UtenteDAO dao = new UtenteDAO();
      if (!dao.getById(richiedente).isAdmin()) {
         return false;
      }

      Utente sogg = dao.getById(soggetto);
      if (sogg.isAdmin()) {
         sogg.setAdmin(false);
      } else {
         sogg.setAdmin(true);
      }
      dao.update(sogg);
      return false;
   }

   /**
    * @param idCattivone da bannare.
    * @return esito operazione.
    */
   @Override
   public boolean sospensioneUtente(final int idCattivone) {
      UtenteDAO utenteDAO = new UtenteDAO();
      Utente utente = utenteDAO.getById(idCattivone);
      utente.setDataBan(new Date());
      return utenteDAO.update(utente);
   }
}
