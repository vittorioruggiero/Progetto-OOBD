package dbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static DBConnection instance;
    private Connection connection;
    private String USERNAME = "postgres";
    private String PASSWORD = "database99";
    private String IP = "localhost";
    private String PORT = "5432";
    private String url;
    
    private DBConnection() throws SQLException {
        this.connection = null;
        this.url = "jdbc:postgresql://"+IP+":"+PORT+"/Agenzia procuratori";
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(this.url, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public static DBConnection getInstance() throws SQLException {
        if (DBConnection.instance == null) {
            DBConnection.instance = new DBConnection();
        }
        else if (DBConnection.instance.getConnection().isClosed()) {
            DBConnection.instance = new DBConnection();
        }
        return DBConnection.instance;
    }
}
