package UI.Pages;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import UI.Style;
import UI.UI;

public class CreateUserPage extends Page {
	// Variables
	GridBagConstraints gbc; // GridBagConstraints for layout
	JTextField usernameField; // Field for Username
	JPasswordField pwdField; // Field for Password
	JPasswordField pwdConfirmField; // Field for Confirm Password

	public CreateUserPage(UI ui) {
		super(ui);
		createTitle();
		createUsernameField();
		createPasswordField();
		createButtons();
	}

	/**
	 * Remove Header from this Page
	 */
	@Override
	protected void pageHeader() {

	}

	/**
	 * Set the layout to GridBagLayout and initialize constraints
	 */
	@Override
	protected void pageLayout() {
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
	}

	/**
	 * Create the title for the Create User Page
	 */
	private void createTitle() {
		JLabel titleLabel = new JLabel(Style.NEW_USER_TITLE);
		titleLabel.setFont(Style.TITLE_FONT); // Set the font of the title label
		titleLabel.setForeground(Style.TEA_GREEN);
		gbc.gridx = 0; // Column 0
		gbc.gridy = 0; // Row 0
		gbc.gridwidth = 3; // Span across 3 columns
		gbc.insets = new Insets(75, 50, 50, 50); // Add padding around the label

		// add to panel
		this.add(titleLabel, gbc); // Add to panel
	}

	/**
	 * Create the username field for the Create User Page
	 */
	private void createUsernameField() {
		// label for username
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(Style.BASE_FONT); // Set the font of the username label
		usernameLabel.setForeground(Style.TEA_GREEN);
		gbc.gridx = 0; // Column 0
		gbc.gridy = 1; // Row 1
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(usernameLabel, gbc);

		// text field for username
		usernameField = new JTextField(20);
		usernameField.setFont(Style.BASE_FONT); // Set the font of the username text field
		usernameField.setFont(Style.BASE_FONT); // Set the font of the username text field
		usernameField.setBackground(Style.TEA_GREEN); // set the background colour of the username text field
		usernameField.setForeground(Style.BALTIC_BLUE); // set the font color of the username text field's text
		usernameField.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		gbc.gridx = 1; // Column 1
		gbc.gridy = 1; // Row 1
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(usernameField, gbc);

		// TODO: add extra icon to display whether username available or not
	}

	/**
	 * Create 2 Password Fields, one for the password and one for the confirm
	 * password
	 */
	private void createPasswordField() {
		// TODO Hide the First Password
		// label for password creation
		JLabel pwdLabel = new JLabel("Password:");
		pwdLabel.setFont(Style.BASE_FONT); // Set the font of the password label
		pwdLabel.setForeground(Style.TEA_GREEN);
		gbc.gridx = 0; // Column 0
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(pwdLabel, gbc);

		// text field for password
		pwdField = new JPasswordField(20);
		pwdField.setFont(Style.BASE_FONT); // Set the font of the password text field
		pwdField.setFont(Style.BASE_FONT); // Set the font of the password text field
		pwdField.setBackground(Style.TEA_GREEN); // set the background colour of the password text field
		pwdField.setForeground(Style.BALTIC_BLUE); // set the font color of the password text field's text
		pwdField.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		gbc.gridx = 1; // Column 1
		gbc.gridy = 2; // Row 2
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(pwdField, gbc);

		// create password row

		// label for password
		JLabel pwdConfirmLabel = new JLabel("Confirm Password:");
		pwdConfirmLabel.setFont(Style.BASE_FONT); // Set the font of the password label
		pwdConfirmLabel.setForeground(Style.TEA_GREEN);
		gbc.gridx = 0; // Column 0
		gbc.gridy = 3; // Row 3
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.LABEL_PADS; // Add padding around the label

		// Add to panel
		this.add(pwdConfirmLabel, gbc);

		// password field for password
		pwdConfirmField = new JPasswordField(20);
		pwdConfirmField.setFont(Style.BASE_FONT); // Set the font of the confirm password text field
		pwdConfirmField.setBackground(Style.TEA_GREEN); // set the background colour of the confirm password text field
		pwdConfirmField.setForeground(Style.BALTIC_BLUE); // set the font color of the confirm password text field's
															// text
		pwdConfirmField.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		char pwdEchoChar = (char) 0; // Character to show when password is hidden (0 means no character)
		// Character to show when password is visible (0 means no character)
		char pwdVisibleEchoChar = pwdConfirmField.getEchoChar();
		gbc.gridx = 1; // Column 1
		gbc.gridy = 3; // Row 3
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.FIELD_PADS; // Add padding around the text field

		// Add to panel
		this.add(pwdConfirmField, gbc);

		// Add checkbox for showing password
		JCheckBox showPwdCheckBox = new JCheckBox("Show Password");
		showPwdCheckBox.setFont(Style.BASE_FONT); // Set the font of the checkbox
		showPwdCheckBox.setBackground(this.PageColor);
		showPwdCheckBox.setForeground(Style.TEA_GREEN);
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
				pwdField.setEchoChar(pwdEchoChar); // Show the password
			} else {
				// otherwise back to original char
				pwdConfirmField.setEchoChar(pwdVisibleEchoChar); // Hide the password
				pwdField.setEchoChar(pwdVisibleEchoChar); // Hide the password
			}
		});

		// Add to panel
		this.add(showPwdCheckBox, gbc);
	}

	/**
	 * Create Buttons To Either Create user or Go Back
	 */
	private void createButtons() {
		JPanel nestedBtnPanel = new JPanel(); // Create a nested panel for the buttons to exist where they look nice
		nestedBtnPanel.setLayout(new GridLayout(1, 2, 25, 0));
		nestedBtnPanel.setBackground(this.PageColor);

		// cancel button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(Style.BASE_FONT);
		cancelButton.setBackground(Style.LIGHT_GREEN);
		cancelButton.setForeground(Style.BALTIC_BLUE);
		ImageIcon xicon = ui.resizeImg(new ImageIcon("assets/UI/xicon.png"), 30, 30);
		cancelButton.setIcon(xicon);
		cancelButton.setHorizontalAlignment(JLabel.RIGHT);
		cancelButton.setHorizontalAlignment(SwingConstants.CENTER);
		cancelButton.setVerticalAlignment(SwingConstants.CENTER);
		cancelButton.setIconTextGap(20);

		cancelButton.addActionListener(e -> {
			ui.switchPanel("login"); // Switch back to the login panel when the cancel button is clicked
			// Clear Text Fields
			usernameField.setText("");
			pwdField.setText("");
			pwdConfirmField.setText("");
		});

		// Add to nested panel
		nestedBtnPanel.add(cancelButton);

		// create login button
		JButton createButton = new JButton("Create User");
		createButton.setFont(Style.BASE_FONT);
		createButton.setBackground(Style.LIGHT_GREEN);
		createButton.setForeground(Style.BALTIC_BLUE);
		ImageIcon plusicon = ui.resizeImg(new ImageIcon("assets/UI/plus.png"), 30, 30);
		createButton.setIcon(plusicon);
		createButton.setHorizontalAlignment(JLabel.RIGHT);
		createButton.setHorizontalAlignment(SwingConstants.CENTER);
		createButton.setVerticalAlignment(SwingConstants.CENTER);
		createButton.setIconTextGap(20);

		createButton.addActionListener(e -> {
			// Check if the password fields match
			if (!new String(pwdField.getPassword()).equals(new String(pwdConfirmField.getPassword()))) { // NOTE This is
																											// Deprecated
				JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
				// Clear Text Fields and set Color Red
				pwdField.setText("");
				pwdConfirmField.setText("");
				// pwdField.setBackground(Color.RED);
				// pwdConfirmField.setBackground(Color.RED);
				return;
			}
			// check if password box is blank
			else if (usernameField.getText().isEmpty() || new String(pwdField.getPassword()).isEmpty()) {
				JOptionPane.showMessageDialog(this, "Cannot leave any fields empty", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Try to Create New User
			if (ui.createUser(usernameField.getText(), new String(pwdField.getPassword()), false)) {
				// Clear Text Fields
				usernameField.setText("");
				pwdField.setText("");
				pwdConfirmField.setText("");
				// Set Color Normal

			} else {
				JOptionPane.showMessageDialog(this, "Unable to Create User, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		});

		// Add to nested panel
		nestedBtnPanel.add(createButton);

		// add to main panel
		gbc.gridx = 1; // Column 1
		gbc.gridy = 4; // Row 4
		gbc.gridwidth = 1; // Span across 1 column
		gbc.insets = Style.BTN_PADS; // Add padding around the buttons
		this.add(nestedBtnPanel, gbc);
	}
}
