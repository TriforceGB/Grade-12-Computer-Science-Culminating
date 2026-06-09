package UI;

import java.util.EventListener;

import javax.swing.JPanel;

import DB.DB;

/**
 * This is the Page that displays the user's media library.
 */
public class listPanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Create the List Panel
	 *
	 * @param ui The UI to associate with the panel
	 * @param db The database reference
	 */
	public listPanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		
		// filter needs:
		// by type (movie, show, anime) checkboxes
		// by name text field
		// by rating 2x (1-10) spinner (one is max, one is min: if max < min -> min = max) (seperated by a dash)
		// by status (undecided, backlog, watching, completed, dropped) dropdown
		
	}
}
