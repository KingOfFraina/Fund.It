package model.services;

import model.DAO.SegnalazioneDAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Segnalazione;
import model.beans.StatoSegnalazione;

import java.util.Date;
import java.util.List;

public class SegnalazioneServiceImp implements SegnalazioneService {
    /**
     * @param richiedente id del richiedente della lista segnalazioni.
     * @return una collezione di tutte le segnalazioni effettuate.
     */
    @Override
    public List<Segnalazione> trovaSegnalazioni(final int richiedente) {
        if (richiedente < 0) {
            return null;
        }
        UtenteDAO utenteDAO = new UtenteDAO();
        if (!utenteDAO.getById(richiedente).isAdmin()) {
            return null;
        }
        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        return segnalazioneDAO.getAll();

    }

    /**
     * @param richiedente richiedente dell'operazione.
     * @param s           Istanza di Segnalazione da risolvere.
     * @return true se la segnalazione è stata
     * risolta senza errori, false altrimenti.
     */
    @Override
    public boolean risolviSegnalazione(final int richiedente,
                                       final Segnalazione s) {
        if (richiedente < 0) {
            return false;
        }
        UtenteDAO utenteDAO = new UtenteDAO();
        if (!utenteDAO.getById(richiedente).isAdmin()) {
            return false;
        }
        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        return segnalazioneDAO.update(s);
    }

    /**
     * @param campagna    Istanza di Campagna da segnalare.
     * @param segnalatore Id di Utente che segnala la campagna.
     * @return true se la segnalazione è stata inserita, false altrimenti.
     */
    @Override
    public boolean segnalaCampagna(final Campagna campagna,
                                   final int segnalatore) {
        UtenteDAO utenteDAO = new UtenteDAO();
        Segnalazione segnalazione = new Segnalazione();
        segnalazione.setCampagnaSegnalata(campagna);
        segnalazione.setSegnalato(campagna.getUtente());
        segnalazione.setSegnalatore(utenteDAO.getById(segnalatore));
        segnalazione.setDataOra(new Date());
        segnalazione.setDescrizione(""); //todo
        segnalazione.setStatoSegnalazione(StatoSegnalazione.ATTIVA);
        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        return segnalazioneDAO.save(segnalazione);

    }
}
