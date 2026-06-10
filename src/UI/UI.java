package UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.EventListener;

import javax.swing.JFrame;

import DB.DB;
import TableClass.User;

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
	private User currentUser; // Info about the Current Login User

	// Panels
	private CardLayout card; // Layout manager for switching between panels
	private Container panelContainer; // Container that holds the panels
	// Each of these Load a Different Page in the UI
	private loginPanel loginPanel;
	private createUserPanel createUserPanel;
	private homePanel homePanel;
	private listPanel listPanel;
	private searchPanel searchPanel;
	private settingsPanel settingPanel;
	private mediaPanel mediaPanel;
	private adminPanel adminPanel;

	/**
	 * This Create the UI and Display it for the User
	 */
	public UI(DB db) {
		// Taking in the Reference(s)
		this.db = db;

		// Settings
		this.setTitle(Style.APP_TITLE); // Set the title of the window
		this.setSize(WIDTH, HEIGHT); // Set the size of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
		this.getContentPane().setBackground(Style.BACKGROUND_COLOR); // Set Default Background Color
		this.setResizable(false); // Disable window resizing

		// Initializing Panels
		// TODO Edit for Sub Class Refactor
		this.loginPanel = new loginPanel(this, this.db);
		this.createUserPanel = new createUserPanel(this, this.db);
		this.homePanel = new homePanel(this, this.db);
		this.listPanel = new listPanel(this, this.db);
		this.searchPanel = new searchPanel(this, this.db);
		this.settingPanel = new settingsPanel(this, this.db);
		this.mediaPanel = new mediaPanel(this, this.db);
		this.adminPanel = new adminPanel(this, this.db);

		// Setting Up Card layout
		this.panelContainer = getContentPane();
		this.card = new CardLayout();
		this.panelContainer.setLayout(card);

		// Adding Panels to the Card Layout
		this.panelContainer.add(this.loginPanel, "login");
		this.panelContainer.add(this.createUserPanel, "createUser");
		this.panelContainer.add(this.homePanel, "home");
		this.panelContainer.add(this.listPanel, "list");
		this.panelContainer.add(this.searchPanel, "search");
		this.panelContainer.add(this.settingPanel, "setting");
		this.panelContainer.add(this.mediaPanel, "media");
		this.panelContainer.add(this.adminPanel, "admin");

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
	 * @param username The username of the user to create (String)
	 * @param password The password of the user to create (String)
	 * @param isAdmin  Whether the user is an admin (boolean)
	 * @return
	 */
	public boolean createUser(String username, String password, boolean isAdmin) {
		boolean created = db.createUser(username, password, isAdmin);
		if (created) {
			this.switchPanel("login");
		}
		return created;
	}
}
