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
 *
 */
public class Movie {

	private int id;
	private String name;
	private int director;
	
	public Movie() {
	}
	public Movie(int i,String n,int d) {
		this.id=i;
		this.name=n;
		this.director=d;
	}
	public void setId(int i) {
		this.id=i;
	}
	public void setName(String n) {
		this.name=n;
	}
	public void setDirector(int i) {
		this.director=i;
	}
	public int getId() {
		return this.id;
	}
	public int getDirector() {
		return this.director;
	}
	public String getName() {
		return this.name;
	}
	public void addActor(Actor act) throws SQLException {
		Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
        		ResultSet rs = stmt.executeQuery("select max(id) from movie_actors")){
        	Integer x=rs.next() ? rs.getInt(1) : null;
        	x=x+1;
        try (PreparedStatement pstmt = con.prepareStatement("insert into movie_actors values(?,\'?\')") ){
            pstmt.setInt(1,this.id);
            pstmt.setInt(2, act.getId());
        	pstmt.executeUpdate();
        }
        }
	}
	public List<Actor> getActors(){
		Connection con = Database.getConnection();
    	try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from movies")) {
    		List<Actor> list = new ArrayList<Actor>();
    		while(rs.next()) {
    				Actor x = new Actor(rs.getInt(1),rs.getString(2));
    				list.add(x);
    		}
    		return list;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
