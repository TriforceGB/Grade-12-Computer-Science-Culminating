package UI;

import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * This page allows the user to search for media.
 */
public class searchPanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Constructor for the Search Panel.
	 *
	 * @param ui Reference to the main UI
	 * @param db Reference to the database
	 */
	public searchPanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		// TODO
	}
}
