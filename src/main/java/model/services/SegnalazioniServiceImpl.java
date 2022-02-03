package model.services;

import model.DAO.DAO;
import model.DAO.SegnalazioneDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;
import model.beans.Utente;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class SegnalazioniServiceImpl implements SegnalazioniService {

    /**
     * Wrapper di istanza di SegnalazioneDAO.
     */
    private static DAO<Segnalazione> dao;

    /**
     * Costruttore.
     */
    public SegnalazioniServiceImpl() {
        if (dao == null) {
            dao = new SegnalazioneDAO();
        }
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
        return dao.getById(idSegnalazione);
    }

    /**
     * @param idSegnalazione intero che rappresenta l'id della segnalazione
     * @param stato          Il nuovo stato della segnalazione
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean risolviSegnalazione(final int idSegnalazione,
                                       final StatoSegnalazione stato) {
        Segnalazione s = dao.getById(idSegnalazione);
        if (s == null) {
            throw new RuntimeException("Segnalazione non trovata con id: "
                    + idSegnalazione);
        }
        s.setStatoSegnalazione(stato);
        return dao.update(s);
    }

    /**
     * @param campagna    istanza di Campagna da segnalare
     * @param segnalatore istanza di Utente che effettua la segnalazionr
     * @param descrizione Stringa di descrizione della segnalazione
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean segnalaCampagna(final Campagna campagna,
                                   final Utente segnalatore,
                                   final String descrizione) {
        if (campagna == null || segnalatore == null) {
            throw new RuntimeException("Campagna o segnalatore sono null");
        }
        Segnalazione s = new Segnalazione();
        s.setCampagnaSegnalata(campagna);
        s.setSegnalato(campagna.getUtente());
        s.setSegnalatore(segnalatore);
        s.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
        s.setDataOra(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        s.setDescrizione(descrizione);
        return dao.save(s);
    }
}
