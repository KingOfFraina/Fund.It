package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAOHelper<T> {
   /**
    * @param resultSet resultSet della query eseguita
    * @param alias     eventuale alias del field
    * @return l'istanza della della classe T popolata con le informazioni
    * presenti nel resultSet
    * @throws SQLException eccezione lanciata in caso di problemi
    */
   T extract(ResultSet resultSet, String alias) throws SQLException;

   /**
    * @param preparedStatement il prepared Statement da popolare
    * @param entity            l'entity sorgente dei dati
    * @return l'index del campo successivo da riempire
    * @throws SQLException eccezione lanciata in caso di problemi
    */
   int fillPreparedStatement(PreparedStatement preparedStatement, T entity)
           throws SQLException;
}
