package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import UI.Style;
import UI.UI;

/**
 * The Settings Page Class. Used to display and edit user settings.
 */
public class SettingsPage extends Page {
	// Variables
	JTextArea statistics;

	private boolean isAdmin = false;

	JButton adminButton;

	public void setAdmin(boolean admin) {
		isAdmin = admin;
		adminButton.setVisible(admin);
	}

	/**
	 * Gets News Stats for when the User Logins
	 *
	 * @param username The Username Name
	 * @param stats    The Stats for the Media and the
	 */
	public void getStats(int[][] stats) {
		statistics.setText("""
				Total Media Tracked: %d

				Media Dropped: %d

				Media Backlogged: %d

				Media Watching: %d

				Media Complete: %d

				Total Media in DB: %d

				Total Movies: %d

				Total Shows: %d
				
				Total Anime: %d
				""".formatted(stats[0][4], stats[0][0], stats[0][1], stats[0][2], stats[0][3], stats[1][3], stats[1][0],
				stats[1][1], stats[1][2]));
	}

	/**
	 * Create the Settings Page
	 *
	 * @param ui The UI object that this page belongs to
	 */
	public SettingsPage(UI ui) {
		super(ui); // Uses the basic page layout and background color

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBackground(this.PageColor);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(this.PageColor);
		JPanel statPanel = new JPanel();
		statPanel.setBackground(this.PageColor);

		// Button Panel
		int hgap = 20;
		int vgap = 20;
		buttonPanel.setLayout(new GridLayout(0, 2, hgap, vgap));

		// Buttons
		JButton chngUserButton = new JButton("Change Username");
		JButton chngPassButton = new JButton("Change Password");
		JButton expUserButton = new JButton("Export User");
		JButton impUserButton = new JButton("Import User");
		JButton expMediaButton = new JButton("Export Media");
		JButton impMediaButton = new JButton("Import Media");
		JButton delUserButton = new JButton("Delete User");
		adminButton = new JButton("Admin Panel");
		chngUserButton.setFocusable(false);
		chngPassButton.setFocusable(false);
		expUserButton.setFocusable(false);
		impUserButton.setFocusable(false);
		expMediaButton.setFocusable(false);
		impMediaButton.setFocusable(false);
		delUserButton.setFocusable(false);
		adminButton.setFocusable(false);

		// Fonts
		chngUserButton.setFont(Style.BASE_FONT_BIG);
		chngPassButton.setFont(Style.BASE_FONT_BIG);
		expUserButton.setFont(Style.BASE_FONT_BIG);
		impUserButton.setFont(Style.BASE_FONT_BIG);
		expMediaButton.setFont(Style.BASE_FONT_BIG);
		impMediaButton.setFont(Style.BASE_FONT_BIG);
		delUserButton.setFont(Style.BASE_FONT_BIG);
		adminButton.setFont(Style.BASE_FONT_BIG);

		// Colours
		chngUserButton.setBackground(Style.LIGHT_GREEN);
		chngUserButton.setForeground(Style.BALTIC_BLUE);
		chngPassButton.setBackground(Style.LIGHT_GREEN);
		chngPassButton.setForeground(Style.BALTIC_BLUE);
		expUserButton.setBackground(Style.LIGHT_GREEN);
		expUserButton.setForeground(Style.BALTIC_BLUE);
		impUserButton.setBackground(Style.LIGHT_GREEN);
		impUserButton.setForeground(Style.BALTIC_BLUE);
		expMediaButton.setBackground(Style.LIGHT_GREEN);
		expMediaButton.setForeground(Style.BALTIC_BLUE);
		impMediaButton.setBackground(Style.LIGHT_GREEN);
		impMediaButton.setForeground(Style.BALTIC_BLUE);
		delUserButton.setBackground(Style.LIGHT_GREEN);
		delUserButton.setForeground(Style.BALTIC_BLUE);
		adminButton.setBackground(Style.LIGHT_GREEN);
		adminButton.setForeground(Style.BALTIC_BLUE);

		// Images

		ui.addButtonImg(chngUserButton, new ImageIcon("assets/UI/changenameicon.png"), 20, 45, 45);
		ui.addButtonImg(chngPassButton, new ImageIcon("assets/UI/changepassicon.png"), 20, 45, 45);
		ui.addButtonImg(expUserButton, new ImageIcon("assets/UI/exportusericon.png"), 40, 45, 45);
		ui.addButtonImg(impUserButton, new ImageIcon("assets/UI/importusericon.png"), 40, 45, 45);
		ui.addButtonImg(expMediaButton, new ImageIcon("assets/UI/exporticon.png"), 20, 45, 45);
		ui.addButtonImg(impMediaButton, new ImageIcon("assets/UI/importicon.png"), 20, 45, 45);
		ui.addButtonImg(delUserButton, new ImageIcon("assets/UI/binicon.png"), 20, 45, 45);
		ui.addButtonImg(adminButton, new ImageIcon("assets/UI/adminicon.png"), 20, 45, 45);

		chngUserButton.addActionListener(e -> {
			String changedUsername = JOptionPane.showInputDialog("Enter new username"); // Prompt to change username
			// Check for Empty Username
			if (changedUsername.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Cannot have an empty username.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Make the Change
			else {
				if (ui.editUsername(changedUsername)) {
					// Change is Made
					JOptionPane.showMessageDialog(this, "Successfully change Username.", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// Change wasn't made
					JOptionPane.showMessageDialog(this, "Unable to change Username, Try again", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		chngPassButton.addActionListener(e -> {
			String changedPass = JOptionPane.showInputDialog("Enter new password"); // Prompt to change the password
			if (changedPass == null) {
				return;
			}
			// Prompt to confirm password
			String changedPassConfirm = JOptionPane.showInputDialog("Re-enter password to confirm");
			// checks to see if both entries match, if not then the user will have to retry
			if (!changedPass.equals(changedPassConfirm)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (changedPass.isEmpty()) { // checks to see if password field was entered as empty
				JOptionPane.showMessageDialog(this, "Cannot have an empty password.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				if (ui.editPassword(changedPass)) {
					// Change is Made
					JOptionPane.showMessageDialog(this, "Successfully change Password.", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// Change wasn't made
					JOptionPane.showMessageDialog(this, "Unable to change Password, Try again", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		expUserButton.addActionListener(e -> {
			if (!ui.exportUser()) {
				JOptionPane.showMessageDialog(this, "Unable to export User, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		impUserButton.addActionListener(e -> {
			if (ui.importUser()) {
				// Change is Made
				JOptionPane.showMessageDialog(this, "Successfully Imported User", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				// Change wasn't made
				JOptionPane.showMessageDialog(this, "Unable to Import User, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		expMediaButton.addActionListener(e -> {
			if (!ui.exportMedia()) {
				JOptionPane.showMessageDialog(this, "Unable to export media, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		impMediaButton.addActionListener(e -> {
			if (ui.importMedia()) {
				// Change is Made
				JOptionPane.showMessageDialog(this, "Successfully Imported Media", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				// Change wasn't made
				JOptionPane.showMessageDialog(this, "Unable to Import Media, Try again", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		delUserButton.addActionListener(e -> {
			// Prompt to confirm account deletion
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?");
			if (confirm == 0) {
				if (ui.deleteUser()) {
					// Change is Made
					JOptionPane.showMessageDialog(this, "Successfully Delete User", "Success",
							JOptionPane.INFORMATION_MESSAGE);
					ui.logout();
				} else {
					// Change wasn't made
					JOptionPane.showMessageDialog(this, "Unable to Delete user, Try again", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		adminButton.addActionListener(e -> ui.switchPanel("adminUsr"));

		// buttonPanel.setPreferredSize(new Dimension(600, 400));
		buttonPanel.add(chngUserButton);
		buttonPanel.add(chngPassButton);
		buttonPanel.add(expUserButton);
		buttonPanel.add(impUserButton);
		buttonPanel.add(expMediaButton);
		buttonPanel.add(impMediaButton);
		buttonPanel.add(delUserButton);
		buttonPanel.add(adminButton);
		adminButton.setVisible(isAdmin);

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, 300, 100));
		// Stat Panel
		statPanel.setLayout(new BorderLayout());
		JLabel statLabel = new JLabel("User Stats");
		statLabel.setFont(Style.BASE_FONT_BIG);
		statLabel.setHorizontalAlignment(JLabel.CENTER);
		statLabel.setPreferredSize(new Dimension(400, 50));
		statLabel.setBackground(Style.BALTIC_BLUE);
		statLabel.setForeground(Style.TEA_GREEN);

		statistics = new JTextArea();
		statistics.setFont(Style.BASE_FONT_BIG);
		statistics.setEditable(false);
		statistics.setPreferredSize(new Dimension(500, 600));
		statistics.setBackground(Style.BORDER_COLOR);
		statistics.setForeground(Style.TEA_GREEN);
		statistics.setBorder(new LineBorder(Color.black));

		JScrollPane statScrollPane = new JScrollPane(statistics);
		statScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		statScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		statScrollPane.setBorder(new LineBorder(Color.BLACK));
		statScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Style.BORDER_COLOR;
				this.trackColor = Style.TEA_GREEN;
			}
		});

		statPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 100));
		statPanel.add(statLabel, BorderLayout.PAGE_START);
		statPanel.add(statScrollPane, BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.CENTER);
		contentPanel.add(statPanel, BorderLayout.LINE_END);

		this.add(contentPanel, BorderLayout.CENTER);
	}

}
