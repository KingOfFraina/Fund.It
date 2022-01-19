package model.services;

import model.DAO.DonazioneDAO;
import model.beans.Donazione;
import model.beans.Utente;

import java.util.List;

public class DonazioniServiceImp implements DonazioniService{

    private DonazioneDAO dao;

    public DonazioniServiceImp() {
        this.dao = new DonazioneDAO();
    }

    @Override
    public boolean effettuaDonazione(Donazione d) {
        return dao.save(d);
    }

    @Override
    public List<Donazione> visualizzaDonazioni(Utente u) {
        return dao.getAllByUtente(u.getIdUtente());
    }

    @Override
    public boolean commenta(Donazione d) {
        return dao.update(d);
    }
}
