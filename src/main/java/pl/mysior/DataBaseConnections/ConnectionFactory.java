package pl.mysior.DataBaseConnections;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String url = "jdbc:mysql://localhost:3306/employee";
    private static final String user = "root";
    private static final String pass = "root";

    private static ConnectionFactory connectionFactory = null;

    public Connection getConnection() {
        Connection con = null;
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(pass);
        ds.setMinIdle(5);
        ds.setMaxActive(15);
        try {
            con = ds.getConnection();
            //con = DriverManager.getConnection(url,user,pass);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
}
