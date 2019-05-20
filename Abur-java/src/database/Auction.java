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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Alex
 *
 */
public class Auction extends RecursiveTreeObject<Auction>{

	public StringProperty id_gamer = new SimpleStringProperty();
	public StringProperty id_item = new SimpleStringProperty();
	public StringProperty price = new SimpleStringProperty();
	public StringProperty exp_date = new SimpleStringProperty();
	public Item x;
	public Auction() {
		this.id_gamer.set("-1");
		this.id_item.set("-1");
	}
	
//2019-04-01
	public Auction(Integer id_gamer,Integer id_item,Float price) {
		this.id_gamer.set(String.valueOf(id_gamer));
		this.id_item.set(String.valueOf(id_item));
		this.price.set(String.valueOf(price));
		try {
			this.x=this.x.getByName(String.valueOf(id_item));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	public Auction(Integer id_gamer,Integer id_item,Float price,String d) {
		this.id_gamer.set(String.valueOf(id_gamer));
		this.id_item.set(String.valueOf(id_item));
		this.price.set(String.valueOf(price));
		this.exp_date.set(d);
	}
	
	/**
	 * @return the id
	 */
	public Integer getIdGamer() {
		return Integer.valueOf(this.id_gamer.get());
	}
	
	public Integer getIdItem() {
		return Integer.valueOf(this.id_item.get());
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return Float.valueOf(this.price.get());
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price.set(String.valueOf(price));
	}
	
	public ObservableList<Auction> get(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		ObservableList<Auction> list = FXCollections.observableArrayList();
		Statement pstmt = con.createStatement();
		ResultSet rez;
		rez = pstmt.executeQuery("select * from auction where id_item in (select id from items where item_name like '%"+ name +"%'");
			while(rez.next()) {	
				Auction x = new Auction(rez.getInt(1),rez.getInt(2),rez.getFloat(3),rez.getString(4));
				list.add(x);
			}
		return list;
	}
	
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement pstmt = con.prepareStatement("insert into auction values(?,?,?,to_date(?,'yyyy-mm-dd'),sysdate,sysdate)");
        pstmt.setInt(1, this.getIdGamer());
        pstmt.setInt(2, this.getIdItem());
        pstmt.setFloat(3, this.getPrice());
        pstmt.setString(4, this.exp_date.get());
        pstmt.executeUpdate();
	}
	
}

