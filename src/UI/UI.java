package UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import DB.DB;

/**
 * The main UI class that extends JFrame and manages the application's window
 * and panels.
 * This is where the application connect to the UI
 */
public class UI extends JFrame implements EventListener {
	// Constants
	private static final int WIDTH = 1920; // Width of the window
	private static final int HEIGHT = 1080; // Height of the window
	private static final Color BACKGROUND_COLOR = Color.GRAY; // Background color of the window
	// Variables
	private DB db; // reference to the database

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
		this.setTitle(style.APP_TITLE); // Set the title of the window
		this.setSize(WIDTH, HEIGHT); // Set the size of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
		this.getContentPane().setBackground(BACKGROUND_COLOR); // Set Default Background Color
		this.setResizable(false); // Disable window resizing

		// Initializing Panels
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
	 * This method creates the button header for the UI, This isn't call inside the
	 * class but rather inside the panels of the others
	 *
	 */
	public JPanel createHeader() {
		// TODO
		return null;
	}

	// Controls

	/**
	 * This method logs out the user and returns to the login panel
	 */
	public void logout() {

	}
}
