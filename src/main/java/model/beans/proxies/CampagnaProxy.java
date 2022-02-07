package model.beans.proxies;


import model.DAO.DAO;
import model.DAO.DonazioneDAO;
import model.DAO.ImmagineDAO;
import model.DAO.SegnalazioneDAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Immagine;
import model.beans.Segnalazione;
import model.beans.Utente;
import model.beans.proxyInterfaces.CampagnaInterface;

import java.util.List;

public final class CampagnaProxy implements CampagnaInterface {

    /**
     * riferimento alla campagna.
     */
    private final Campagna campagna;

    /**
     * costruttore.
     *
     * @param newCampagna campagna
     */
    public CampagnaProxy(final Campagna newCampagna) {
        this.campagna = newCampagna;
    }

    @Override
    public List<Immagine> getImmagini() {
        if (campagna.getImmagini() == null) {
            ImmagineDAO immagineDAO = new ImmagineDAO();
            List<Immagine> immagineList =
                    immagineDAO.getByIdCampagna(campagna.getIdCampagna());

            immagineList.forEach(i -> i.setCampagna(campagna));

            campagna.setImmagini(immagineList);

            return immagineList;
        } else {
            return campagna.getImmagini();
        }
    }

    @Override
    public List<Donazione> getDonazioni() {
        if (campagna.getDonazioni() == null) {
            DonazioneDAO donazioneDAO = new DonazioneDAO();
            List<Donazione> donazioneList =
                    donazioneDAO.getByIdCampagna(campagna.getIdCampagna());

            donazioneList.forEach(d -> d.setCampagna(campagna));
            double somma = donazioneList.stream().
                    mapToDouble(Donazione::getSommaDonata).
                    sum();
            campagna.setSommaRaccolta(somma);
            campagna.setDonazioni(donazioneList);
            return donazioneList;
        } else {
            return campagna.getDonazioni();
        }
    }

    /**
     * @return istanza dell'utente creatore della campagna.
     */
    @Override
    public Utente getUtente() {
        if (campagna.getUtente().getCf() == null) {
            DAO<Utente> dao = new UtenteDAO();
            Utente u =
                    dao.getById(campagna.getUtente().getIdUtente());
            campagna.setUtente(u);

            return u;
        } else {
            return campagna.getUtente();
        }
    }

    @Override
    public List<Segnalazione> getSegnalazioni() {
        if (campagna.getSegnalazioni() == null) {
            SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
            List<Segnalazione> segnalazioneList =
                    segnalazioneDAO.getByIdCampagna(campagna.getIdCampagna());

            segnalazioneList.forEach(s -> s.setCampagnaSegnalata(campagna));

            campagna.setSegnalazioni(segnalazioneDAO.getAll());
            return segnalazioneList;
        } else {
            return campagna.getSegnalazioni();
        }
    }
}
