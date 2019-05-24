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
		GenAlg x = new GenAlg("12");
		try {
			x.getDatesAndValues();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
