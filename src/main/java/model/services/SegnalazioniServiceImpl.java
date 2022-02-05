package model.services;

import model.DAO.DAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;

import java.time.LocalDateTime;
import java.util.List;

public class SegnalazioniServiceImpl implements SegnalazioniService {

    /**
     * Wrapper d'istanza di SegnalazioneDAO.
     */
    private final DAO<Segnalazione> dao;

    /**
     * @param segnalazioneDAO istanza di interfaccia DAO<T>
     */
    public SegnalazioniServiceImpl(final DAO<Segnalazione> segnalazioneDAO) {
        this.dao = segnalazioneDAO;
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
     * @param campagna    istanza di Campagna da segnalare
     * @param segnalatore istanza di Utente che effettua la segnalazione
     * @param descrizione Stringa di descrizione della segnalazione
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean segnalaCampagna(final Campagna campagna,
                                   final Utente segnalatore,
                                   final String descrizione) {
        if (campagna == null || segnalatore == null || descrizione == null) {
            throw new IllegalArgumentException("Arguments must be not null");
        } else {
            Segnalazione s = new Segnalazione();
            s.setCampagnaSegnalata(campagna);
            s.setSegnalato(campagna.getUtente());
            s.setSegnalatore(segnalatore);
            s.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
            s.setDataOra(LocalDateTime.now());
            s.setDescrizione(descrizione);
            return dao.save(s);
        }
    }
}
