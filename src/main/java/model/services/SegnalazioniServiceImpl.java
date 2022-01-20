package model.services;

import model.DAO.DAO;
import model.DAO.SegnalazioneDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;


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
     * @param richiedente id del richiedente della lista segnalazioni.
     * @return una collezione di tutte le segnalazioni effettuate.
     */
    @Override
    public List<Segnalazione> trovaSegnalazioni(final int richiedente) {
        return null;
    }

    /**
     * @param richiedente idUtente del richiedente del servizio.
     * @param s           Istanza di Segnalazione da risolvere.
     * @return true se la segnalazione è stata
     * risolta senza errori, false altrimenti.
     */
    @Override
    public boolean risolviSegnalazione(final int richiedente,
                                       final Segnalazione s) {
        return false;
    }

    /**
     * @param campagna    Istanza di Campagna da segnalare.
     * @param segnalatore Id di Utente che segnala la campagna.
     * @return true se la segnalazione è stata inserita, false altrimenti.
     */
    @Override
    public boolean segnalaCampagna(final Campagna campagna, final int segnalatore) {
        return false;
    }
}
