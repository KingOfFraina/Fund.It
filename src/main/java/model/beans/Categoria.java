package model.beans;

public final class Categoria {
    /***
     * Il nome della categoria.
     */
    private String nome;
    /**
     * id categoria.
     */
    private int idCategoria;

    /***
     * Costruttore.
     */
    public Categoria() {
        nome = "";
    }

    /***
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

    /**
     * @return ide della categoria.
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * @param id id della categoria.
     */
    public void setIdCategoria(final int id) {
        this.idCategoria = id;
    }

    @Override
    public String toString() {
        return "Categoria{"
                + "nome='" + nome + '\''
                + '}';
    }
}
