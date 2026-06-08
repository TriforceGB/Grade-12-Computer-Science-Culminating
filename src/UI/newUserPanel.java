package UI;

import javax.swing.JPasswordField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.GridLayout;

import DB.DB;

public class newUserPanel extends JPanel {
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

		// create title label
		JLabel titleLabel = new JLabel(style.NEW_USER_TITLE);
		titleLabel.setFont(style.TITLE_FONT); // Set the font of the title label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 0; // Row 0
		gbc.gridwidth = 3; // Span across 3 columns
		gbc.insets = new Insets(75, 50, 50, 50); // Add padding around the label

		// add to panel
		this.add(titleLabel, gbc); // Add to panel

		// create username row

		// label for username
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(style.BASE_FONT); // Set the font of the username label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 1; // Row 1
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(usernameLabel, gbc);

		// text field for username
		JTextField usernameField = new JTextField(20);
		usernameField.setFont(style.BASE_FONT); // Set the font of the username text field
		gbc.gridx = 1; // Column 1
		gbc.gridy = 1; // Row 1
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(usernameField, gbc);

		// TODO: add extra icon to display whether username available or not
		// NOTE: DB TASK ZACH ON IT

		// create password creation row

		// label for password creation
		JLabel pwdLabel = new JLabel("Password:");
		pwdLabel.setFont(style.BASE_FONT); // Set the font of the password label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(pwdLabel, gbc);

		// text field for password
		JTextField pwdField = new JTextField(20);
		pwdField.setFont(style.BASE_FONT); // Set the font of the password text field
		gbc.gridx = 1; // Column 1
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(pwdField, gbc);

		// create password row

		// label for password
		JLabel pwdConfirmLabel = new JLabel("Confirm Password:");
		pwdConfirmLabel.setFont(style.BASE_FONT); // Set the font of the password label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 3; // Row 3
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(pwdConfirmLabel, gbc);

		// password field for password
		JPasswordField pwdConfirmField = new JPasswordField(20);
		pwdConfirmField.setFont(style.BASE_FONT); // Set the font of the password text field
		char pwdEchoChar = (char) 0; // Character to show when password is hidden (0 means no character)
		// Character to show when password is visible (0 means no character)
		char pwdVisibleEchoChar = pwdConfirmField.getEchoChar();
		gbc.gridx = 1; // Column 1
		gbc.gridy = 3; // Row 3
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(pwdConfirmField, gbc);

		// Add checkbox for showing password
		JCheckBox showPwdCheckBox = new JCheckBox("Show Password");
		showPwdCheckBox.setFont(style.BASE_FONT); // Set the font of the checkbox
		gbc.gridx = 2; // Column 2
		gbc.gridy = 3; // Row 3
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = new java.awt.Insets(0, 0, 5, 50); // Add padding around the checkbox

		// checkbox functionality to show/hide password
		showPwdCheckBox.addActionListener(e -> {
			// whether or not the checkbox is selected (toggled state), show or hide the
			// password
			if (showPwdCheckBox.isSelected()) {
				// we show password by setting the echo char to the visible character (which is
				// the default echo char of the password field)
				pwdConfirmField.setEchoChar(pwdEchoChar); // Show the password
			} else {
				// otherwise back to original char
				pwdConfirmField.setEchoChar(pwdVisibleEchoChar); // Hide the password
			}
		});

		// Add to panel
		this.add(showPwdCheckBox, gbc);

		// button row
		JPanel nestedBtnPanel = new JPanel(); // Create a nested panel for the buttons to exist where they look nice
		nestedBtnPanel.setLayout(new GridLayout(1, 2, 25, 0));

		// create new user buttom
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(style.BASE_FONT);

		cancelButton.addActionListener(e -> {
			ui.switchPanel("login"); // Switch back to the login panel when the cancel button is clicked
		});

		// Add to nested panel
		nestedBtnPanel.add(cancelButton);

		// create login button
		JButton createButton = new JButton("Create User");
		createButton.setFont(style.BASE_FONT);

		createButton.addActionListener(e -> {
			// TODO Create USER
		});

		// Add to nested panel
		nestedBtnPanel.add(createButton);

		// add to main panel
		gbc.gridx = 1; // Column 1
		gbc.gridy = 4; // Row 4
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = style.BTN_PADS; // Add padding around the buttons
		this.add(nestedBtnPanel, gbc);
	}
}
