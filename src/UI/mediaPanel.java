package UI;

import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * This page Display more details about the media.
 */
public class mediaPanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Create the Media Panel
	 *
	 * @param ui Reference to the main UI
	 * @param db Reference to the database
	 */
	public mediaPanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		// TODO
	}
}
