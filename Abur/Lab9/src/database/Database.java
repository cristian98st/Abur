/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author Alex
 *
 */
public class Database {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "student";
    private static final String PASSWORD = "STUDENT";
    private static Connection connection = null;
    private Database() { }
    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }
    public static void createConnection() {
    	Connection con=null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	Database.connection=con;
    }
    
    public static void closeConnection() {
    	try {
    	Database.connection.close();
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    public static void commit() {
    	try {
			Database.connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public static void rollback() {
    	try {
			Database.connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
