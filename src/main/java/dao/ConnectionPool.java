package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

class ConnectionPool {
    static Connection getConnection() throws SQLException {
        return Pool.POOL.getConnection();
    }

    private static class Pool {
        private static final ComboPooledDataSource POOL = new ComboPooledDataSource();

        static {
            try {
                POOL.setDriverClass("com.mysql.jdbc.Driver");
                POOL.setJdbcUrl("jdbc:mysql://localhost:3306/homework1");
                POOL.setUser("root");
                POOL.setPassword("admin");

                POOL.setMaxStatements(20);
                POOL.setMaxStatementsPerConnection(20);
                POOL.setMinPoolSize(1);
                POOL.setAcquireIncrement(1);
                POOL.setMaxPoolSize(10);
                POOL.setMaxIdleTime(30);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }
}
