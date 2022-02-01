package controller.utils;

import model.beans.Utente;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class Validator {
    /**
     * List di errori.
     */
    private final List<String> errors;
    /**
     * Request da controllare.
     */
    private final HttpServletRequest request;
    /**
     * Pattern per INT.
     */
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
    /**
     * Pattern per DOUBLE.
     */
    private static final Pattern DOUBLE_PATTERN =
            Pattern.compile("^(-)?(\\d+)(\\.\\d+)?$");
    /**
     * Pattern per NAME.
     */
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-zà-ź \\s]{2,50}$");
    /**
     * Pattern per EMAIL.
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9.!#$%&’*+\\=?^_`{|}~-]{1,29}+"
                    + "@[a-zA-Z0-9-]{1,29}+(?:\\.[a-zA-Z0-9-]+){1,10}$");
    /**
     * Pattern per PASSWORD.
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
                    + "(?=.*[@$!%*?&._])[A-Za-z\\d@$!%*?&._]{8,32}$");
    /**
     * Pattern per CODICE FISCALE.
     */
    private static final Pattern CODICE_FISCALE_PATTERN =
            Pattern.compile("^[A-Z]{6}[0-9]{2}[A-Z]"
                    + "[0-9]{2}[A-Z][0-9]{3}[A-Z]$");
    /**
     * Pattern per NUMERO DI TELEFONO.
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    /**
     * Pattern per INDIRIZZO.
     */
    private static final Pattern ADDRESS_PATTERN =
            Pattern.compile("[a-zA-Z0-9\\s]{1,74}+,\\s[0-9]{1,5}$");
    /**
     * Pattern per CITY.
     */
    private static final Pattern CITY_PATTERN =
            Pattern.compile("^[A-Za-zà-ź \s]{2,50}$");
    /**
     * Pattern per CAP.
     */
    private static final Pattern CAP_PATTERN = Pattern.compile("^[0-9]{5}$");

    /**
     * Costruttore.
     * @param requestt set della request per pullare gli int.
     */
    public Validator(final HttpServletRequest requestt) {
        errors = new ArrayList<>();
        this.request = requestt;
    }

    /**
     * Ci sono errori?
     * @return se ci sono errori.
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * getErrori.
     * @return errori.
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Inserisce message nella lista se la condition è false.
     * @param condition condizione.
     * @param message messaggio.
     * @return true se condition è vera false altrimenti.
     */
    private boolean gatherError(final boolean condition, final String message) {
        if (condition) {
            return true;
        } else {
            errors.add(message);
            return false;
        }
    }

    /**
     * checka se value != null o !value.isBlank().
     * @param value valore da checkare.
     * @return true o false.
     */
    public boolean required(final String value) {
        return value != null && !value.isBlank();
    }

    /**
     * assertMatch tra value e regxp con eventuale msg di errore.
     * @param value valore.
     * @param regexp regexp.
     * @param msg msg di errore.
     * @return true se matcha, altrimenti false.
     */
    public boolean assertMatch(final String value,
                               final Pattern regexp, final String msg) {
        String param = request.getParameter(value);
        boolean condition = required(param) && regexp.matcher(param).matches();

        return gatherError(condition, msg);
    }

    /**
     * Verifica CAP.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertCAP(final String value, final String msg) {
        return assertMatch(value, CAP_PATTERN, msg);
    }

    /**
     * Verifica City.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertCity(final String value, final String msg) {
        return assertMatch(value, CITY_PATTERN, msg);
    }

    /**
     * Verifica Indirizzo.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertIndirizzo(final String value, final String msg) {
        return assertMatch(value, ADDRESS_PATTERN, msg);
    }

    /**
     * Verifica int.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertInt(final String value, final String msg) {
        return assertMatch(value, INT_PATTERN, msg);
    }

    /**
     * Verifica Password.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertPassword(final String value, final String msg) {
        return assertMatch(value, PASSWORD_PATTERN, msg);
    }

    /**
     * Verifica Double.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertDouble(final String value, final String msg) {
        return assertMatch(value, DOUBLE_PATTERN, msg);
    }
    /**
     * Verifica Email.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertEmail(final String value, final String msg) {
        return assertMatch(value, EMAIL_PATTERN, msg);
    }
    /**
     * Verifica Phone.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertPhone(final String value, final String msg) {
        return assertMatch(value, PHONE_PATTERN, msg);
    }
    /**
     * Verifica Nome/Cognome.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertNameSurname(final String value, final String msg) {
        return assertMatch(value, NAME_PATTERN, msg);
    }
    /**
     * Verifica CF.
     * @param value valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertCF(final String value, final String msg) {
        return assertMatch(value, CODICE_FISCALE_PATTERN, msg);
    }
    /**
     * Verifica ints.
     * @param values valore da controllare.
     * @param msg   msg di errore.
     * @return true se value rispetta la regexp, false altrimenti.
     */
    public boolean assertInts(final String values, final String msg) {
        String[] params = request.getParameterValues(values);
        boolean allInt = Arrays.stream(params).
                allMatch(param -> INT_PATTERN.matcher(param).matches());
        return gatherError(allInt, msg);
    }

    /**
     * Check sulla lunghezza di due liste.
     * @param first nome della prima lista.
     * @param second nome della seconda lista.
     * @param msg msg.
     * @return true se stessa lunghezza, altrimenti false.
     */
    public boolean assertSize(final String first,
                              final String second, final String msg) {
        String[] firstList = request.getParameterValues(first);
        String[] secondList = request.getParameterValues(second);
        return gatherError(firstList.length == secondList.length, msg);
    }

    /**
     * Check se un bean può essere castato in modo sicuro.
     * @param type Istanza del Tipo con cui verificare la compatibilità.
     * @param attr Object da castare.
     * @return true se attr corrisponde, false altrimenti.
     */
    public boolean isValidBean(final Object type, final Object attr) {
        if (attr == null
                || !attr.getClass().getSimpleName().
                equals(type.getClass().getSimpleName())) {
            return false;
        }
        return true;
    }

    /**
     * Check se un Utente è admin.
     * @param ut utente
     * @return true se admin, false altrimenti.
     */
    public boolean isUtenteAdmin(final Utente ut) {
        if (!ut.isAdmin()) {
            return false;
        }
        return true;
    }

    /**
     * Pulisce gli errori memorizzati.
     */
    public void reset() {
        errors.clear();
    }
}
