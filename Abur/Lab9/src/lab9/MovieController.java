package lab9;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieController {
    public void create(String name, Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
        		ResultSet rs = stmt.executeQuery("select max(id) from movies")){
        	Integer x=rs.next() ? rs.getInt(1) : null;
        	x=x+1;
        try (PreparedStatement pstmt = con.prepareStatement("insert into movies values(" + x + ",\'" + name + "\'," + id + ")")) {
            pstmt.executeUpdate();
        }
        }
    }
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select id from movies where nume like '%" + name + "%'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
    public String findById(int id) throws SQLException {
    	Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select nume from movies where id = " + id )) {
            return rs.next() ? rs.getString(1) : null;
        }
    }	
}
