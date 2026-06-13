package DTO.LocalDB;

/**
 * A class that create the Object needed for the Users Table.
 * Useful for Having info on the Current User
 */
public class User {
	private int id;
	private String username;
	private String password;
	private boolean isAdmin;
	private String created;
	private String lastLogin;

	/**
	 * Create the User Object
	 *
	 * @param id        the User's ID (int)
	 * @param username  the User's username (String)
	 * @param password  the User's password (String)
	 * @param isAdmin   whether the User is an Admin (boolean)
	 * @param created   the date the User was created (String)
	 * @param lastLogin the date the User last logged in (String)
	 */
	public User(int id, String username, String password, boolean isAdmin, String created, String lastLogin) {

		this.id = id;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.created = created;
		this.lastLogin = lastLogin;
	}

	/**
	 * A Cut down version of the Object for User Creation
	 *
	 * @param username the User's username (String)
	 * @param password the User's password (String)
	 * @param isAdmin  whether the User is an Admin (boolean)
	 */
	public User(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	// Getters

	/**
	 * Get the User's ID
	 *
	 * @return the User's ID (int)
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the User's username
	 *
	 * @return the User's username (String)
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the User's password
	 *
	 * @return the User's password (String)
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Get whether the User is an Admin
	 *
	 * @return whether the User is an Admin (boolean)
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Get the date the User was created
	 *
	 * @return the date the User was created (String)
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * Get the date the User last logged in
	 *
	 * @return the date the User last logged in (String)
	 */
	public String getLastLogin() {
		return lastLogin;
	}

}
