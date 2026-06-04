package UI;

import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * This is the Page that displays the login form.
 */
public class loginPanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Create the Login Panel
	 *
	 * @param ui The UI to associate with the panel
	 * @param db The database reference
	 */
	public loginPanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		// TODO
	}
}
