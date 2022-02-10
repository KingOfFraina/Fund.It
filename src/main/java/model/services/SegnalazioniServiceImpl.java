package model.services;

import model.DAO.DAO;
import model.DAO.SegnalazioneDAO;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;

import java.util.List;

public class SegnalazioniServiceImpl implements SegnalazioniService {

    /**
     * Wrapper d'istanza di SegnalazioneDAO.
     */
    private final DAO<Segnalazione> dao;

    /**
     * Costruttore della classe SegnalazioniService.
     * @param segnalazioneDAO istanza di interfaccia DAO
     */
    public SegnalazioniServiceImpl(final DAO<Segnalazione> segnalazioneDAO) {
        this.dao = segnalazioneDAO;
    }

    /**
     * Costruttore della classe SegnalazioniService.
     */
    public SegnalazioniServiceImpl() {
        this.dao = new SegnalazioneDAO();
    }

    /**
     * @return lista delle segnalazioni effettuate dall'utente
     */
    @Override
    public List<Segnalazione> trovaSegnalazioni() {
        return dao.getAll();
    }

    /**
     * @param idSegnalazione id della segnalazione interessata
     * @return istanza di segnalazione
     */
    @Override
    public Segnalazione trovaSegnalazione(final int idSegnalazione) {
        if (idSegnalazione <= 0) {
            throw new IllegalArgumentException("Argument must be > 0");
        } else {
            return dao.getById(idSegnalazione);
        }
    }

    /**
     * @param idSegnalazione intero che rappresenta l'id della segnalazione
     * @param stato          Il nuovo stato della segnalazione
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean risolviSegnalazione(final int idSegnalazione,
                                       final StatoSegnalazione stato) {
        if (idSegnalazione <= 0) {
            throw new IllegalArgumentException("Argument id must be > 0");
        } else {
            Segnalazione s = dao.getById(idSegnalazione);
            if (s == null) {
                throw new RuntimeException("Segnalazione non trovata con id: "
                        + idSegnalazione);
            } else {
                s.setStatoSegnalazione(stato);
                return dao.update(s);
            }
        }
    }


    /**
     * @param segnalazione istanza di Segnalazione che esprime una
     *                     segnalazione fatta da un utente verso una campagna
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean segnalaCampagna(final Segnalazione segnalazione) {
        if (segnalazione == null || segnalazione.getCampagnaSegnalata() == null
                || segnalazione.getSegnalatore() == null
                || segnalazione.getSegnalato() == null
                || segnalazione.getDescrizione() == null) {
            throw new IllegalArgumentException("Arguments must be not null");
        } else {
            return dao.save(segnalazione);
        }
    }
}
