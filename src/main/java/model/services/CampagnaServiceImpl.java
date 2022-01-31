package model.services;

import model.DAO.CampagnaDAO;
import model.DAO.DAO;
import model.beans.Campagna;
import model.beans.StatoCampagna;

import java.util.List;

public final class CampagnaServiceImpl implements CampagnaService {
    /**
     * Wrapper di campagna DAO.
     */
    private final DAO<Campagna> dao;

    /**
     *
     * @param campagnaDAO Istanza di campagna DAO
     */
    public CampagnaServiceImpl(final DAO<Campagna> campagnaDAO) {
        this.dao = campagnaDAO;
    }

    @Override
    public boolean creazioneCampagna(final Campagna campagna) {
        return dao.save(campagna);
    }

    @Override
    public boolean modificaCampagna(final Campagna campagna) {
        return dao.update(campagna);
    }

    @Override
    public String condividiCampagna(final int idCampagna) {
        Campagna campagna = dao.getById(idCampagna);

        if (campagna != null) {
            return campagna.getTitolo();
        }

        return null;
    }

    @Override
    public List<Campagna> ricercaCampagna(final String text) {
        CampagnaDAO campagnaDAO = (CampagnaDAO) dao;
        return campagnaDAO.getByKeyword(text);
    }

    @Override
    public List<Campagna> visualizzaCampagne(final int size, final int offset) {
        CampagnaDAO campagnaDAO = (CampagnaDAO) dao;
        return campagnaDAO.getBySizeOffset(size, offset);
    }

    /**
     * @param idCampagna id della campagna da cercare
     * @return istanza di Campagna avente come id idCampagna, null altrimenti
     */
    @Override
    public Campagna trovaCampagna(final int idCampagna) {
        return dao.getById(idCampagna);
    }

    @Override
    public boolean chiudiCampagna(final Campagna campagna) {
        campagna.setStato(StatoCampagna.CHIUSA);
        return modificaCampagna(campagna);
    }

    @Override
    public boolean cancellaCampagna(final Campagna campagna) {
        campagna.setStato(StatoCampagna.CANCELLATA);
        return modificaCampagna(campagna);
    }
}
