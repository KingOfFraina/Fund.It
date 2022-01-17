package model.beans.proxyInterfaces;

import model.beans.Donazione;
import model.beans.Immagine;
import model.beans.Segnalazione;

import java.util.List;

public interface CampagnaInterface {
   //TODO
   List<Segnalazione> getSegnalazioni();
   List<Immagine> getImmagini();
   List<Donazione> getDonazioni();
}
