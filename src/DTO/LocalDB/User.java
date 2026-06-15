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

	// Extra
	private Media[] mediaRelation; // Stores all Media that is connected to User (Ie they have a relationship with
									// it because of UserData)

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

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Media[] getMediaRelation() {
		return mediaRelation;
	}

	public void setMediaRelation(Media[] mediaRelation) {
		this.mediaRelation = mediaRelation;
	}
}
