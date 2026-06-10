package UI;

import java.awt.BorderLayout;
import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * This is the Page that display after Login. It will show a summary of the
 * user's media library.
 */
public class homePanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Create the Home Panel
	 *
	 * @param ui The UI to associate with the panel
	 * @param db The database reference
	 */
	public homePanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		this.setLayout(new BorderLayout(20, 20));
	}
}
