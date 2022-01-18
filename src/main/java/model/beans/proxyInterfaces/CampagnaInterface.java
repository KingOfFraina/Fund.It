package model.beans.proxyInterfaces;

import model.beans.Donazione;
import model.beans.Immagine;
import model.beans.Segnalazione;

import java.util.List;

public interface CampagnaInterface {
   /**
    * @return lista delle segnalazioni di una campagna.
    */
   List<Segnalazione> getSegnalazioni();

   /**
    * @return lista immagini di una campagna.
    */
   List<Immagine> getImmagini();

   /**
    * @return lista donazioni relativa ad una campagna.
    */
   List<Donazione> getDonazioni();
}
