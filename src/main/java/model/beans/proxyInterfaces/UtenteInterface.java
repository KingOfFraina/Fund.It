package model.beans.proxyInterfaces;

import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Segnalazione;

import java.util.List;

public interface UtenteInterface {
   /**
    * @return lista delle segnalazioni effettuate.
    */
   List<Segnalazione> getSegnalazioni();

   /**
    * @return lista delle donazioni effettuate.
    */
   List<Donazione> getDonazioni();

   /**
    * @return lista delle campagne create.
    */
   List<Campagna> getCampagne();
}
