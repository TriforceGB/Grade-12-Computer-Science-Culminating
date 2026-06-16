package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import UI.Style;
import UI.UI;

/**
 * Admin page base for Admin Settings.
 * Other pages in the admin settings will use this as a base.
 */
public class AdminUserPage extends Page {
	public AdminUserPage(UI ui) {
		super(ui);
	}

	@Override
	protected void pageHeader() {
		JPanel header = new JPanel(); // Create the Main Pane
		// Settings for the Header
		header.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); // Sets the Border of the Header
		header.setLayout(new GridLayout(1, 4, 20, 20)); // Set the Layout of the Header
		header.setPreferredSize(new Dimension(0, 50)); // Sets the Size of the Header
		header.setBackground(this.PageColor); // Sets the Color to Match the Page

		// Buttons
		JButton userDbBtn = new JButton("User DB");
		JButton mediaDbBtn = new JButton("Media DB");
		JButton backBtn = new JButton("Back");
		JButton exitBtn = new JButton("Exit");

		// Fonts

		// Colours

		// Images
		ImageIcon userDbIcon = ui.resizeImg(new ImageIcon("assets/UI/userdbicon.png"), 30, 30);
		userDbBtn.setFont(Style.BASE_FONT);
		userDbBtn.setBackground(Style.LIGHT_GREEN);
		userDbBtn.setForeground(Style.BALTIC_BLUE);
		userDbBtn.setIcon(userDbIcon);
		userDbBtn.setHorizontalAlignment(JLabel.RIGHT);
		userDbBtn.setHorizontalAlignment(SwingConstants.CENTER);
		userDbBtn.setVerticalAlignment(SwingConstants.CENTER);
		userDbBtn.setIconTextGap(20);
		userDbBtn.setFocusable(false);

		ImageIcon mediaDbIcon = ui.resizeImg(new ImageIcon("assets/UI/mediadbicon.png"), 30, 30);
		mediaDbBtn.setFont(Style.BASE_FONT);
		mediaDbBtn.setBackground(Style.LIGHT_GREEN);
		mediaDbBtn.setForeground(Style.BALTIC_BLUE);
		mediaDbBtn.setIcon(mediaDbIcon);
		mediaDbBtn.setHorizontalAlignment(JLabel.RIGHT);
		mediaDbBtn.setHorizontalAlignment(SwingConstants.CENTER);
		mediaDbBtn.setVerticalAlignment(SwingConstants.CENTER);
		mediaDbBtn.setIconTextGap(20);
		mediaDbBtn.setFocusable(false);

		ImageIcon backIcon = ui.resizeImg(new ImageIcon("assets/UI/backicon.png"), 30, 30);
		backBtn.setFont(Style.BASE_FONT);
		backBtn.setBackground(Style.LIGHT_GREEN);
		backBtn.setForeground(Style.BALTIC_BLUE);
		backBtn.setIcon(backIcon);
		backBtn.setHorizontalAlignment(JLabel.RIGHT);
		backBtn.setHorizontalAlignment(SwingConstants.CENTER);
		backBtn.setVerticalAlignment(SwingConstants.CENTER);
		backBtn.setIconTextGap(20);
		backBtn.setFocusable(false);

		ImageIcon exiticon = ui.resizeImg(new ImageIcon("assets/UI/exiticon.png"), 30, 30);
		exitBtn.setFont(Style.BASE_FONT);
		exitBtn.setBackground(Style.LIGHT_GREEN);
		exitBtn.setForeground(Style.BALTIC_BLUE);
		exitBtn.setIcon(exiticon);
		exitBtn.setHorizontalAlignment(JLabel.RIGHT);
		exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
		exitBtn.setVerticalAlignment(SwingConstants.CENTER);
		exitBtn.setIconTextGap(20);
		exitBtn.setFocusable(false);

		// Action listener
		userDbBtn.addActionListener(e -> ui.switchPanel("adminUsr"));
		mediaDbBtn.addActionListener(e -> ui.switchPanel("adminMedia"));
		backBtn.addActionListener(e -> ui.switchPanel("settings"));
		exitBtn.addActionListener(e -> ui.logout());

		header.add(userDbBtn);
		header.add(mediaDbBtn);
		header.add(backBtn);
		header.add(exitBtn);

		// add the header to the page
		this.add(header, BorderLayout.NORTH);
	}
}
