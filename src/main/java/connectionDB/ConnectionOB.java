package connectionDB;

import java.sql.*;

public class ConnectionOB {
    private static ConnectionOB connectionOB;
    private Connection connection;
    private ConnectionOB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost/logindatabase","root","12345");
    }
    public Connection getConnection(){

        return connection;

    }
    public static ConnectionOB getInstance() throws ClassNotFoundException, SQLException{
        if(connectionOB==null){

            connectionOB =new ConnectionOB();
        }
        return connectionOB;
    }
}
