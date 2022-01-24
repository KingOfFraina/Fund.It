package model.services;

import model.beans.Categoria;

import java.util.List;

public interface CategoriaService {
   /**
    * La funzione permette l'inserimento di una nuova categoria.
    *
    * @param categoria Il bean che contiene le informazioni della categoria
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean inserisciCategoria(Categoria categoria);

   /**
    * La funzione permette la modifica di una categoria presente.
    *
    * @param categoria Il bean che contiene le informazioni della categoria
    * @return l'esito con cui si è conclusa l'operazione
    */
   boolean modificaCategoria(Categoria categoria);

   /**
    * La funzione permette la visualizzazione di tutte le categorie presenti.
    *
    * @return la lista delle categorie
    */
   List<Categoria> visualizzaCategorie();

   /**
    * La funzione permette la ricerca di una categoria.
    *
    * @param categoria Il bean che contiene le informazioni della categoria
    * @return il bean della categoria se esiste
    */
   Categoria visualizzaCategoria(Categoria categoria);
}
