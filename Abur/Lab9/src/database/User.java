/**
 * 
 */
package database;

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

	
	public User(String name,String pass,String mail,Integer coins) {
		this.username=name;
		this.pass=pass;
		this.mail=mail;
		this.coins=coins;		
	}

}
