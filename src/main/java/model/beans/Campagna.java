package model.beans;

import model.beans.proxyInterfaces.CampagnaInterface;

import java.util.List;

public final class Campagna implements CampagnaInterface {
    /**
     * id della campagna.
     */
    private int idCampagna;
    /**
     * titolo della campagna.
     */
    private String titolo;
    /**
     * stato della campagna.
     */
    private StatoCampagna stato;
    /**
     * descrizione della campagna.
     */
    private String descrizione;
    /**
     * Somma attualmente raccolta nei fondi della campagna.
     */
    private Double sommaRaccolta;
    /**
     * Somma target scelta da raggiungere.
     */
    private Double sommaTarget;
    /**
     * riferimento alla categoria della campagna.
     */
    private Categoria categoria;
    /**
     * riferimento all'utente.
     */
    private Utente utente;
    /**
     * immagini relative alla campagna.
     */
    private List<Immagine> immagini;
    /**
     * lista delle donazioni alla campagna.
     */
    private List<Donazione> donazioni;
    /**
     * lista delle segnalazioni alla campagna.
     */
    private List<Segnalazione> segnalazioni;

    /**
     * @return id della campagna.
     */
    public int getIdCampagna() {
        return idCampagna;
    }

    /**
     * @return stato della campagna.
     */
    public StatoCampagna getStato() {
        return stato;
    }

    /**
     * @return descrizione della campagna.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @return somma raccolta.
     */
    public Double getSommaRaccolta() {
        return sommaRaccolta;
    }

    /**
     * @return somma target.
     */
    public Double getSommaTarget() {
        return sommaTarget;
    }

    /**
     * @return categoria di una campagna.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @return utente creatore della campagna.
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @param id id della campagna.
     */
    public void setIdCampagna(final int id) {
        this.idCampagna = id;
    }

    /**
     * @param status indica lo stato della campagna.
     */
    public void setStato(final StatoCampagna status) {
        this.stato = status;
    }

    /**
     * @param descrizioneCampagna indica la descrizione annessa alla campagna.
     */
    public void setDescrizione(final String descrizioneCampagna) {
        this.descrizione = descrizioneCampagna;
    }

    /**
     * @param fondiRaccolti indica la somma fino ad ora raccolta.
     */
    public void setSommaRaccolta(final Double fondiRaccolti) {
        this.sommaRaccolta = fondiRaccolti;
    }

    /**
     * @param target indica la somma da raggiungere per la raccolta.
     */
    public void setSommaTarget(final Double target) {
        this.sommaTarget = target;
    }

    /**
     * @param categoriaCampagna indica la categoria cui appartiene la campagna.
     */
    public void setCategoria(final Categoria categoriaCampagna) {
        this.categoria = categoriaCampagna;
    }

    /**
     * @param beneficiario indica l'utente creatore della campagna.
     */
    public void setUtente(final Utente beneficiario) {
        this.utente = beneficiario;
    }

    /**
     * @return foto sulla campagna.
     */
    public List<Immagine> getImmagini() {
        return immagini;
    }

    /**
     * @param foto lista di foto relative alla campagna.
     */
    public void setImmagini(final List<Immagine> foto) {
        this.immagini = foto;
    }

    /**
     * @return lista delle donazioni.
     */
    public List<Donazione> getDonazioni() {
        return donazioni;
    }

    /**
     * @param importo lista delle donazioni.
     */
    public void setDonazioni(final List<Donazione> importo) {
        this.donazioni = importo;
    }

    /**
     * @return lista delle segnalazioni.
     */
    public List<Segnalazione> getSegnalazioni() {
        return segnalazioni;
    }

    /**
     * @param reports lista delle segnalazioni.
     */
    public void setSegnalazioni(final List<Segnalazione> reports) {
        this.segnalazioni = reports;
    }

    /**
     * @return titolo della campagna.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @param title titolo della campagna
     */
    public void setTitolo(final String title) {
        this.titolo = title;
    }

    @Override
    public String toString() {
        return "Campagna{"
                + "idCampagna="
                + idCampagna
                + ", titolo="
                + titolo
                + ", stato="
                + stato
                + ", descrizione='"
                + descrizione + '\''
                + ", sommaRaccolta="
                + sommaRaccolta
                + ", sommaTarget="
                + sommaTarget
                + ", categoria="
                + categoria.getIdCategoria()
                + ", utente="
                + utente.getIdUtente()
                + '}';
    }
}
