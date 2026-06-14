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
import DTO.LocalDB.Media.UserData;

/**
 * This is the public interface for the application to interact with the
 * database.
 */
public class DB {
	// Constant
	private static String[] DB_TABLE = { "User", "Media", "UserData" }; // A List of Tables that Should Exist
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
						createUser(new User("admin", "admin", true));
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
	 * Create a User into the DB with the Given Username.
	 * Both the Creation Date and Last Login are already set to Today Date
	 *
	 * @param username the User's username (String)
	 * @param password the User's password (String)
	 * @param isAdmin  whether the User is an Admin (boolean)
	 */
	public boolean createUser(User newUser) {
		// Get Today's Date as Creation
		String created = LocalDate.now().toString();
		String lastLogin = created; // Set Last Login to Creation Date

		// Try to add the User to the DB
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.CREATE_USER)) {
			stmt.setString(1, newUser.getUsername());
			stmt.setString(2, newUser.getPassword());
			stmt.setBoolean(3, newUser.isAdmin());
			stmt.setString(4, created);
			stmt.setString(5, lastLogin);
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while creating user:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Take a given User Object and edits the user to match it
	 *
	 * @param editUser A User Object that has the edited values, the ID of the User
	 *                 should match the User to be edited (User)
	 * @return true if the user was successfully edited, false otherwise
	 */
	public boolean editUser(User editUser) {
		// Create a PreparedStatement to update the User
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.EDIT_USER)) {
			// Values to Change
			stmt.setString(1, editUser.getUsername());
			stmt.setString(2, editUser.getPassword());
			stmt.setBoolean(3, editUser.isAdmin());
			// Matching Base off ID
			stmt.setInt(4, editUser.getId());
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while editing user:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete a User with the Given Id
	 *
	 * @param userId the id of the User to be deleted (int)
	 * @return true if the user was successfully deleted, false otherwise
	 */
	public boolean deleteUser(int userId) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.DELETE_USER)) {
			stmt.setInt(1, userId);
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while deleting user:");
			e.printStackTrace();
			return false;
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

				// Return the Data Format
				return new User(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getBoolean("isAdmin"),
						rs.getString("created"),
						updateLogin(rs.getInt("id")));
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
	private String updateLogin(int userId) {
		String lastLogin = LocalDate.now().toString();
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.UPDATE_LOGIN)) {
			stmt.setString(1, lastLogin);
			stmt.setInt(2, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Exception while updating login:");
			e.printStackTrace();
		}
		return lastLogin; // return today Date
	}

	/**
	 * A Method that finds all User in the DB and return them as an Array
	 *
	 * @return an Array of User Objects
	 */
	public User[] getAllUsers() {
		try (Statement stmt = dbConnect.createStatement()) {
			ResultSet rs = stmt.executeQuery(Query.ALL_USERS);
			User[] UserList = new User[rs.getInt("count")];
			int i = 0;
			while (rs.next()) {
				UserList[i] = new User(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getBoolean("isAdmin"),
						rs.getString("created"),
						rs.getString("lastLogin"));
				i++;
			}
			return UserList;

		} catch (Exception e) {
			System.err.println("Exception While Finding All Users: ");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Add a Media Object into the DB
	 *
	 * @param newMedia the Media to add (Media)
	 * @return true if the Media was added successfully, false otherwise (boolean)
	 */
	public boolean createMedia(Media newMedia) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.CREATE_MEDIA)) {
			stmt.setInt(1, newMedia.getType());
			stmt.setInt(2, newMedia.getExternalId());
			stmt.setString(3, newMedia.getName());
			stmt.setString(4, newMedia.getDescription());
			stmt.setInt(5, newMedia.getEpisodeCount());
			stmt.setString(6, newMedia.getPosterPath());
			stmt.setString(7, newMedia.getPosterLink());
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while creating media:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Edit an Entry with the Given Object
	 *
	 * @param editMedia The Object to Overwrite the Database Entry with
	 * @return True if the Edit was Successful, False Otherwise
	 */
	public boolean editMedia(Media editMedia) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.EDIT_MEDIA)) {
			// Edited Data
			stmt.setInt(1, editMedia.getType());
			stmt.setInt(2, editMedia.getExternalId());
			stmt.setString(3, editMedia.getName());
			stmt.setString(4, editMedia.getDescription());
			stmt.setInt(5, editMedia.getEpisodeCount());
			stmt.setString(6, editMedia.getPosterPath());
			stmt.setString(7, editMedia.getPosterLink());
			// Match Via ID
			stmt.setInt(8, editMedia.getId());
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while editing media:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete a Media Entry
	 *
	 * @param mediaId the Media's id (int)
	 * @return true if the Media was deleted, false otherwise
	 */
	public boolean deleteMedia(int mediaId) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.DELETE_MEDIA)) {
			stmt.setInt(1, mediaId);
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while deleting media:");
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
	public Media[] findMedia(int userId, boolean isMovie, boolean isTV, Boolean isAnime, int status, String name,
			int ratingMin, int ratingMax) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.FIND_MEDIA)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, (isMovie) ? 1 : 0);
			stmt.setInt(3, (isTV) ? 2 : 0);
			stmt.setInt(4, (isAnime) ? 3 : 0);
			stmt.setInt(5, status);
			stmt.setString(6, "%" + name + "%"); // Wild Card of both the left and right side of the name
			stmt.setInt(7, ratingMin - 1); // -1 to include ratingMin in the range
			stmt.setInt(8, ratingMax + 1); // +1 to include ratingMax in the range
			ResultSet rs = stmt.executeQuery();
			if (rs.getInt("count") > 0) {
				Media[] foundMedia = new Media[rs.getInt("count")];
				int i = 0;
				while (rs.next()) {
					foundMedia[i] = new Media(
							rs.getInt("id"),
							rs.getInt("type"),
							rs.getInt("externalId"),
							rs.getString("name"),
							rs.getString("description"),
							rs.getString("posterPath"),
							rs.getString("posterLink"),
							rs.getInt("status"),
							rs.getString("startDate"),
							rs.getString("finishDate"),
							rs.getInt("rating"),
							rs.getInt("lastEpisode"),
							rs.getString("review"),
							rs.getInt("rewatched"));
					i++;
				}
				return foundMedia;
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

	/**
	 * This Export all Media Stored in the database into a Object that will be turn
	 * into a Json by Gson. It can also be used to Just make a list of all Media in
	 * the DB
	 *
	 * @return Media[] array of all Media Stored in the database
	 */
	public Media[] exportMedia() {
		try (Statement stmt = dbConnect.createStatement()) {
			ResultSet rs = stmt.executeQuery(Query.ALL_MEDIA);
			if (rs.getInt("count") > 0) {
				Media[] allMedia = new Media[rs.getInt("count")];
				int i = 0;
				while (rs.next()) {
					allMedia[i] = new Media(
							rs.getInt("id"),
							rs.getInt("type"),
							rs.getInt("externalId"),
							rs.getString("name"),
							rs.getString("description"),
							rs.getString("posterPath"),
							rs.getString("posterLink"));
					i++;
				}
				return allMedia;
			} else {
				System.err.println("Unable to Find Media with this filter");
				return null;
			}

		} catch (SQLException e) {
			System.err.println("Exception while exporting media:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Imports an array of Media into the database
	 *
	 * @param media array of Media to import
	 * @return true if all the import was successful, false otherwise
	 */
	public boolean importMedia(Media[] mediaList) {
		boolean cleanImport = true;
		for (Media media : mediaList) {
			if (!this.createMedia(media)) {
				cleanImport = false; // Return false if any media fails to be added
				System.err.println(
						"Failed to add %s to the Database. Possibility was already there".formatted(media.getName()));
			} else {
				System.out.println("Imported: " + media.getName());
			}
		}
		return cleanImport; // returns if everything imported cleanly
	}

	/**
	 * Create User Data and connect it to the Given User Id and Media ID
	 *
	 * @param userId   The ID of the User (int)
	 * @param mediaId  The ID of the Media (int)
	 * @param userData The Info being added (UserData)
	 * @return
	 */
	public boolean createUserData(int userId, int mediaId, UserData userData) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.CREATE_USERDATA)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, mediaId);
			stmt.setInt(3, userData.getStatus());
			stmt.setString(4, userData.getStartDate());
			stmt.setString(5, userData.getFinishDate());
			stmt.setInt(6, userData.getRating());
			stmt.setInt(7, userData.getLastEpisode());
			stmt.setString(8, userData.getReview());
			stmt.setInt(9, userData.getRewatched());
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false

		} catch (Exception e) {
			System.err.println("Exception while Adding UserData:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Edits a UserData for a Given User and Media
	 *
	 * @param userId   The ID of the Current User (Int)
	 * @param mediaId  The ID of the Media in the Local DB (int)
	 * @param userData The Information that is being edited (UserData)
	 * @return True if Changes could be made, False Otherwise
	 */
	public boolean editUserData(int userId, int mediaId, UserData userData) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.EDIT_USERDATA)) {
			stmt.setInt(1, userData.getStatus());
			stmt.setString(2, userData.getStartDate());
			stmt.setString(3, userData.getFinishDate());
			stmt.setInt(4, userData.getRating());
			stmt.setInt(5, userData.getLastEpisode());
			stmt.setString(6, userData.getReview());
			stmt.setInt(7, userData.getRewatched());
			// Match Based off ID
			stmt.setInt(8, userId);
			stmt.setInt(9, mediaId);
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false

		} catch (Exception e) {
			System.err.println("Exception while Adding UserData:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete a Media Entry
	 *
	 * @param userId  the User's id (int)
	 * @param mediaId the Media's id (int)
	 * @return true if the Media was deleted, false otherwise
	 */
	public boolean deleteUserData(int userId, int mediaId) {
		try (PreparedStatement stmt = dbConnect.prepareStatement(Query.DELETE_USERDATA)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, mediaId);
			int rowsAffected = stmt.executeUpdate(); // Runs Command
			return rowsAffected > 0; // If a row was affected, return true else false
		} catch (SQLException e) {
			System.err.println("Exception while deleting media:");
			e.printStackTrace();
			return false;
		}
	}
}
