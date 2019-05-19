/**
 * 
 */
package database;

/**
 * @author Alex
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Alex
 *
 */
public class Game extends RecursiveTreeObject<Game>{

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty title = new SimpleStringProperty();
	private StringProperty launch_date = new SimpleStringProperty();
	private FloatProperty price = new SimpleFloatProperty();

	public Game() {
		this.id.set(-1);
	}
	
//2019-04-01
	public Game(Integer id,String title,String date,Float price) {
		this.id.set(id);
		this.title.set(title);
		this.launch_date.set(date);
		this.price.set(price);
	}
	
	public Game(String title,String date,Float price) {
		this.id.set(-1);
		this.title.set(title);
		this.launch_date.set(date);
		this.price.set(price);
	}

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id.get();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id.set(id);
	}

	public void setTitle(String title) {
		this.title.set(title);
	}
	
	public String getTitle() {
		return this.title.get();
	}
	
	public void setDate(String d) {
		this.launch_date.set(d);
	}
	
	public String getDate() {
		return this.launch_date.get();
	}
	/**
	 * @return the price
	 */
	public Float getPrice() {
		return this.price.get();
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price.set(price);
	}
	
	public List<Game> get(String col,String name,String col2Order,String order) throws SQLException, DBException {
		Connection con = Database.getConnection();
		String columns = "id,title,price,launch_date,added_at,updated_at";
		String ord = "asc,desc";
		List<Game> list = new ArrayList<Game>();
		Statement pstmt = con.createStatement();
		ResultSet rez;
		if(columns.contains(col) && !col.contains(",")) {
			if(ord.contains(order) && !order.contains(",")) {
				rez = pstmt.executeQuery("select * from games where "+ col+" like '%"+ name +"%' order by "+col2Order +" "+order);
			}
			else
				rez = pstmt.executeQuery("select * from games where "+ col+" like '%"+ name +"%' ");
			while(rez.next()) {	
				Game x = new Game(rez.getInt(1),rez.getString(2),rez.getString(4),rez.getFloat(3));
				list.add(x);
			}
		}
		return list;
	}
	
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        if(this.id.getValue()==-1) {
		    try (Statement stmt = con.createStatement();
		    		ResultSet rs = stmt.executeQuery("select max(id) from games")){
		    	this.id.set((int) (rs.next() ? rs.getInt(1)+1 : 1));
		    }
        }
        PreparedStatement pstmt = con.prepareStatement("insert into games values(?,?,?,to_date(?),sysdate,sysdate)");
        pstmt.setInt(1, this.getId());
        pstmt.setString(2, this.getTitle());
        pstmt.setFloat(3, this.getPrice());
        pstmt.setString(4, this.getDate());
        pstmt.executeUpdate();
	}
	
}
