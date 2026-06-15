package UI;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
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

	private boolean loadedMediaPageOnce = false;

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
		// TODO add later one we got Admin Panel working
		// this.adminPanel = new adminPanel(this, this.db);

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
		// this.panelContainer.add(this.adminPanel, "admin");

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
		if (this.currentUser.isAdmin()) {
			JOptionPane.showMessageDialog(this,
					"Cannot Delete a Admin User, Please Have Another Admin Remove Power Before Deletion", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return db.deleteUser(this.currentUser.getId());
		}
	}

	/**
	 * With the Given Object, Add to DB
	 *
	 * @param newMedia New Media from Search to add
	 * @return True if added, False if not
	 */
	public boolean createMedia(Media newMedia) {
		return db.createMedia(newMedia);
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
			boolean isBackLog, boolean isWatching, Boolean isCompleted, String name,
			int ratingMin, int ratingMax) {
		return db.findMedia(this.currentUser.getId(), isMovie, isTV, isAnime, isUndecided, isDropped, isBackLog,
				isWatching, isCompleted, name, ratingMin, ratingMax);
	}

	public Boolean exportMedia() {
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

	public Boolean importMedia() {
		String json = openFile();
		Media[] mediaList = gson.fromJson(json, Media[].class);
		// Throw an error if media is null
		if (mediaList == null) {
			return false;
		}

		for (Media media : mediaList) {
			if (!db.createMedia(media)) {
				System.err.println("Failed to create media: " + media.getName());
			}
		}
		return true;

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
		String result = "<html>"; // result string to return

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
		result += "</html>";

		return result; // return once finished
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
}
