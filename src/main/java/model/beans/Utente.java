package model.beans;

import java.util.Date;
import java.util.List;

public final class Utente {
    /**
     * Id dell'utente.
     */
    private int idUtente;
    /**
     * Data del ban dell'utente.
     */
    private Date dataBan;
    /**
     * Booleano che identifica se l'utente è admin oppure no.
     */
    private boolean admin;
    /**
     * Path della fotoProfilo.
     */
    private String fotoProfilo;
    /**
     * Password dell'utente.
     */
    private String password;
    /**
     * Numero di telefono.
     */
    private String telefono;
    /**
     * Nome dell'utente.
     */
    private String nome;
    /**
     * Cognome dell'utente.
     */
    private String cognome;
    /**
     * Email dell'utente.
     */
    private String email;
    /**
     * Strada dell'abitazione dell'utente.
     */
    private String strada;
    /**
     * Città dell'utente.
     */
    private String citta;
    /**
     * Cap dell'utente.
     */
    private String cap;
    /**
     * Codice Fiscale dell'utente.
     */
    private String cf;
    /**
     * Data di nascita dell'utente.
     */
    private Date dataDiNascita;
    /**
     * Lista delle donazioni effettuate dall'utente.
     */
    private List<Donazione> donazioniList;
    /**
     * Lista delle segnalazioni .... TODO
     */
    private List<Segnalazione> segnalazioneList;

    /**
     * Lista delle campagne avviate dall'utente.
     */
    private List<Campagna> campagnaList;

    /**
     * @return idUtente.
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * @param id idUtente.
     */
    public void setIdUtente(final int id) {
        this.idUtente = id;
    }

    /**
     * @return dataBan.
     */
    public Date getDataBan() {
        return dataBan;
    }

    /**
     * @param data Data del ban.
     */
    public void setDataBan(final Date data) {
        this.dataBan = data;
    }

    /**
     * @return isAdmin.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param adm bool (Admin==1/Utente normale==0).
     */
    public void setAdmin(final boolean adm) {
        this.admin = adm;
    }

    /**
     * @return Path della foto profilo.
     */
    public String getFotoProfilo() {
        return fotoProfilo;
    }

    /**
     * @param path Path della foto profilo.
     */
    public void setFotoProfilo(final String path) {
        this.fotoProfilo = path;
    }

    /**
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param pw Password.
     */
    public void setPassword(final String pw) {
        this.password = pw;
    }

    /**
     * @return Telefono registrato.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param tf Telefono da registrare.
     */
    public void setTelefono(final String tf) {
        this.telefono = tf;
    }

    /**
     * @return Nome dell'utente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param name Nome dell'utente.
     */
    public void setNome(final String name) {
        this.nome = name;
    }

    /**
     * @return Cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param surname Cognome dell'utente.
     */
    public void setCognome(final String surname) {
        this.cognome = surname;
    }

    /**
     * @return E-mail dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param em E-mail dell'utente.
     */
    public void setEmail(final String em) {
        this.email = em;
    }

    /**
     * @return Strada dell'utente.
     */
    public String getStrada() {
        return strada;
    }

    /**
     * @param st Strada dell'utente.
     */
    public void setStrada(final String st) {
        this.strada = st;
    }

    /**
     * @return Città dell'utente.
     */
    public String getCitta() {
        return citta;
    }

    /**
     * @param city Città dell'utente.
     */
    public void setCitta(final String city) {
        this.citta = city;
    }

    /**
     * @return Cap dell'utente.
     */
    public String getCap() {
        return cap;
    }

    /**
     * @param cappello Cap dell'utente.
     */
    public void setCap(final String cappello) {
        this.cap = cappello;
    }

    /**
     * @return Codice Fiscale dell'utente.
     */
    public String getCf() {
        return cf;
    }

    /**
     * @param codf Codice Fiscale dell'utente.
     */
    public void setCf(final String codf) {
        this.cf = codf;
    }

    /**
     * @return Data Di Nascita dell'utente.
     */
    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * @param ddn Data Di Nascita dell'utente.
     */
    public void setDataDiNascita(final Date ddn) {
        this.dataDiNascita = ddn;
    }

    /**
     * @return Lista donazioni effettuate dall'utente.
     */
    public List<Donazione> getDonazioniList() {
        return donazioniList;
    }

    /**
     * @param dList Lista donazioni effettuate dall'utente.
     */
    public void setDonazioniList(final List<Donazione> dList) {
        this.donazioniList = dList;
    }

    /**
     * @return Lista TODO .
     */
    public List<Segnalazione> getSegnalazioneList() {
        return segnalazioneList;
    }

    /**
     * @param sList List TODO .
     */
    public void setSegnalazioneList(final List<Segnalazione> sList) {
        this.segnalazioneList = sList;
    }

    /**
     * @return Lista delle campagne avviate dall'utente.
     */
    public List<Campagna> getCampagnaList() {
        return campagnaList;
    }

    /**
     * @param cList Lista delle campagne avviate dall'utente.
     */
    public void setCampagnaList(final List<Campagna> cList) {
        this.campagnaList = cList;
    }
}
