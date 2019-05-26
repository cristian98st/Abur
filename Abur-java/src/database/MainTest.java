/**
 * 
 */
package database;

import java.sql.SQLException;

/**
 * @author Alex
 *
 */
public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenAlg x = new GenAlg("35");
		try {
			x.getDatesAndValues();
			System.out.println(x.start());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
