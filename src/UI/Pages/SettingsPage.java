package UI.Pages;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import UI.Style;
import UI.UI;

/**
 * The Settings Page Class. Used to display and edit user settings.
 */
public class SettingsPage extends Page {
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
		JButton adminButton = new JButton("Admin Panel");
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

		ImageIcon filal = ui.resizeImg(new ImageIcon("assets/UI/filal.png"), 45, 45);

		// TODO: Button Functionality
		chngUserButton.addActionListener(e -> {
			String changeduser = JOptionPane.showInputDialog("Enter new username"); // Prompt to change username
			if (changeduser.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Cannot have an empty username.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// TODO Database changing code here
		});
		chngPassButton.addActionListener(e -> {
			String changedpass = JOptionPane.showInputDialog("Enter new password"); // Prompt to change the password
			if (changedpass == null) {
				return;
			}
			// Prompt to confirm password
			String chanagedpassconfirm = JOptionPane.showInputDialog("Re-enter password to confirm");
			// checks to see if both entries match, if not then the user will have to retry
			if (!changedpass.equals(chanagedpassconfirm)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (changedpass.isEmpty()) { // checks to see if password field was entered as empty
				JOptionPane.showMessageDialog(this, "Cannot have an empty password.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Changing password code here
		});
		expUserButton.addActionListener(e -> ui.switchPanel("search")); // TODO
		impUserButton.addActionListener(e -> ui.switchPanel("setting")); // TODO
		expMediaButton.addActionListener(e -> ui.logout()); // TODO
		impMediaButton.addActionListener(e -> ui.logout()); // TODO
		delUserButton.addActionListener(e -> {
			// Prompt to confirm account deletion
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?");
			if (confirm == 1) {
				// Check for admin code here (if admin wants to delete account but they're the
				// only admin force them to appoint new admin)
				// Delete code here
			}

		});
		adminButton.addActionListener(e -> ui.logout()); // TODO

		// Stat Panel
		// TODO: Michael pls do this

		buttonPanel.add(chngUserButton);
		buttonPanel.add(chngPassButton);
		buttonPanel.add(expUserButton);
		buttonPanel.add(impUserButton);
		buttonPanel.add(expMediaButton);
		buttonPanel.add(impMediaButton);
		buttonPanel.add(delUserButton);
		buttonPanel.add(adminButton); // TODO (make sure only admins can see this button)

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(300, 250, 300, 850));
		// STILL NEED STAT PANEL

		contentPanel.add(buttonPanel, BorderLayout.CENTER);
		contentPanel.add(statPanel, BorderLayout.LINE_END);

		this.add(contentPanel, BorderLayout.CENTER);
	}
}
