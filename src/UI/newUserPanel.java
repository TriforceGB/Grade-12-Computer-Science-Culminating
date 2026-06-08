package UI;

import javax.swing.JPasswordField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridLayout;
import DB.DB;

public class newUserPanel extends JPanel{
    private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

    /**
	 * Create the Login Panel
	 *
	 * @param ui The UI to associate with the panel
	 * @param db The database reference
	 */
	public newUserPanel(UI ui, DB db) {
        this.ui = ui;
        this.db = db;

        // Set the layout for the panel
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
    }
}
