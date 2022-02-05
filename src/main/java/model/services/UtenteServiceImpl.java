package model.services;

import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Utente;
import java.time.LocalDateTime;
import java.util.List;

public class UtenteServiceImpl implements UtenteService {

    private DAO<Utente> dao;


    //public UtenteServiceImpl(final DAO<Uten>)
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
        return new UtenteDAO().update(utente);
    }

    /**
     * @param richiedente della lista completa utenti.
     * @return null se il richiedente non è admin. List di Utente se è admin.
     */
    @Override
    public List<Utente> visualizzaUtenti(final Utente richiedente) {
        if (!richiedente.isAdmin()) {
            return null;
        }

        return new UtenteDAO().getAll();
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

        if (!richiedente.isAdmin()) {
            return false;
        }

        soggetto.setAdmin(!soggetto.isAdmin());
        return new UtenteDAO().update(soggetto);
    }

    /**
     * @param utente istanza di Utente da sospendere
     * @return true se l'utente è stato sospeso, false altrimenti
     */
    @Override
    public boolean sospensioneUtente(final Utente utente) {
        if (utente.getDataBan() != null) {
            utente.setDataBan(null);
        } else {
            utente.setDataBan(LocalDateTime.now());
        }
        DAO<Utente> utenteDAO = new UtenteDAO();
        return utenteDAO.update(utente);
    }
}
