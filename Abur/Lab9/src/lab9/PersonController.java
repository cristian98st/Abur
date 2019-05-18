/**
 * 
 */
package lab9;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Database;

/**
 * @author Alex
 *	actor=0,director=1,
 */
public class PersonController {

    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
        		ResultSet rs = stmt.executeQuery("select max(id) from persons")){
        	Integer x=rs.next() ? rs.getInt(1) : null;
        	x=x+1;
        try (PreparedStatement pstmt = con.prepareStatement("insert into persons values(" + x + ",\'" + name + "\',0)")) {
            pstmt.executeUpdate();
        }
        }
    }
    
    public void create(String name,String type) throws SQLException {
        Connection con = Database.getConnection();
        Integer t=0;
        try (Statement stmt = con.createStatement();
        		ResultSet rs = stmt.executeQuery("select max(id) from persons")){
        	Integer x=rs.next() ? rs.getInt(1) : null;
        	x=x+1;
        	if(type.equals("Director"))
        		t=1;
        try (PreparedStatement pstmt = con.prepareStatement("insert into persons values(" + x + ",\'" + name + "\'," + t+ ")")) {
            pstmt.executeUpdate();
        }
        }
    }
    
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select id from persons where nume like '%" + name + "%'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
    public String findById(int id) throws SQLException {
    	Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select nume from persons where id = " + id )) {
            return rs.next() ? rs.getString(1) : null;
        }
    }	
    
    public List<Person> findAll(){
    	Connection con = Database.getConnection();
    	try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons")) {
    		List<Person> list = new ArrayList<Person>();
    		while(rs.next()) {
    			if(rs.getInt(3)==1) {
    				Actor x = new Actor(rs.getInt(1),rs.getString(2));
    				list.add(x);
    			}
    			else {
    				Director x = new Director(rs.getInt(1),rs.getString(2));
    				list.add(x);
    			}
    		}
    		return list;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}

