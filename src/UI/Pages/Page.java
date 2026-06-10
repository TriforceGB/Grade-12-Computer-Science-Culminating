package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import UI.Style;
import UI.UI;

/**
 * This is the Parent Class that all Pages are based on
 * Gives the Basic method and basic setup
 * This isn't used for login as that act differently
 */
public class Page extends JPanel {
	// Variables
	protected UI ui; // Reference to the main UI
	protected Color PageColor; // Color of the page background

	/**
	 * Create the base struct of a page. all settings can be edited
	 * using Override in the subclasses.
	 *
	 * @param ui the main UI reference
	 */
	Page(UI ui) {
		this.ui = ui;

		// Set Variables
		this.pageColor();

		// Setting Layout for all Pages
		this.pageLayout();
		this.pageBackground();

		// Adds the Header
		this.pageHeader();
	}

	// Page Variables
	protected void pageColor() {
		this.PageColor = Style.BACKGROUND_COLOR;
	}

	// Page Settings
	// All of these can over Overridden by the subclasses
	/**
	 * This method is called to layout the panel for the page
	 */
	protected void pageLayout() {
		this.setLayout(new BorderLayout(20, 20));
	}

	protected void pageBackground() {
		// TODO Pick A Good Color Scheme
		this.setBackground(this.PageColor);
	}

	/**
	 * This method create the page header and add it to the page
	 */
	protected void pageHeader() {
		JPanel header = new JPanel(); // Create the Main Pane
		// Settings for the Header
		header.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); // Sets the Border of the Header
		header.setLayout(new GridLayout(1, 5, 20, 20)); // Set the Layout of the Header
		header.setPreferredSize(new Dimension(0, 50)); // Sets the Size of the Header
		header.setBackground(this.PageColor); // Sets the Color to Match the Page

		// Buttons
		JButton homeButton = new JButton("Home");
		JButton listButton = new JButton("List");
		JButton searchButton = new JButton("Search");
		JButton settingButton = new JButton("Settings");
		JButton exitButton = new JButton("Exit");

		// Fonts
		homeButton.setFont(Style.BASE_FONT);
		listButton.setFont(Style.BASE_FONT);
		searchButton.setFont(Style.BASE_FONT);
		settingButton.setFont(Style.BASE_FONT);
		exitButton.setFont(Style.BASE_FONT);

		homeButton.addActionListener(e -> ui.switchPanel("home"));
		listButton.addActionListener(e -> ui.switchPanel("list"));
		searchButton.addActionListener(e -> ui.switchPanel("search"));
		settingButton.addActionListener(e -> ui.switchPanel("setting"));
		exitButton.addActionListener(e -> ui.logout());

		header.add(homeButton);
		header.add(listButton);
		header.add(searchButton);
		header.add(settingButton);
		header.add(exitButton);

		// add the header to the page
		this.add(header, BorderLayout.NORTH);
	}
}
