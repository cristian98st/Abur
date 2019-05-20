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
public class Main {

	public static void main(String[] args) throws SQLException {
		System.out.println(Game.buyGame(22, 221));

//		select * from owned_games where id_owner = '22';

	}

}
