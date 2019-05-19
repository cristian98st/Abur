/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Alex
 *
 */
public class Item extends RecursiveTreeObject<Item>{

	private IntegerProperty id;
	private StringProperty name;
	private StringProperty pclass;
	private StringProperty type;
	private StringProperty wear;
	private StringProperty rarity;
	private IntegerProperty price;

	public Item() {
		this.id.set(-1);
	}
	

	public Item(Integer id,String name,String pclass,String type,String wear,String rarity,Integer price) {
		this.id.set(id);
		this.name.set(name);
		this.pclass.set(pclass);
		this.type.set(type);
		this.wear.set(wear);
		this.rarity.set(rarity);
		this.price.set(price);
	}
	
	public Item(String name,String pclass,String type,String wear,String rarity,Integer price) {
		this.id.set(-1);
		this.name.set(name);
		this.pclass.set(pclass);
		this.type.set(type);
		this.wear.set(wear);
		this.rarity.set(rarity);
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

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name.get();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * @return the pclass
	 */
	public String getPclass() {
		return this.pclass.get();
	}

	/**
	 * @param pclass the pclass to set
	 */
	public void setPclass(String pclass) {
		this.pclass.set(pclass);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type.get();
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type.set(type);
	}

	/**
	 * @return the wear
	 */
	public String getWear() {
		return this.wear.get();
	}

	/**
	 * @param wear the wear to set
	 */
	public void setWear(String wear) {
		this.wear.set(wear);
	}

	/**
	 * @return the rarity
	 */
	public String getRarity() {
		return this.rarity.get();
	}

	/**
	 * @param rarity the rarity to set
	 */
	public void setRarity(String rarity) {
		this.rarity.set(rarity);
	}

	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return this.price.get();
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price.set(price);
	}

	//TODO: ADD BY RARITY,PRICE,ETC WITH ORDER BY;
	
	public List<Item> getItemsByName(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from items where name like '?'");
		pstmt.setString(1, name);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr<1)
			throw new DBException("No items found!");
		else {
			rez.beforeFirst();
			List<Item> list = new ArrayList<Item>();
			while(rez.next()) {	
				Item x = new Item(rez.getInt(1),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),rez.getInt(7));
				list.add(x);
			}
		return list;
		}
	}
	
	public List<Item> getItemsByRarity(String rar) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from items where rarity like '?'");
		pstmt.setString(1, rar);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr<1)
			throw new DBException("No items found!");
		else {
			rez.beforeFirst();
			List<Item> list = new ArrayList<Item>();
			while(rez.next()) {	
				Item x = new Item(rez.getInt(1),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),rez.getInt(7));
				list.add(x);
			}
		return list;
		}
	}
	
	public List<Item> getItemsByName(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from items where name like '?'");
		pstmt.setString(1, name);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr<1)
			throw new DBException("No items found!");
		else {
			rez.beforeFirst();
			List<Item> list = new ArrayList<Item>();
			while(rez.next()) {	
				Item x = new Item(rez.getInt(1),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),rez.getInt(7));
				list.add(x);
			}
		return list;
		}
	}
	
	public List<Item> getItemsByName(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from items where name like '?'");
		pstmt.setString(1, name);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr<1)
			throw new DBException("No items found!");
		else {
			rez.beforeFirst();
			List<Item> list = new ArrayList<Item>();
			while(rez.next()) {	
				Item x = new Item(rez.getInt(1),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),rez.getInt(7));
				list.add(x);
			}
		return list;
		}
	}
	
	public List<Item> getItemsByName(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from items where name like '?'");
		pstmt.setString(1, name);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr<1)
			throw new DBException("No items found!");
		else {
			rez.beforeFirst();
			List<Item> list = new ArrayList<Item>();
			while(rez.next()) {	
				Item x = new Item(rez.getInt(1),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),rez.getInt(7));
				list.add(x);
			}
		return list;
		}
	}
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        if(this.id==-1) {
		    try (Statement stmt = con.createStatement();
		    		ResultSet rs = stmt.executeQuery("select max(id) from items")){
		    	this.id=rs.next() ? rs.getInt(1)+1 : 1;
		    }
        }
        PreparedStatement pstmt = con.prepareStatement("insert into items values(?,'?','?','?','?','?',?,sysdate,sysdate)");
        pstmt.setInt(1, this.id);
        pstmt.setString(2, this.name);
        pstmt.setString(3,this.pclass);
        pstmt.setString(4,this.type);
        pstmt.setString(5,this.wear);
        pstmt.setString(6,this.rarity);
        pstmt.setInt(7, this.price);
        pstmt.executeUpdate();
        con.commit();
	}
	
}
