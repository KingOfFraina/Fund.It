package model.persistence;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConPool
{
   private DataSource datasource;
   private static ConPool instance;

   private ConPool()
   {
   }

   public static ConPool getInstance()
   {
      if(instance == null)
         instance = new ConPool();

      return instance;
   }

   public Connection getConnection() throws SQLException
   {
      if (datasource == null)
      {
         PoolProperties p = new PoolProperties();
         p.setUrl("jdbc:mysql://fundit-mysqlserver.mysql.database.azure.com:3306/funditdb?useSSL=true");
         p.setDriverClassName("com.mysql.cj.jdbc.Driver");
         p.setUsername("funditadmin");
         p.setPassword("cAEmiz5Dx3PZRQ");
         p.setMaxActive(100);
         p.setInitialSize(10);
         p.setMinIdle(10);
         p.setRemoveAbandonedTimeout(60);
         p.setRemoveAbandoned(true);
         datasource = new DataSource();
         datasource.setPoolProperties(p);
      }

      return datasource.getConnection();
   }
}