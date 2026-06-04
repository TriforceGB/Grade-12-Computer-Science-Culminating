package UI;

import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * The adminPanel class represents the admin panel of the UI. It able to make
 * major changes to the system. you need to be an admin to use this panel.
 */
class adminPanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Create the Admin Panel
	 *
	 * @param ui The UI to associate with the panel
	 * @param db The database reference
	 */
	public adminPanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		// TODO
	}
}
