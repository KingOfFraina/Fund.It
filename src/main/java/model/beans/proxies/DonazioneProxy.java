package model.beans.proxies;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.DAO.UtenteDAO;
import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Utente;
import model.beans.proxyInterfaces.DonazioneInterface;

public final class DonazioneProxy implements DonazioneInterface {
    /**
     * donazione.
     */
    private Donazione donazione;

    /**
     * costruttore.
     *
     * @param newDonazione donazione.
     */
    public DonazioneProxy(final Donazione newDonazione) {
        this.donazione = newDonazione;
    }

    public DonazioneProxy() {
        this.donazione = new Donazione();
    }

    @Override
    public Campagna getCampagna() {
        if (donazione.getCampagna().getCategoria() == null) {
            DAO<Campagna> campagnaDAO = new CampagnaDAO();
            donazione.setCampagna(
                    campagnaDAO.getById(donazione.getCampagna().getIdCampagna()));
        }

        return donazione.getCampagna();
    }

    @Override
    public Utente getUtente() {
        if (donazione.getUtente().getCf() == null) {
            DAO<Utente> utenteDAO = new UtenteDAO();
            Utente u = utenteDAO.getById(donazione.getUtente().getIdUtente());
            Utente utenteNuovo = donazione.getUtente();
            utenteNuovo.setNome(u.getNome());
            utenteNuovo.setCognome(u.getCognome());
            donazione.setUtente(utenteNuovo);
        }

        return donazione.getUtente();
    }

    /**
     * @param donazione istanza di Donazione
     */

    public void setDonazione(Donazione donazione) {
        this.donazione = donazione;
    }
}
