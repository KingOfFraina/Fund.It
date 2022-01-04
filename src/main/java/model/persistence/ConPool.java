package model.persistence;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConPool {
    /***
     *
     */
    private DataSource datasource;
    /***
     *
     */
    private static ConPool instance;
    /***
     *
     */
    private static final int MAX_ACTIVE = 100;
    /***
     *
     */
    private static final int INITIAL_SIZE = 10;
    /***
     *
     */
    private static final int MIN_IDLE = 10;
    /***
     *
     */
    private static final int TIMEOUT = 60;

    private ConPool() {
    }

    /***
     *
     * @return ConPool
     */
    public static ConPool getInstance() {
        if (instance == null) {
            instance = new ConPool();
        }

        return instance;
    }

    /***
     *
     * @return Connection
     * @throws SQLException
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

    public void closeDataSource()
    {
       if(datasource != null)
          datasource.close();
    }
}
