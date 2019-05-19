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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Alex
 *
 */
public class Item extends RecursiveTreeObject<Item>{

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty pclass = new SimpleStringProperty();
	private StringProperty type = new SimpleStringProperty();
	private StringProperty wear = new SimpleStringProperty();
	private StringProperty rarity = new SimpleStringProperty();
	private IntegerProperty price = new SimpleIntegerProperty();

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
	public void setPclass(String pclas) {
		this.pclass.set(pclas);
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
	
	public List<Item> get(String col,String name,String col2Order,String order) throws SQLException, DBException {
		Connection con = Database.getConnection();
		String columns = "ID,ITEM_NAME,TYPEOF,WEAR,RARITY,CLASS,ADDED_AT,UPDATED_AT";
		String ord = "ASC,DESC";
		List<Item> list = new ArrayList<Item>();
		PreparedStatement pstmt = con.prepareStatement("select * from items where ? like '%?%' order by ? ?");
		if(columns.toLowerCase().contains(col) && !col.contains(",")) {
			pstmt.setString(1, col);
			pstmt.setString(2, name);
			if(ord.toLowerCase().contains(col2Order) && !col2Order.contains(",")) {
				pstmt.setString(3, col2Order);
				pstmt.setString(4, order);
			}
			ResultSet rez = pstmt.executeQuery();
			int rowNr=0;
			while(rez.next())
				rowNr++;
			if(rowNr<1)
				throw new DBException("No items found!");
			else {
				rez.beforeFirst();
				while(rez.next()) {	
					Item x = new Item(rez.getInt(1),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),rez.getInt(7));
					list.add(x);
				}
			}
		}
		return list;
	}
	
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        if(this.id.getValue()==-1) {
		    try (Statement stmt = con.createStatement();
		    		ResultSet rs = stmt.executeQuery("select max(id) from items")){
		    	this.id.set((int) (rs.next() ? rs.getInt(1)+1 : 1));
		    }
        }
        PreparedStatement pstmt = con.prepareStatement("insert into items values(?,?,?,?,?,?,?,sysdate,sysdate)");
        pstmt.setInt(1, this.getId());
//        System.out.print(this.name.get());
        System.out.print(this.pclass.get()+'\n');
        pstmt.setString(2, this.getName());
        pstmt.setString(3, this.getPclass());
        pstmt.setString(4, this.type.get());
        pstmt.setString(5, this.wear.get());
        pstmt.setString(6, this.rarity.get());
        pstmt.setInt(7, this.price.get());
        pstmt.executeUpdate();
	}
	
}
