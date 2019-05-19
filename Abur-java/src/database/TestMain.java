/**
 * 
 */
package database;

import java.sql.SQLException;

/**
 * @author Alex
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Item x= new Item("mjolnir","asa","hammer","folosit","rar",10);
		User y = new User("Alex","parola123","alex@mail.ro",1000);
		System.out.print(x.getPclass());
		try {
			y.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
