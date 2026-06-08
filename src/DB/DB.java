package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the public interface for the application to interact with the
 * database.
 */
public class DB {
	// Constant
	private static String[] DB_TABLE = { "Media", "UserData", "Users" };
	// Variables
	private static Connection dbConnect; // Connection to DB

	/**
	 * Init a Connection with an SQLite DB,
	 *
	 * @param path the Path of the DB
	 */
	public DB(String path) {
		String jdbcUrl = "jdbc:sqlite:" + path; // Path to DB
		try {
			dbConnect = DriverManager.getConnection(jdbcUrl); // Connect to DB
			CreateTable(); // Create DB
		} catch (Exception e) {
			System.err.println("Exception while connecting to DB:");
			e.printStackTrace();
		}
	}

	/**
	 * Check Which Table Exist and Create the Tables that don't
	 */
	private void CreateTable() {
		try (Statement stmt = dbConnect.createStatement()) {
			ResultSet rs = stmt.executeQuery(Query.FIND_TABLE);
			// NOTE if we can use the Arrays Lib this can be easier
			int i = 0; // index for Loop
			String[] currentTables = new String[rs.getInt("count")]; // An array of Tables that exist
			while (rs.next()) {
				currentTables[i] = rs.getString("name");
				i++;
			}

		} catch (SQLException e) {
			System.err.println("Exception while checking Tables");
			e.printStackTrace();
		}

	}
}
