package UI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JButton;
import DB.DB;

/**
 * This page allows the user to edit settings of the program.
 */
public class settingsPanel extends JPanel {
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
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel nestedBtnPanel = new JPanel();
		nestedBtnPanel.setLayout(new GridLayout(4, 2, 25, 0));

		// Change username btn
		JButton changeUsernameButton = new JButton("Change Username");
		changeUsernameButton.setFont(style.BASE_FONT);

		changeUsernameButton.addActionListener(e -> { 
			// TODO: update current user's username in the database and update the UI accordingly
		});

		// Add to nested panel
		nestedBtnPanel.add(changeUsernameButton);

		// Change password btn
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setFont(style.BASE_FONT);

		changePasswordButton.addActionListener(e -> {
			// TODO: update current user's password in the database and update the UI accordingly
		});

		// Add to nested panel
		nestedBtnPanel.add(changePasswordButton);

		// Export user btn
		JButton exportUserButton = new JButton("Export User");
		exportUserButton.setFont(style.BASE_FONT);

		exportUserButton.addActionListener(e -> {
			// TODO: export current user's data from the database
		});

		// Add to nested panel
		nestedBtnPanel.add(exportUserButton);

		// Import user btn
		JButton importUserButton = new JButton("Import User");
		importUserButton.setFont(style.BASE_FONT);

		importUserButton.addActionListener(e -> {
			// TODO: import user data into the database and update the UI accordingly
		});

		// Add to nested panel
		nestedBtnPanel.add(importUserButton);

		// Export media btn
		JButton exportMediaButton = new JButton("Export Media");
		exportMediaButton.setFont(style.BASE_FONT);

		exportMediaButton.addActionListener(e -> {
			// TODO: export media data from the database
		});

		// Add to nested panel
		nestedBtnPanel.add(exportMediaButton);

		// Import media btn
		JButton importMediaButton = new JButton("Import Media");
		importMediaButton.setFont(style.BASE_FONT);

		importMediaButton.addActionListener(e -> {
			// TODO: import media data into the database and update the UI accordingly
		});

		// Add to nested panel
		nestedBtnPanel.add(importMediaButton);

		// Delete user btn
		JButton deleteUserButton = new JButton("Delete User");
		deleteUserButton.setFont(style.BASE_FONT);

		deleteUserButton.addActionListener(e -> {
			// TODO: delete current user from the database and return to the login panel
		});

		// Add to nested panel
		nestedBtnPanel.add(deleteUserButton);

		

		this.add(nestedBtnPanel, gbc);
	}
}
