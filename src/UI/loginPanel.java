package UI;

import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.GridLayout;

import DB.DB;
import TableClass.User;

/**
 * This is the Page that displays the login form.
 */
public class loginPanel extends JPanel {
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

		// Set the layout for the panel
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// following a grid layout is 3 items long and 4 rows
		// label on first centered
		// then username label and text field
		// then password label and text field and checkbox for showing password
		// then two buttons for creating new user and logging in
		// all items from left to right described

		// create title label
		JLabel titleLabel = new JLabel(Style.APP_TITLE);
		titleLabel.setFont(Style.TITLE_FONT); // Set the font of the title label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 0; // Row 0
		gbc.gridwidth = 3; // Span across 3 columns
		gbc.insets = new Insets(75, 50, 50, 50); // Add padding around the label

		// add to panel
		this.add(titleLabel, gbc); // Add to panel

		// create username row

		// label for username
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(Style.BASE_FONT); // Set the font of the username label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 1; // Row 1
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(usernameLabel, gbc);

		// text field for username
		JTextField usernameField = new JTextField(20);
		usernameField.setFont(Style.BASE_FONT); // Set the font of the username text field
		gbc.gridx = 1; // Column 1
		gbc.gridy = 1; // Row 1
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(usernameField, gbc);

		// TODO: add extra icon to display whether username available or not

		// create password row

		// label for password
		JLabel pwdLabel = new JLabel("Password:");
		pwdLabel.setFont(Style.BASE_FONT); // Set the font of the password label
		gbc.gridx = 0; // Column 0
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(pwdLabel, gbc);

		// text field for password
		JPasswordField passwordField = new JPasswordField(20);
		passwordField.setFont(Style.BASE_FONT); // Set the font of the password text field
		char pwdEchoChar = (char) 0; // Character to show when password is hidden (0 means no character)
		char pwdVisibleEchoChar = passwordField.getEchoChar(); // Character to show when password is visible (0 means no
																// character)
		gbc.gridx = 1; // Column 1
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(passwordField, gbc);

		// Add checkbox for showing password
		JCheckBox showPwdCheckBox = new JCheckBox("Show Password");
		showPwdCheckBox.setFont(Style.BASE_FONT); // Set the font of the checkbox
		gbc.gridx = 2; // Column 2
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = new java.awt.Insets(0, 0, 5, 50); // Add padding around the checkbox

		// checkbox functionality to show/hide password
		showPwdCheckBox.addActionListener(e -> {
			// whether or not the checkbox is selected (toggled state), show or hide the
			// password
			if (showPwdCheckBox.isSelected()) {
				// we show password by setting the echo char to the visible character (which is
				// the default echo char of the password field)
				passwordField.setEchoChar(pwdEchoChar); // Show the password
			} else {
				// otherwise back to original char
				passwordField.setEchoChar(pwdVisibleEchoChar); // Hide the password
			}
		});

		// Add to panel
		this.add(showPwdCheckBox, gbc);

		// button row
		JPanel nestedBtnPanel = new JPanel(); // Create a nested panel for the buttons to exist where they look nice
		nestedBtnPanel.setLayout(new GridLayout(1, 2, 25, 0));

		// create new user button
		JButton createUserButton = new JButton("Create New User");
		createUserButton.setFont(Style.BASE_FONT);

		createUserButton.addActionListener(e -> {
			ui.switchPanel("createUser"); // Switch to the create user panel when the button is clicked
			// Clear Text Fields
			usernameField.setText("");
			passwordField.setText("");
		});

		// Add to nested panel
		nestedBtnPanel.add(createUserButton);

		// create login button
		JButton loginButton = new JButton("Login");
		loginButton.setFont(Style.BASE_FONT);

		// TODO Get Enter to Login Working
		loginButton.addActionListener(e -> {
			String username = usernameField.getText(); // Get the username from the text field
			String password = new String(passwordField.getPassword()); // Get the password from the password field

			// Tries to Login
			if (ui.login(username, password)) {
				// User is found
				// Clear Text Fields
				usernameField.setText("");
				passwordField.setText("");
			} else { // If User is not found
				usernameField.setBackground(Color.RED);
				passwordField.setBackground(Color.RED);
				// Reset password
				passwordField.setText("");
				JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		// Add to nested panel
		nestedBtnPanel.add(loginButton);

		// add to main panel
		gbc.gridx = 1; // Column 1
		gbc.gridy = 3; // Row 3
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.BTN_PADS; // Add padding around the buttons
		this.add(nestedBtnPanel, gbc);
	}
}
