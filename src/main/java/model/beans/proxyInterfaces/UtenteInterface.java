package model.beans.proxyInterfaces;

import model.beans.Campagna;
import model.beans.Donazione;
import model.beans.Segnalazione;

import java.util.List;

public interface UtenteInterface {
   //TODO
   List<Segnalazione> getSegnalazioni();
   List<Donazione> getDonazioni();
   List<Campagna> getCampagne();
}
