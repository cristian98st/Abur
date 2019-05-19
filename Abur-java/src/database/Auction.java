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
public class Auction extends RecursiveTreeObject<Auction>{

	private IntegerProperty id_gamer = new SimpleIntegerProperty();
	private IntegerProperty id_item = new SimpleIntegerProperty();
	private FloatProperty price = new SimpleFloatProperty();
	private StringProperty exp_date = new SimpleStringProperty();

	public Auction() {
		this.id_gamer.set(-1);
		this.id_item.set(-1);
	}
	
//2019-04-01
	public Auction(Integer id_gamer,Integer id_item,Float price) {
		this.id_gamer.set(id_gamer);
		this.id_item.set(id_item);
		this.price.set(price);
	}
	
	public Auction(Integer id_gamer,Integer id_item,Float price,String d) {
		this.id_gamer.set(id_gamer);
		this.id_item.set(id_item);
		this.price.set(price);
		this.exp_date.set(d);
	}
	
	/**
	 * @return the id
	 */
	public Integer getIdGamer() {
		return this.id_gamer.get();
	}

	/**
	 * @param id the id to set
	 */
	public void setIdGamer(Integer id) {
		this.id_gamer.set(id);
	}
	
	public Integer getIdItem() {
		return this.id_item.get();
	}

	/**
	 * @param id the id to set
	 */
	public void setIdItem(Integer id) {
		this.id_item.set(id);
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
	
	public List<Auction> get(String col,String name,String col2Order,String order) throws SQLException, DBException {
		Connection con = Database.getConnection();
		String columns = "id_gamer,id_item,price,exp_date,added_at,updated_at";
		String ord = "asc,desc";
		List<Auction> list = new ArrayList<Auction>();
		Statement pstmt = con.createStatement();
		ResultSet rez;
		if(columns.contains(col) && !col.contains(",")) {
			if(ord.contains(order) && !order.contains(",")) {
				rez = pstmt.executeQuery("select * from auction where "+ col+" like '%"+ name +"%' order by "+col2Order +" "+order);
			}
			else
				rez = pstmt.executeQuery("select * from auction where "+ col+" like '%"+ name +"%' ");
			while(rez.next()) {	
				Auction x = new Auction(rez.getInt(1),rez.getInt(2),rez.getFloat(3),rez.getString(4));
				list.add(x);
			}
		}
		return list;
	}
	
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        PreparedStatement pstmt = con.prepareStatement("insert into auction values(?,?,?,to_date(?),sysdate,sysdate)");
        pstmt.setInt(1, this.getIdGamer());
        pstmt.setInt(2, this.getIdItem());
        pstmt.setFloat(3, this.getPrice());
        pstmt.setString(4, this.exp_date.get());
        pstmt.executeUpdate();
	}
	
}

