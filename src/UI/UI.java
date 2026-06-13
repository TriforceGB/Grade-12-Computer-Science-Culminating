package UI;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import DB.DB;
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


	public ImageIcon resizeImg(ImageIcon original, int width, int height) {
        Image ogImage = original.getImage();
        Image resizedImage = ogImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newicon = new ImageIcon(resizedImage);
		return newicon;
	}

	public void addButtonImg(JButton button, ImageIcon image, int gap, int width, int height) {
		ImageIcon changeIcon = resizeImg(image, width, height);
		button.setIcon(changeIcon);
		button.setHorizontalAlignment(JLabel.RIGHT);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setIconTextGap(gap);
	}
}
