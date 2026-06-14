package UI.Pages;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import UI.Style;
import UI.UI;

/**
 * The Home Page Class. Used to Create the Main Page of the UI.
 * This will display some basic information about the user and their media.
 */
public class HomePage extends Page {
	// Variables
	private JPanel contentPanel;
	private JPanel backlogpanel = new JPanel();
	private JPanel currentpanel = new JPanel();
	private JPanel finishpanel = new JPanel();
	private int hgap = 20;
	private int cols = 3;

	/**
	 * Create the Home Page
	 *
	 * @param ui The UI object that this page belongs to
	 */
	public HomePage(UI ui) {
		super(ui); // Uses the basic page layout and background color

		panelLayout();
		backlogPanel();
		currentWatchingPanel();
		finishPanel();

		this.add(contentPanel, BorderLayout.CENTER);
	}

	/**
	 * Create the Panel Layout for the Home Page Content
	 */
	private void panelLayout() {
		contentPanel = new JPanel(); // Create the Panel
		contentPanel.setBackground(this.PageColor);
		contentPanel.setLayout(new GridLayout(1, 3, hgap, 0));
	}

	/**
	 * Create the Backlog Panel Widget for the User
	 */
	private void backlogPanel() {
		backlogpanel.setLayout(new BorderLayout());
		backlogpanel.setBackground(this.PageColor);

		JPanel blbuttonpanel = new JPanel();
		blbuttonpanel.setBackground(this.PageColor);
		blbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));
		for (int i = 1; i <= 25; i++) {
			blbuttonpanel.add(new JButton(String.valueOf(i)));
		}

		JScrollPane blScrollPane = new JScrollPane(blbuttonpanel);
		blScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		blScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel bllabel = new JLabel("Backlog", SwingConstants.CENTER);
		bllabel.setForeground(Style.TEA_GREEN);
		bllabel.setFont(Style.BASE_FONT);

		backlogpanel.add(bllabel, BorderLayout.PAGE_START);
		backlogpanel.add(blbuttonpanel, BorderLayout.CENTER);

		// Add the backlog panel to the content panel
		contentPanel.add(backlogpanel);
	}

	/**
	 * Create the Current Watching Panel Widget for the User
	 */
	private void currentWatchingPanel() {
		currentpanel.setLayout(new BorderLayout());
		currentpanel.setBackground(this.PageColor);

		JPanel crbuttonpanel = new JPanel();
		crbuttonpanel.setBackground(this.PageColor);
		crbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));
		for (int i = 1; i <= 25; i++) {
			crbuttonpanel.add(new JButton(String.valueOf(i)));
		}

		JScrollPane crScrollPane = new JScrollPane(crbuttonpanel);
		crScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		crScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel crlabel = new JLabel("Currently Watching", SwingConstants.CENTER);
		crlabel.setFont(Style.BASE_FONT);
		crlabel.setForeground(Style.TEA_GREEN);

		currentpanel.add(crlabel, BorderLayout.PAGE_START);
		currentpanel.add(crbuttonpanel, BorderLayout.CENTER);

		// Add the current panel to the content panel
		contentPanel.add(currentpanel);
	}

	/**
	 * Create the Finish Panel Widget for the User
	 */
	private void finishPanel() {
		finishpanel.setLayout(new BorderLayout());
		finishpanel.setBackground(this.PageColor);

		JPanel finbuttonpanel = new JPanel();
		finbuttonpanel.setBackground(this.PageColor);
		finbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));
		for (int i = 1; i <= 25; i++) {
			finbuttonpanel.add(new JButton(String.valueOf(i)));
		}

		JScrollPane finScrollPane = new JScrollPane(finbuttonpanel);
		finScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		finScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel finlabel = new JLabel("Finished", SwingConstants.CENTER);
		finlabel.setFont(Style.BASE_FONT);
		finlabel.setForeground(Style.TEA_GREEN);

		finishpanel.add(finlabel, BorderLayout.PAGE_START);
		finishpanel.add(finbuttonpanel, BorderLayout.CENTER);

		// Add the finish panel to the content panel
		contentPanel.add(finishpanel);
	}
}
