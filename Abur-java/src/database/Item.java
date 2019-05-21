package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.InvalidationListener;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.sql.Types.VARCHAR;

/**
 * @author Alex
 *
 */
public class Item extends RecursiveTreeObject<Item>{

	public StringProperty id = new SimpleStringProperty();
	public StringProperty name = new SimpleStringProperty();
	public StringProperty pclass = new SimpleStringProperty();
	public StringProperty type = new SimpleStringProperty();
	public StringProperty wear = new SimpleStringProperty();
	public StringProperty rarity = new SimpleStringProperty();
	public StringProperty price = new SimpleStringProperty();
	public ObservableValue<JFXButton> buyButton;

	public Item() {
		this.id.set("-1");
	}


	public Item(String id,String name,String pclass,String type,String wear,String rarity,String price) {
		this.id.set(id);
		this.name.set(name);
		this.pclass.set(pclass);
		this.type.set(type);
		this.wear.set(wear);
		this.rarity.set(rarity);
		this.price.set(price);

		this.buyButton = new ObservableValue<JFXButton>() {
			@Override
			public void addListener(ChangeListener<? super JFXButton> listener) {

			}

			@Override
			public void removeListener(ChangeListener<? super JFXButton> listener) {

			}

			@Override
			public JFXButton getValue() {
				JFXButton button = new JFXButton("Buy");
				button.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
				return button;
			}

			@Override
			public void addListener(InvalidationListener listener) {

			}

			@Override
			public void removeListener(InvalidationListener listener) {

			}
		};
	}

	public Item(String name,String pclass,String type,String wear,String rarity,String price) {
		this.id.set("-1");
		this.name.set(name);
		this.pclass.set(pclass);
		this.type.set(type);
		this.wear.set(wear);
		this.rarity.set(rarity);
		this.price.set(price);

		this.buyButton = new ObservableValue<JFXButton>() {
			@Override
			public void addListener(ChangeListener<? super JFXButton> listener) {

			}

			@Override
			public void removeListener(ChangeListener<? super JFXButton> listener) {

			}

			@Override
			public JFXButton getValue() {
				JFXButton button = new JFXButton("Buy");
				button.setStyle("-fx-background-color: -fx-parent; -fx-border-color: -fx-parent; -fx-text-fill: #8f2300");
				return button;
			}

			@Override
			public void addListener(InvalidationListener listener) {

			}

			@Override
			public void removeListener(InvalidationListener listener) {

			}
		};
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return Integer.valueOf(id.get());
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id.set(String.valueOf(id));
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
	 * @param pclas the pclass to set
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
	public Float getPrice() {
		return Float.valueOf(this.price.get());
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price.set(String.valueOf(price));
	}

	public ObservableList<Item> get(String col,String name,String col2Order,String order) throws SQLException, DBException {
		Connection con = Database.getConnection();
		String columns = "id,item_name,typeof,wear,rarity,class,added_at,updated_at";
		String ord = "asc,desc";
		ObservableList<Item> list = FXCollections.observableArrayList();
		Statement pstmt = con.createStatement();
		ResultSet rez;
		if(columns.contains(col) && !col.contains(",")) {
			if(ord.contains(order) && !order.contains(",")) {
				rez = pstmt.executeQuery("select * from items where "+ col+" like '%"+ name +"%' order by "+col2Order +" "+order);
			}
			else
				rez = pstmt.executeQuery("select * from items where "+ col+" like '%"+ name +"%' ");
			while(rez.next()) {
				Item x = new Item(String.valueOf(rez.getInt(1)),rez.getString(2),rez.getString(3),rez.getString(4),rez.getString(5),rez.getString(6),String.valueOf(rez.getInt(7)));
				list.add(x);
			}
		}
		return list;
	}

	public static String buyItem(int buyerID, int itemID) throws SQLException {
		Connection con = Database.getConnection();
		String procedure = "{ call BUY_ITEM(?,?,?)}";
		CallableStatement cs = con.prepareCall(procedure);

		cs.setInt(1,buyerID);
		cs.setInt(2, itemID);
		cs.registerOutParameter(3, VARCHAR);

		cs.execute();

		String result = cs.getString(3);
		con.close();

		return result;
	}

	public List<String> getMyItems(int id) throws SQLException {
		Connection con = Database.getConnection();
		List<String> items = new ArrayList<>();
		Statement stms = con.createStatement();
		ResultSet result = stms.executeQuery("SELECT item_name FROM items JOIN owned_items ON items.id = owned_items.id_item " +
				"WHERE owned_items.id_owner = " + id );

		while(result.next()){
			items.add(result.getString(1));
		}

		return items;
	}

	public void commit() throws SQLException {
		Connection con = Database.getConnection();
		if(this.id.getValue()=="-1") {
			try (Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery("select max(id) from items")){
				this.id.set(String.valueOf((rs.next() ? rs.getInt(1)+1 : 1)));
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
		pstmt.setFloat(7, this.getPrice());
		pstmt.executeUpdate();
	}

}