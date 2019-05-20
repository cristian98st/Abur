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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Alex
 *
 */
public class Game extends RecursiveTreeObject<Game>{

	public StringProperty id = new SimpleStringProperty();
	public StringProperty title = new SimpleStringProperty();
	public StringProperty launch_date = new SimpleStringProperty();
	public StringProperty price = new SimpleStringProperty();

	public Game() {
		this.id.set("-1");
	}
	
//2019-04-01
	public Game(String id,String title,String date,String price) {
		this.id.set(id);
		this.title.set(title);
		this.launch_date.set(date);
		this.price.set(price);
	}
	
	public Game(String title,String date,String price) {
		this.id.set("-1");
		this.title.set(title);
		this.launch_date.set(date);
		this.price.set(price);
	}

	
	/**
	 * @return the id
	 */
	public String getId() {
		return this.id.get();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	public String getPrice() {
		return this.price.get();
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price.set(price);
	}
	
	public ObservableList<Game> get(String col,String name,String col2Order,String order) throws SQLException, DBException {
		Connection con = Database.getConnection();
		String columns = "id,title,price,launch_date,added_at,updated_at";
		String ord = "asc,desc";
		ObservableList<Game> list = FXCollections.observableArrayList();
		Statement pstmt = con.createStatement();
		ResultSet rez;
		if(columns.contains(col) && !col.contains(",")) {
			if(ord.contains(order) && !order.contains(",")) {
				rez = pstmt.executeQuery("select * from games where "+ col+" like '%"+ name +"%' order by "+col2Order +" "+order);
			}
			else
				rez = pstmt.executeQuery("select * from games where "+ col+" like '%"+ name +"%' ");
			while(rez.next()) {	
				Game x = new Game(String.valueOf(rez.getInt(1)),rez.getString(2),rez.getString(4),String.valueOf(rez.getFloat(3)));
				list.add(x);
			}
		}
		return list;
	}

	public void buyGame(){

	}
	
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        if(this.id.get().equals("-1")) {
		    try (Statement stmt = con.createStatement();
		    		ResultSet rs = stmt.executeQuery("select max(id) from games")){
		    	this.id.set(String.valueOf(rs.next() ? rs.getInt(1)+1 : 1));
		    }
        }
        PreparedStatement pstmt = con.prepareStatement("insert into games values(?,?,?,to_date(?,'yyyy-mm-dd'),sysdate,sysdate)");
        pstmt.setInt(1, Integer.valueOf(this.getId()));
        pstmt.setString(2, this.getTitle());
        pstmt.setFloat(3, Float.valueOf(this.getPrice()));
        pstmt.setString(4, this.getDate());
        System.out.print(this.getId());
        pstmt.executeUpdate();
	}
	
}
