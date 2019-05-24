/**
 * 
 */
package database;

import static java.sql.Types.VARCHAR;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Alex
 *
 */
public class GenAlg {
	private List<Chromosone> population;
	private List<String> dates;
	private List<Float> values;
	private String game_id;
	
	public GenAlg(String y) {
		for(int i=0; i<100; i++) {
			Chromosone x = new Chromosone();
			this.population.add(x);
		}
		this.game_id=y;
	}
	
	public void getDatesAndValues() {
		Connection con = Database.getConnection();
        Statement stms = con.createStatement();
        ResultSet result = stms.executeQuery("SELECT price, FROM games JOIN owned_games ON games.id = owned_games.id_game " +
                "WHERE owned_games.id_owner = " + id);

        while (result.next()) {
            games.add(result.getString(1));
        }

        return games;
	}
	
	
}
