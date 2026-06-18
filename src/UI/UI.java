package UI;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import API.API;
import DB.DB;
import DTO.LocalDB.Media;
import DTO.LocalDB.User;
import DTO.LocalDB.Media.UserData;
import UI.Pages.*;

/**
 * The main UI class that extends JFrame and manages the application's window
 * and panels.
 * This is where the application connect to the UI
 */
public class UI extends JFrame implements EventListener {
	// Constants
	private static final int WIDTH = 1920; // Width of the window
	private static final int HEIGHT = 1080; // Height of the window
	// Variables
	private DB db; // reference to the database
	private API api; // reference to the database
	private Gson gson; // reference to the Gson library

	private User currentUser; // Info about the Current Login User

	// Panels
	private CardLayout card; // Layout manager for switching between panels
	private Container panelContainer; // Container that holds the panels
	// Each of these Load a Different Page in the UI
	private LoginPage loginPage;
	private CreateUserPage createUserPage;
	private HomePage homePage;
	private ListPage listPage;
	private SearchPage searchPage;
	private SettingsPage settingPage;
	private MediaPage mediaPage;
	private AdminUserPage adminUsrPage;
	private AdminMediaPage adminMediaPage;

	private boolean loadedMediaPageOnce = false;
	private boolean loadedAdminUserPage = false;
	private boolean loadedAdminMediaPage = false;

	/**
	 * This Create the UI and Display it for the User
	 */
	public UI(DB db, API api, Gson gson) {
		// Taking in the Reference(s)
		this.db = db;
		this.api = api;
		this.gson = gson;

		// Settings
		this.setTitle(Style.APP_TITLE); // Set the title of the window
		this.setSize(WIDTH, HEIGHT); // Set the size of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
		this.getContentPane().setBackground(Style.BALTIC_BLUE); // Set Default Background Color
		this.setResizable(false); // Disable window resizing

		// Initializing Panels
		this.loginPage = new LoginPage(this);
		this.createUserPage = new CreateUserPage(this);
		this.homePage = new HomePage(this);
		this.listPage = new ListPage(this);
		this.searchPage = new SearchPage(this);
		this.settingPage = new SettingsPage(this);
		this.mediaPage = new MediaPage(this);
		this.adminUsrPage = new AdminUserPage(this);
		this.adminMediaPage = new AdminMediaPage(this); // Gets Show User has Selected

		// Setting Up Card layout
		this.panelContainer = getContentPane();
		this.card = new CardLayout();
		this.panelContainer.setLayout(card);

		// Adding Panels to the Card Layout
		this.panelContainer.add(this.loginPage, "login");
		this.panelContainer.add(this.createUserPage, "createUser");
		this.panelContainer.add(this.homePage, "home");
		this.panelContainer.add(this.listPage, "list");
		this.panelContainer.add(this.searchPage, "search");
		this.panelContainer.add(this.settingPage, "setting");
		this.panelContainer.add(this.mediaPage, "media");
		this.panelContainer.add(this.adminUsrPage, "adminUsr");
		this.panelContainer.add(this.adminMediaPage, "adminMedia");

		this.card.show(this.panelContainer, "login"); // Show the Login Panel by Default

		this.setVisible(true); // Display the Window
	}

	/**
	 * This method allows switching between panels in the UI
	 *
	 * @param panelName The name of the panel to switch to (e.g., "login", "home",
	 *                  etc.)
	 */
	public void switchPanel(String panelName) {
		this.card.show(this.panelContainer, panelName);

		if (panelName.equals("list") && !loadedMediaPageOnce) {
			loadedMediaPageOnce = true;
			listPage.addDefaultListToTable();
		}
		if (panelName.equals("home")) {
			homePage.createWidgets();
		}
		if (panelName.equals("adminUsr") && !loadedAdminUserPage) {
			loadedAdminUserPage = true;
			adminUsrPage.loadData();
		}
		if (panelName.equals("adminMedia") && !loadedAdminMediaPage) {
			loadedAdminMediaPage = true;
			adminMediaPage.loadData();
		}
	}

	public void openMediaPage(Media ref, String panelNameCalledFrom) {
		// setup page
		mediaPage.setupMediaPanel(ref, panelNameCalledFrom);
		// then swap
		switchPanel("media");
	}

	/**
	 * This method logs in the user and sends them into the homepage if the account
	 * exists
	 *
	 * @param username The username of the user (String)
	 * @param password The password of the user (String)
	 */
	public boolean login(String username, String password) {
		this.currentUser = db.login(username, password);
		if (this.currentUser != null) {
			this.switchPanel("home");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method logs out the user and returns to the login panel
	 */
	public void logout() {
		// Remove Current user
		this.currentUser = null;
		this.switchPanel("login");
	}

	/**
	 * Is a Shell for the create User Method for DB
	 *
	 * @param newUser a User object that will be enter into the DB
	 * @return if the Change was Made
	 */
	public boolean createUser(User newUser) {
		boolean created = db.createUser(newUser);
		if (created) {
			this.switchPanel("login");
		}
		return created;
	}

	/**
	 * Edit the Current User Username
	 *
	 * @param newUsername The New Username (String)
	 * @return True if Changed on DB, False Otherwise
	 */
	public boolean editUsername(String newUsername) {
		this.currentUser.setUsername(newUsername); // Change Username on the Object
		return db.editUser(this.currentUser); // Change the Username on the DB
	}

	/**
	 * Edit the Current User Password
	 *
	 * @param newPassword The New Password (String)
	 * @return True if Changed on DB, False Otherwise
	 */
	public boolean editPassword(String newPassword) {
		this.currentUser.setPassword(newPassword); // Change Username on the Object
		return db.editUser(this.currentUser); // Change the Username on the DB
	}

	/**
	 * Delete the Current User. Only works if your Not an Admin
	 *
	 * @return True if Changed on DB, False Otherwise
	 */
	public boolean deleteUser() {
		// Check if User is Admin
		if (this.currentUser.getIsAdmin()) {
			JOptionPane.showMessageDialog(this,
					"Cannot Delete a Admin User, Please Have Another Admin Remove Power Before Deletion", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return db.deleteUser(this.currentUser.getId());
		}
	}

	/**
	 * Pulls all Users from the DB
	 *
	 * @return An array of all Users
	 */
	public User[] pullUsers() {
		return db.getAllUsers();
	}

	/**
	 * Pulls all Media from DB
	 *
	 * @return An array of all Media
	 */
	public Media[] pullMedia() {
		return db.exportMedia();
	}

	/**
	 * With the Given Object, Add to DB
	 *
	 * @param newMedia New Media from Search to add
	 * @return True if added, False if not
	 */
	public boolean createMedia(Media newMedia) {
		boolean dbResult = db.createMedia(newMedia);
		boolean apiResult = api.downloadImage(newMedia);
		return dbResult && apiResult;
	}

	/**
	 * Shell for Find Media
	 *
	 * @param isMovie   Can the Media a Movie (bool)
	 * @param isTV      Can the Media a TV (bool)
	 * @param isAnime   Can the Media a Anime (bool)
	 * @param status    the Media status (int)
	 * @param name      the Media name (String)
	 * @param ratingMin the minimum rating (int)
	 * @param ratingMax the maximum rating (int)
	 * @return a List of Media that match the given filters
	 */
	public Media[] findMedia(boolean isMovie, boolean isTV, boolean isAnime, boolean isUndecided, boolean isDropped,
			boolean isBackLog, boolean isWatching, boolean isCompleted, String name,
			int ratingMin, int ratingMax) {
		return db.findMedia(this.currentUser.getId(), isMovie, isTV, isAnime, isUndecided, isDropped, isBackLog,
				isWatching, isCompleted, name, ratingMin, ratingMax);
	}

	/**
	 * Export All the Media in a DB onto a Json File
	 *
	 * @return true if the export was successful, false otherwise
	 */
	public boolean exportMedia() {
		Media[] media = db.exportMedia();
		// Throw an error if media is null
		if (media == null) {
			return false;
		}
		// Create a New Json
		String json = gson.toJson(media);
		saveFile("Media_Export", json);
		return true;
	}

	/**
	 * Import Media From a Json onto the DB
	 *
	 * @return True if the import was successful, false otherwise
	 */
	public boolean importMedia() {
		String json = openFile();
		Media[] mediaList;
		try {
			mediaList = gson.fromJson(json, Media[].class);
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Throw an error if media is null
		}

		for (Media media : mediaList) {
			if (!db.createMedia(media) || !api.downloadImage(media)) {
				System.err.println("Failed to create media: " + media.getName());
			}
		}
		return true;
	}

	/**
	 * Export the Current User onto a Json File
	 *
	 * @return true if the export was successful, false otherwise
	 */
	public boolean exportUser() {
		Media[] media = db.exportUserRelation(this.currentUser.getId()); // Pull all Media and UserData Related to User
		// Add Media to User
		this.currentUser.setMediaRelation(media);
		// Create a New Json
		String json = gson.toJson(this.currentUser);
		saveFile("User_Export", json);
		return true;
	}

	/**
	 * Taken in a Json and Create a user Base off that. Import all the show they
	 * have Userdata connected too
	 *
	 * @return If the User was Imported
	 */
	public boolean importUser() {
		String json = openFile();
		User newUser;

		try {
			newUser = gson.fromJson(json, User.class);
		} catch (Exception e) {
			e.printStackTrace(); // Throw Error if Not a Valid User
			return false;
		}

		newUser.setAdmin(false); // Imported User are Not Admins by default

		// Add user to DB
		if (!db.createUser(newUser)) {
			return false;
		}

		// Recreate the User with for the new ID
		Media[] mediaRelation = newUser.getMediaRelation();
		newUser = db.login(newUser.getUsername(), newUser.getPassword());

		// Add Media that Relate to User
		for (Media media : mediaRelation) {
			if (!db.createMedia(media) || !api.downloadImage(media)) {
				System.err.println("Failed to create media: " + media.getName());
			}

			// Recreate the media with the new ID
			UserData userData = media.getUserData();
			media = db.locateMedia(media.getName(), media.getType(), media.getExternalId());

			// Add UserDate to DB
			if (!db.createUserData(newUser.getId(), media.getId(), userData)) {
				System.err.println("Failed to create user media relation: " + media.getName());
			}
		}
		return true;
	}

	/**
	 * Gets Review for a Media
	 *
	 * @param mediaId The ID of the Media to get reviews for
	 * @return A 2D array of reviews, where each row is a review and each column is
	 *         a review field
	 */
	public String[][] pullReview(int mediaId) {
		String[][] reviews = db.UserReview(mediaId);
		return reviews;
	}

	/**
	 * Pulls the user's stats from the DB and returns them as a 2D array
	 *
	 * @return A 2D array of user stats, where each row is a stat and each column is
	 *         a stat field. The format is as follows: UserStats,
	 *         MediaStats
	 */
	public int[][] pullStats() {
		int[] userData = db.getUserStats(this.currentUser.getId());
		int[] mediaData = db.getMediaStats();

		int[][] stats = { userData, mediaData };
		return stats;

	}

	/**
	 * Finds the Media and return it from the DB. Useful for Getting its ID
	 *
	 * @param refMedia The Media to locate
	 * @return The located Media, or null if not found
	 */
	public Media locateMedia(Media refMedia) {
		Media locatedMedia = db.locateMedia(refMedia.getName(), refMedia.getType(), refMedia.getExternalId());
		return locatedMedia;
	}

	public boolean createUserData(int mediaId, UserData userData) {
		return db.createUserData(this.currentUser.getId(), mediaId, userData);
	}

	public boolean editUserData(int mediaId, UserData userData) {
		return db.editUserData(this.currentUser.getId(), mediaId, userData);
	}

	public boolean deleteUserData(int mediaId) {
		return db.deleteUserData(this.currentUser.getId(), mediaId);
	}

	/**
	 * Handle the Logic for if to Edit or Create or Remove Status
	 *
	 * @param newStatus The new status to set
	 * @param refMedia  The Media to edit
	 * @return If the Status was Edited
	 */
	public boolean editStatus(int newStatus, Media refMedia) {
		boolean change = false;
		String startDate = null;
		String finishDate = null;
		int episodeCount = 0;
		if (refMedia.getStatus() == 0 && newStatus == 0) { // No Change Needed
			change = true;
		} else if (refMedia.getStatus() == 0 && newStatus != 0) { // Create New Status
			// Add Start Date and Finish Date
			startDate = "yyyy-mm-dd";
			finishDate = "yyyy-mm-dd";
			if (newStatus == 3) { // Watching == Set Start Date
				startDate = LocalDate.now().toString();
			} else if (newStatus == 4) { // Finished == Set Finish Date
				finishDate = LocalDate.now().toString();
				episodeCount = refMedia.getEpisodeCount();
			}
			change = db.createUserData(this.currentUser.getId(), refMedia.getId(),
					new UserData(newStatus, startDate, finishDate, 0, episodeCount, "", 0));
		} else if (refMedia.getStatus() != 0 && newStatus != 0) { // Update Existing Status
			// Add Start Date and Finish Date
			if (newStatus == 3) { // Watching == Set Start Date
				startDate = LocalDate.now().toString();
			} else if (newStatus == 4) { // Finished == Set Finish Date
				finishDate = LocalDate.now().toString();
				episodeCount = refMedia.getEpisodeCount();
			}
			change = db.editUserData(this.currentUser.getId(), refMedia.getId(),
					new UserData(newStatus, startDate, finishDate, 0, episodeCount, "", 0));
		} else if (refMedia.getStatus() != 0 && newStatus == 0) { // No Change Needed
			change = db.deleteUserData(this.currentUser.getId(), refMedia.getId());
		}

		refMedia.setStatus(newStatus);
		return change;
	}

	// API Shells

	/**
	 * Just a Shell for the searchMovie Method in API
	 *
	 * @param query  The Query for the Show
	 * @param amount The Number of Show to Return
	 * @return A Media Array
	 */
	public Media[] searchMovie(String query, int amount) {
		return this.api.searchMovie(query, amount);
	}

	/**
	 * Just a Shell for the searchShow Method in API
	 *
	 * @param query  The Query for the Show
	 * @param amount The Number of Show to Return
	 * @return A Media Array
	 */
	public Media[] searchShow(String query, int amount) {
		return this.api.searchShow(query, amount);
	}

	/**
	 * Just a Shell for the searchAnime Method in API
	 *
	 * @param query  The Query for the Show
	 * @param amount The Number of Show to Return
	 * @return A Media Array
	 */
	public Media[] searchAnime(String query, int amount) {
		return this.api.searchAnime(query, amount);
	}

	// Image and Other UI Methods
	public ImageIcon resizeImg(ImageIcon original, int width, int height) {
		Image ogImage = original.getImage();
		Image resizedImage = ogImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(resizedImage);
		return newIcon;
	}

	public void addButtonImg(JButton button, ImageIcon image, int gap, int width, int height) {
		ImageIcon changeIcon = resizeImg(image, width, height);
		button.setIcon(changeIcon);
		button.setHorizontalAlignment(JLabel.RIGHT);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setIconTextGap(gap);
	}

	/**
	 * @param toFormat The string of text (without line breaks) to be formatted
	 * @param cPerLine The number of characters to place on each line. Will
	 *                 overshoot depedent on word size, so account for a little
	 *                 extra rooom.
	 * @param maxPass  The amount of wiggle room there is around the cPerLine. If
	 *                 doesn't fit cPerLine but fits within cPerLine + maxPass then
	 *                 will add word to current line
	 * @return The string of text formatted via html with line breaks at parts
	 *         attempting to match cPerLine, but based on number of words
	 */
	public String getHtmlFormatText(String toFormat, int cPerLine, int maxPass) {
		String[] words = toFormat.split(" "); // split @ each space for each word
		String result = "<html><body style='width: 300px;'>"; // result string to return

		int tracker = 0; // tracks current line number of chars
		for (String word : words) {
			int wordLength = word.length();
			if ((tracker + wordLength) > cPerLine) { // exceeds cPerLine
				if ((tracker + wordLength) < (cPerLine + maxPass)) { // but doesn't exceed the maximun pass range
					// add word but prep for next line instead
					tracker = 0;
					result += word + "<br>"; // add break line for html
				} else {
					// simply add to next line
					tracker = wordLength + 1; // accounting for space
					result += "<br>" + word + " "; // new line, then word, then a space
				}
			} else { // doesn't exceed cPerLine so add as normal
				tracker += wordLength + 1; // add length of word and space as well
				result += word + " "; // add word with a space
			}
		}
		// when done append ending html
		result += "</body></html>";

		return result; // return once finished
	}

	public String getRawTextFromHtmlFormat(String fromHtml) {
		String result = fromHtml;

		result = result.replace("<br>", " ");
		result = result.replace("<html>", "");
		result = result.replace("</html>", "");

		return result;
	}

	// File Import and Export
	/**
	 * Prop the User to Pick a Location for a File to Save
	 *
	 * @param title The title of the file to save
	 * @param body  The body of the file to save
	 */
	public void saveFile(String title, String body) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Where Do You Want to Save?");

		// Make Sure it Export as a JSON
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
		fileChooser.setFileFilter(filter);
		// Set Default File Name
		fileChooser.setSelectedFile(new File(title + ".json"));

		// Show Save Dialog
		int result = fileChooser.showSaveDialog(this);
		// If they Pick a Location
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile(); // Create a File at that Location

			// Add .json extension if not present
			if (!file.getName().endsWith(".json")) {
				file = new File(file.getParent(), file.getName() + ".json");
			}

			// Write to the File
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(body);

				// Show Success Message
				JOptionPane.showMessageDialog(this, "Successfully Saved File", "Success",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				// Show Error Message
				JOptionPane.showMessageDialog(this, "Unable to Save File, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}
	}

	/**
	 * Opens a Pop up that will let the User Select a Json.
	 *
	 * @return Returns the Content of the File as String
	 */
	public String openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a Json to Import");

		// Make Sure it Export as a JSON
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
		fileChooser.setFileFilter(filter);

		// Show Open Dialog
		int result = fileChooser.showOpenDialog(this);
		// If they Pick a Location
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile(); // Create a File at that Location

			// Read the File
			try {
				String output = Files.readString(file.toPath());
				return output;

			} catch (Exception e) {
				// Show Error Message
				JOptionPane.showMessageDialog(this, "Unable to Open File, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}
		return null;
	}

	public boolean isAdmin() {
		System.out.println(this.currentUser.getIsAdmin());
		return this.currentUser.getIsAdmin();
	}

	public void showAdmin(boolean admin) {
		this.settingPage.setAdmin(admin);
	}

	public void setStats() {
		this.settingPage.getStats(this.pullStats());
	}

	/**
	 * Create the Homepage After the User Logs-in
	 */
	public void createHomePage() {
		this.homePage.createWidgets();
	}

	public String getMovieTypeFromInt(int movieType) {
		switch (movieType) {
			case 1:
				return "Movie";
			case 2:
				return "TV Show";
			case 3:
				return "Anime";
			default:
				return "N/A";
		}
	}

	public String getStatusString(int status) {
		switch (status) {
			case 0:
				return "Undecided";
			case 1:
				return "Dropped";
			case 2:
				return "Backlog";
			case 3:
				return "Watching";
			case 4:
				return "Completed";
			default:
				return "Unknown";
		}
	}
}
