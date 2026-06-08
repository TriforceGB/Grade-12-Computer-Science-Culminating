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
	private static String[] DB_TABLE = { "Users", "Media", "UserData" };
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
			checkTable(); // Create DB
		} catch (Exception e) {
			System.err.println("Exception while connecting to DB:");
			e.printStackTrace();
		}
	}

	/**
	 * Check Which Table Exist and Create the tables that doesn't
	 */
	private void checkTable() {
		int i = 0; // index for Loop
		String[] currentTables; // An array of tables that exist

		// Query for Table List
		try (Statement stmt = dbConnect.createStatement()) {
			ResultSet rs = stmt.executeQuery(Query.FIND_TABLE);

			currentTables = new String[rs.getInt("count")];

			// Finds all Table that exist
			while (rs.next()) {
				currentTables[i] = rs.getString("name");
				i++;
			}
		} catch (SQLException e) {
			System.err.println("Exception while checking Tables");
			e.printStackTrace();
			return;
		}

		// NOTE if we can use the Arrays Lib this can be easier
		// Check if a needed table exist
		for (int j = 0; j < DB_TABLE.length; j++) {
			boolean exists = false;
			for (int k = 0; k < currentTables.length; k++) {
				if (DB_TABLE[j].equals(currentTables[k])) {
					exists = true;
				}
			}

			// If it doesn't Create it
			if (!exists) {
				// Create the Correct Table
				switch (j) {
					case 0: // Users Table
						executeCommand(Query.CREATE_USERS_TABLE);
						break;
					case 1: // Media Table
						executeCommand(Query.CREATE_MEDIA_TABLE);
						break;
					case 2: // UserData Table
						executeCommand(Query.CREATE_USERDATA_TABLE);
						break;
				}
			}
		}
	}

	/**
	 * execute given query, Only work with normal statement not PreparedStatement.
	 * Does not Return anything, just runs the Query.
	 * Used for making table and other commands
	 *
	 * @param command the given command
	 */
	private void executeCommand(String command) {
		try (Statement stmt = dbConnect.createStatement()) {
			stmt.execute(command); // Runs the Query
		} catch (SQLException e) {
			System.err.println("Exception while Running: ");
			System.err.println(command);
			e.printStackTrace();
		}
	}
}
