package model.beans;

public final class Categoria {
    /***
     * Il nome della categoria.
     */
    private String nome;

    /***
     * Costruttore.
     */
    public Categoria() {
        nome = "";
    }

    /***
     *
     * @return String il nome della categoria.
     */
    public String getNome() {
        return nome;
    }

    /***
     *
     * @param nomeCategoria Il nome della categoria.
     */
    public void setNome(final String nomeCategoria) {
        this.nome = nomeCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{"
                + "nome='" + nome + '\''
                + '}';
    }
}
