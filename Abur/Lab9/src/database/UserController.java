/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alex
 *
 */
public class UserController {
	public void create(String name, Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
        		ResultSet rs = stmt.executeQuery("select max(id) from movies")){
        	Integer x=rs.next() ? rs.getInt(1) : null;
        	x=x+1;
        try (PreparedStatement pstmt = con.prepareStatement("insert into movies values(?,\'?\',?)")) {
            pstmt.executeUpdate();
        }
        }
    }

}
