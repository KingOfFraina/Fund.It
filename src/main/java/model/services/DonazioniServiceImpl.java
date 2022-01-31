package model.services;

import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.beans.Donazione;
import model.beans.Utente;

import java.util.List;

public final class DonazioniServiceImpl implements DonazioniService {
    /**
     * Wrapper di DonazioneDAO.
     */
    private final DAO<Donazione> dao;

    /**
     * @param donazioneDAO istanza di DonazioneDAO
     */
    public DonazioniServiceImpl(final DAO<Donazione> donazioneDAO) {
        this.dao = donazioneDAO;
    }

    @Override
    public boolean effettuaDonazione(final Donazione d) {
        return dao.save(d);
    }

    @Override
    public List<Donazione> visualizzaDonazioni(final Utente u) {
        if (!u.isAdmin()) {
            DonazioneDAO donazioneDAO = (DonazioneDAO) dao;
            return donazioneDAO.getAllByUtente(u.getIdUtente());
        }

        return new DonazioneDAO().getAll();
    }

    @Override
    public boolean commenta(final Donazione d) {
        return dao.update(d);
    }
}
