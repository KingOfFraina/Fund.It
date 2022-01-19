package model.services;

import model.DAO.DonazioneDAO;
import model.beans.Donazione;
import model.beans.Utente;

import java.util.List;

public final class DonazioniServiceImp implements DonazioniService {
    /**
     * dao utilizzato.
     */
    private DonazioneDAO dao;

    /**
     * costruttore.
     */
    public DonazioniServiceImp() {
        this.dao = new DonazioneDAO();
    }

    @Override
    public boolean effettuaDonazione(final Donazione d) {
        return dao.save(d);
    }

    @Override
    public List<Donazione> visualizzaDonazioni(final Utente u) {
        return dao.getAllByUtente(u.getIdUtente());
    }

    @Override
    public boolean commenta(final Donazione d) {
        return dao.update(d);
    }
}
