package model.beans;


import java.util.Date;


public final class Segnalazione {
    /**
     * Identificativo univoco della segnalazione.
     */
    private int idSegnalazione;
    /**
     * Data della segnalazione.
     */
    private Date dataOra;
    /**
     * Descrizione della segnalazione.
     */
    private String descrizione;
    /**
     * Insieme degli stati possibili assunti da una segnalazione.
     */
    private StatoSegnalazione statoSegnalazione;
    /**
     * Riferimento all'oggetto utente che segnala la campagna.
     */
    private Utente segnalatore;
    /**
     * Riferimento all'oggetto utente che viene segnalato.
     */
    private Utente segnalato;

    /**
     * @return int Id attuale della segnalazione
     */
    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    /**
     * @param id Nuovo identificativo della segnalazione.
     */
    public void setIdSegnalazione(final int id) {
        this.idSegnalazione = id;
    }

    /**
     * @return Date Rappresenta la data della segnalazione.
     */
    public Date getDataOra() {
        return dataOra;
    }

    /**
     * @param data La nuova data della segnalazione.
     */
    public void setDataOra(final Date data) {
        this.dataOra = data;
    }

    /**
     * @return String stringa descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param description La nuova descrizione della segnalazione.
     */
    public void setDescrizione(final String description) {
        this.descrizione = description;
    }

    /**
     * @return StatoSegnalazione Lo stato attuale della segnalazione.
     */
    public StatoSegnalazione getStatoSegnalazione() {
        return statoSegnalazione;
    }

    /**
     * @param stato Il nuovo stato della segnalazione.
     */
    public void setStatoSegnalazione(final StatoSegnalazione stato) {
        this.statoSegnalazione = stato;
    }

    /**
     * @return Utente oggetto utente.
     */
    public Utente getSegnalatore() {
        return segnalatore;
    }

    /**
     * @param utente Utente che ha segnalato la campagna.
     */
    public void setSegnalatore(final Utente utente) {
        this.segnalatore = utente;
    }

    /**
     * @return Utente istanza di Utente che viene segnalata.
     */
    public Utente getSegnalato() {
        return segnalato;
    }

    /**
     * @param utente Istanza di utente che viene segnalata.
     */
    public void setSegnalato(final Utente utente) {
        this.segnalato = utente;
    }

    @Override
    public String toString() {
        return "Segnalazione{"
                + "idSegnalazione=" + idSegnalazione
                + ", dataOra=" + dataOra
                + ", descrizione='" + descrizione + '\''
                + ", statoSegnalazione=" + statoSegnalazione
                + ", segnalatore=" + segnalatore
                + ", segnalato=" + segnalato
                + '}';
    }
}
