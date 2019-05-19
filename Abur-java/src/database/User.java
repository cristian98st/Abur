/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Alex
 *
 */
public class User extends RecursiveTreeObject<User>{
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty pass = new SimpleStringProperty();
	private StringProperty mail = new SimpleStringProperty();
	private IntegerProperty coins = new SimpleIntegerProperty();

	public User() {
		this.id.set(-1);
	}
	
	public User(String name,String pass,String mail,Integer coins) {
		this.id.set(-1);
		this.username.set(name);
		this.pass.set(pass);
		this.mail.set(mail);
		this.coins.set(coins);	
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id.get();
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username.get(); 
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username.set(username);
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return this.pass.get();
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass.set(pass);
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return this.mail.get();
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail.set(mail);
	}

	/**
	 * @return the coins
	 */
	public Integer getCoins() {
		return this.coins.get();
	}

	/**
	 * @param coins the coins to set
	 */
	public void setCoins(Integer coins) {
		this.coins.set(coins);
	}

	
	public void fetchByName(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from accounts where username like '%?%'");
		pstmt.setString(1, name);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr!=1)
			throw new DBException("Too many rows!");
		else {
			rez.beforeFirst();
			rez.next();
			this.id.set(rez.getInt(1));
			this.username.set(rez.getString(2));
			this.pass.set(rez.getString(3));
			this.mail.set(rez.getString(4));
			this.coins.set(rez.getInt(5));
		}
	}
	
	public void fetchByMail(String mail) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from accounts where email like '%?%'");
		pstmt.setString(1, mail);
		ResultSet rez = pstmt.executeQuery();
		int rowNr=0;
		while(rez.next())
			rowNr++;
		if(rowNr!=1)
			throw new DBException("Too many rows!");
		else {
			rez.beforeFirst();
			rez.next();
			this.id.set(rez.getInt(1));
			this.username.set(rez.getString(2));
			this.pass.set(rez.getString(3));
			this.mail.set(rez.getString(4));
			this.coins.set(rez.getInt(5));
		}
	}
	
	public void update() throws SQLException, DBException {
		Connection con = Database.getConnection();
        if(this.id.get()==-1) {
		    throw new DBException("Fetch user first!");
        }
        PreparedStatement pstmt1 = con.prepareStatement("select * from accounts where username = '?'");
		pstmt1.setString(1, this.username.get());
		ResultSet rez = pstmt1.executeQuery();
		rez.next();
		String sql = "update accounts set updated_at = sysdate";
		if(this.username.get() != rez.getString(2))
			sql = sql + " , username = '" + this.username + '\'';
		if(this.pass.get() != rez.getString(3))
			sql = sql + " , pass = '" + this.pass + '\'';
		if(this.mail.get() != rez.getString(4))
			sql = sql + " , email = '" + this.mail + '\'';
		if(this.coins.get() != rez.getInt(5))
			sql = sql + " , coins = " + this.coins;
		PreparedStatement pstmt2 = con.prepareStatement(sql);
		pstmt2.executeUpdate();
        con.commit();
	}
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        if(this.id.get()==-1) {
		    try (Statement stmt = con.createStatement();
		    		ResultSet rs = stmt.executeQuery("select max(id) from accounts")){
		    	this.id.set((int)(rs.next() ? rs.getInt(1)+1 : 1));
		    }
        }
        PreparedStatement pstmt = con.prepareStatement("insert into accounts values(?,?,?,?,?,sysdate,sysdate)");
        pstmt.setInt(1, this.id.get());
        pstmt.setString(2, this.username.get());
        pstmt.setString(3,this.pass.get());
        pstmt.setString(4,this.mail.get());
        pstmt.setInt(5, this.coins.get());
        pstmt.executeUpdate();
	}
}
