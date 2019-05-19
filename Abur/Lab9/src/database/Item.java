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

/**
 * @author Alex
 *
 */
public class Item {

	private Integer id;
	private String name;
	private String pclass;
	private String type;
	private String wear;
	private String rarity;
	private Integer price;

	public Item() {
		this.id=-1;
	}
	

	public Item(Integer id,String name,String pclass,String type,String wear,String rarity,Integer price) {
		this.id=id;
		this.name=name;
		this.pclass=pclass;
		this.type=type;
		this.wear=wear;
		this.rarity=rarity;
		this.price=price;
	}
	
	public Item(String name,String pclass,String type,String wear,String rarity,Integer price) {
		this.id=-1;
		this.name=name;
		this.pclass=pclass;
		this.type=type;
		this.wear=wear;
		this.rarity=rarity;
		this.price=price;
	}

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pclass
	 */
	public String getPclass() {
		return pclass;
	}

	/**
	 * @param pclass the pclass to set
	 */
	public void setPclass(String pclass) {
		this.pclass = pclass;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the wear
	 */
	public String getWear() {
		return wear;
	}

	/**
	 * @param wear the wear to set
	 */
	public void setWear(String wear) {
		this.wear = wear;
	}

	/**
	 * @return the rarity
	 */
	public String getRarity() {
		return rarity;
	}

	/**
	 * @param rarity the rarity to set
	 */
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price = price;
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
