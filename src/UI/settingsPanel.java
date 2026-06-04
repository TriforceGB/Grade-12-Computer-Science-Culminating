package UI;

import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * This page allows the user to edit settings of the program.
 */
public class settingsPanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Constructor for the searchPanel.
	 *
	 * @param ui Reference to the main UI
	 * @param db Reference to the database
	 */
	public settingsPanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		// TODO
	}
}
