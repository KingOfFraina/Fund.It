package model.storage;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConPool {
   /***
    * Instanza di dataSource.
    */
   private DataSource datasource;
   /***
    * Instanza della classe ConPool.
    */
   private static ConPool instance;
   /***
    * Connessioni Max.
    */
   private static final int MAX_ACTIVE = 100;
   /***
    * Size del pool.
    */
   private static final int INITIAL_SIZE = 10;
   /***
    * Tempo minimo di idle.
    */
   private static final int MIN_IDLE = 10;
   /***
    * Timeout connessione.
    */
   private static final int TIMEOUT = 15;

   private ConPool() {
   }

   /***
    * Metodo che ritorna l'instanza di ConPool.
    * @return l'istanza della classe
    */
   public static ConPool getInstance() {
      if (instance == null) {
         instance = new ConPool();
      }

      return instance;
   }

   /**
    * Metodo per ottenere una connessione al database.
    *
    * @return Una Connection
    * @throws SQLException Eccezione se qualcosa va storto
    */
   public Connection getConnection() throws SQLException {
      if (datasource == null) {
         PoolProperties p = new PoolProperties();
         p.setUrl("jdbc:mysql://"
                 + "fundit-mysqlserver.mysql.database.azure.com:3306/"
                 + "funditdb?useSSL=true");
         p.setDriverClassName("com.mysql.cj.jdbc.Driver");
         p.setUsername("funditadmin");
         p.setPassword("cAEmiz5Dx3PZRQ");
         p.setMaxActive(MAX_ACTIVE);
         p.setInitialSize(INITIAL_SIZE);
         p.setMinIdle(MIN_IDLE);
         p.setRemoveAbandonedTimeout(TIMEOUT);
         p.setRemoveAbandoned(true);
         datasource = new DataSource();
         datasource.setPoolProperties(p);
      }

      return datasource.getConnection();
   }

   /***
    * Metodo per chiudere la dataSource.
    */
   public void closeDataSource() {
      if (datasource != null) {
         try {
            datasource.getConnection().close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
         datasource.close();
      }
   }
}
