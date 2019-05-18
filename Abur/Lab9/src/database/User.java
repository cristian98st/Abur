/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alex
 *
 */
public class User {
	private Integer id;
	private String username;
	private String pass;
	private String mail;
	private Integer coins;

	public User() {
		this.id=-1;
	}
	
	public User(String name,String pass,String mail,Integer coins) {
		this.id=-1;
		this.username=name;
		this.pass=pass;
		this.mail=mail;
		this.coins=coins;		
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the coins
	 */
	public Integer getCoins() {
		return coins;
	}

	/**
	 * @param coins the coins to set
	 */
	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	
	public void setUserByName(String name) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from accounts where username like '?'");
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
			this.id=rez.getInt(1);
			this.username = rez.getString(2);
			this.pass = rez.getString(3);
			this.mail = rez.getString(4);
			this.coins = rez.getInt(5);
		}
	}
	
	public void setUserByMail(String mail) throws SQLException, DBException {
		Connection con = Database.getConnection();
		PreparedStatement pstmt = con.prepareStatement("select * from accounts where email like '?'");
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
			this.id=rez.getInt(1);
			this.username = rez.getString(2);
			this.pass = rez.getString(3);
			this.mail = rez.getString(4);
			this.coins = rez.getInt(5);
		}
	}
	
	public void commit() throws SQLException {
        Connection con = Database.getConnection();
        if(this.id==-1) {
		    try (Statement stmt = con.createStatement();
		    		ResultSet rs = stmt.executeQuery("select max(id) from accounts")){
		    	this.id=rs.next() ? rs.getInt(1)+1 : 1;
		    }
        }
        PreparedStatement pstmt = con.prepareStatement("insert into accounts values(?,'?','?','?',?,sysdate,sysdate)");
        pstmt.setInt(1, this.id);
        pstmt.setString(2, this.username);
        pstmt.setString(3,this.pass);
        pstmt.setString(4,this.mail);
        pstmt.setInt(5, this.coins);
        pstmt.executeUpdate();
        con.commit();
	}
}
