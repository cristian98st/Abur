/**
 * 
 */
package lab9;


import java.sql.SQLException;

import database.Database;


/**
 * @author Alex
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		Connection c=Database.getConnection();
//		String sql= "SELECT * FROM movies";
//		Statement st = c.createStatement();
//		ResultSet r = st.executeQuery(sql);
//		System.out.print(r);
        try {
            PersonController persons = new PersonController();
            MovieController movies = new MovieController();
//            System.out.print("hello");
            persons.create("Francis Ford Coppola");
//            persons.create("Marlon Brando");
//            persons.create("Al Pacino");
//            Database.commit();
//			System.out.print( persons.findByName("Coppola"));
            movies.create("The Godfather", persons.findByName("Coppola"));					
//            ...//Add other movies to the database			
//            Database.commit();            
//            
            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println(e);
            Database.rollback();
        }
	}

}
