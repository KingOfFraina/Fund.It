package model.services;

import model.DAO.DonazioneDAO;
import model.beans.Donazione;
import model.beans.Utente;

import java.util.List;

public final class DonazioniServiceImpl implements DonazioniService {

    @Override
    public boolean effettuaDonazione(final Donazione d) {
        return new DonazioneDAO().save(d);
    }

    @Override
    public List<Donazione> visualizzaDonazioni(final Utente u) {
        if(!u.isAdmin())
            return new DonazioneDAO().getAllByUtente(u.getIdUtente());
        return new DonazioneDAO().getAll();
    }

    @Override
    public boolean commenta(final Donazione d) {
        return new DonazioneDAO().update(d);
    }
}
