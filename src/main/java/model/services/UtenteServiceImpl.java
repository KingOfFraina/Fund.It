package model.services;

import model.DAO.UtenteDAO;
import model.beans.Utente;

import java.util.Date;
import java.util.List;

public class UtenteServiceImpl implements UtenteService {
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
    public List<Utente> visualizzaUtenti(final Utente richiedente) {
        if (richiedente.getIdUtente() < 0) {
            return null;
        }

        UtenteDAO dao = new UtenteDAO();
        List<Utente> list = dao.getAll();

        return list;
    }

    /**
     * @param richiedente Istanza di Utente di chi ha ordinato l'operazione.
     * @param soggetto    Istanza di Utente di cui va modificato lo stato.
     * @return esito dell'operazione.
     */
    @Override
    public boolean promuoviDeclassaUtente(final Utente richiedente,
                                          final Utente soggetto) {
        if (richiedente.getIdUtente() < 0) {
            return false;
        }
        UtenteDAO dao = new UtenteDAO();
        if (!richiedente.isAdmin()) {
            return false;
        }

        if (soggetto.isAdmin()) {
            soggetto.setAdmin(false);
        } else {
            soggetto.setAdmin(true);
        }
        dao.update(soggetto);
        return false;
    }

    /**
     * @param cattivone da bannare.
     * @return esito operazione.
     */
    @Override
    public boolean sospensioneUtente(final Utente cattivone) {
        UtenteDAO utenteDAO = new UtenteDAO();
        cattivone.setDataBan(new Date());
        return utenteDAO.update(cattivone);
    }
}
