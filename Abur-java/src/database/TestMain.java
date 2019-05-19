/**
 * 
 */
package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		try {
			List<Item> z = x.get("item_name", "Wood", "item_name","asc");
			System.out.print(z.get(0).getPrice());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		System.out.print(x.getPclass());
//		try {
//			y.commit();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
