package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import DTO.LocalDB.Media;
import DTO.LocalDB.User;

/**
 * This is the public interface for the application to interact with the
 * database.
 */
public class DB {
	// Constant
	private static String[] DB_TABLE = { "User", "Media", "UserData" };
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
						createUser("admin", "admin", true);
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
	 * Execute given query, Only work with normal statement not PreparedStatement.
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

	/**
	 * Finds a User Based on a Given Username and Password.
	 *
	 * @param username the User's username (String)
	 * @param password the User's password (String)
	 * @return a User Object if the username and password match, null otherwise
	 */
	public User login(String username, String password) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.LOGIN_USER)) {
			// Add Values into the Query
			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery(); // Pulls the Query
			if (rs.next()) { // Check if anything was returned
				updateLogin(rs.getInt("id"));
				// Return the Data Format
				return new User(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getBoolean("isAdmin"),
						rs.getString("created"),
						rs.getString("lastLogin"));
			} else {
				// No User Found
				System.err.println("Unable to Find User");
				return null;
			}
		} catch (SQLException e) {
			System.err.println("Exception while logging in:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Update the last login for a User
	 *
	 * @param userId the User's id (int)
	 */
	private void updateLogin(int userId) {
		String lastLogin = LocalDate.now().toString();
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.UPDATE_LOGIN)) {
			stmt.setString(1, lastLogin);
			stmt.setInt(2, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Exception while updating login:");
			e.printStackTrace();
		}
	}

	/**
	 * Create a User into the DB with the Given Username.
	 * Both the Creation Date and Last Login are already set to Today Date
	 *
	 * @param username the User's username (String)
	 * @param password the User's password (String)
	 * @param isAdmin  whether the User is an Admin (boolean)
	 */
	public boolean createUser(String username, String password, boolean isAdmin) {
		// Get Today's Date as Creation
		String created = LocalDate.now().toString();
		String lastLogin = created;

		// Try to add the User to the DB
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.CREATE_USER)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setBoolean(3, isAdmin);
			stmt.setString(4, created);
			stmt.setString(5, lastLogin);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Exception while creating user:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Return a List of Media that match the given filters
	 *
	 * @param userId    the User's id (int)
	 * @param type      the Media type (int)
	 * @param status    the Media status (int)
	 * @param name      the Media name (String)
	 * @param ratingMin the minimum rating (int)
	 * @param ratingMax the maximum rating (int)
	 * @return a List of Media that match the given filters
	 */
	public Media[] findMedia(int userId, int type, int status, String name, int ratingMin, int ratingMax) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.FIND_MEDIA)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, type);
			stmt.setInt(3, status);
			stmt.setString(4, "%" + name + "%");
			stmt.setInt(5, ratingMin);
			stmt.setInt(6, ratingMax);
			ResultSet rs = stmt.executeQuery();
			if (rs.getInt("count") > 0) {
				Media[] foundmeta = new Media[rs.getInt("count")];
				int i = 0;
				while (rs.next()) {
					foundmeta[i] = new Media(
							rs.getInt("id"),
							rs.getInt("type"),
							rs.getInt("externalId"),
							rs.getString("name"),
							rs.getString("description"),
							rs.getString("posterPath"),
							rs.getInt("status"),
							rs.getInt("rating"),
							rs.getInt("lastEpisode"),
							rs.getString("review"),
							rs.getInt("rewatched"));
					i++;
				}
				return foundmeta;
			} else {
				System.err.println("Unable to Find Media with this filter");
				return null;
			}

		} catch (SQLException e) {
			System.err.println("Exception while creating user:");
			e.printStackTrace();
			return null;
		}

	}
}
