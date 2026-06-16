package UI.Pages;

import UI.Style;
import UI.UI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DTO.LocalDB.Media;

/**
 * The Home Page Class. Used to Create the Main Page of the UI.
 * This will display some basic information about the user and their media.
 */
public class HomePage extends Page {
	// Variables
	private JPanel contentPanel;
	private JPanel backlogPanel = new JPanel();
	private JPanel currentPanel = new JPanel();
	private JPanel finishPanel = new JPanel();
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
		backlogPanel.setLayout(new BorderLayout());
		backlogPanel.setBackground(this.PageColor);

		JPanel blbuttonpanel = new JPanel();
		blbuttonpanel.setBackground(this.PageColor);
		blbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));

		// Pull Backlogged Media
		Media[] backloggedMedia = ui.findMedia(true, true, true, false, false, true, false, false, "", 0, 10);
		if (backloggedMedia != null) {
			for (int i = 0; i < backloggedMedia.length; i++) {
				JButton backloggedButton = new JButton(String.valueOf(i));
				blbuttonpanel.add(backloggedButton);
				Media displayMedia = backloggedMedia[i];
				backloggedButton.addActionListener(e -> {
					ui.openMediaPage(displayMedia, "home");
				});
			}
		} else {
			System.err.println("No Backlogged Media");
		}

		JScrollPane blScrollPane = new JScrollPane(blbuttonpanel);
		blScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		blScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel bllabel = new JLabel("Backlog", SwingConstants.CENTER);
		bllabel.setForeground(Style.TEA_GREEN);
		bllabel.setFont(Style.BASE_FONT);

		backlogPanel.add(bllabel, BorderLayout.PAGE_START);
		backlogPanel.add(blbuttonpanel, BorderLayout.CENTER);

		backlogPanel.revalidate();
		backlogPanel.repaint();
		// Add the backlog panel to the content panel
		contentPanel.add(backlogPanel);
	}

	/**
	 * Create the Current Watching Panel Widget for the User
	 */
	private void currentWatchingPanel() {
		currentPanel.setLayout(new BorderLayout());
		currentPanel.setBackground(this.PageColor);

		JPanel crbuttonpanel = new JPanel();
		crbuttonpanel.setBackground(this.PageColor);
		crbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));

		Media[] currentWatchMedia = ui.findMedia(true, true, true, false, false, false, true, false, "", 0, 10);
		if (currentWatchMedia != null) {
			for (int i = 0; i < currentWatchMedia.length; i++) {
				JButton currentWatchingButton = new JButton(String.valueOf(i));
				crbuttonpanel.add(currentWatchingButton);
				Media displayMedia = currentWatchMedia[i];
				currentWatchingButton.addActionListener(e -> {
					ui.openMediaPage(displayMedia, "home");
				});
			}
		} else {
			System.err.println("No Watching Media");
		}

		JScrollPane crScrollPane = new JScrollPane(crbuttonpanel);
		crScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		crScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel crlabel = new JLabel("Currently Watching", SwingConstants.CENTER);
		crlabel.setFont(Style.BASE_FONT);
		crlabel.setForeground(Style.TEA_GREEN);

		currentPanel.add(crlabel, BorderLayout.PAGE_START);
		currentPanel.add(crbuttonpanel, BorderLayout.CENTER);

		currentPanel.revalidate();
		currentPanel.repaint();
		// Add the current panel to the content panel
		contentPanel.add(currentPanel);
	}

	/**
	 * Create the Finish Panel Widget for the User
	 */
	private void finishPanel() {
		finishPanel.setLayout(new BorderLayout());
		finishPanel.setBackground(this.PageColor);

		JPanel finbuttonpanel = new JPanel();
		finbuttonpanel.setBackground(this.PageColor);
		finbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));

		Media[] CompletedMedia = ui.findMedia(true, true, true, false, false, false, false, true, "", 0, 10);
		if (CompletedMedia != null) {
			for (int i = 0; i < CompletedMedia.length; i++) {
				JButton completeMediaButton = new JButton(String.valueOf(i));
				finbuttonpanel.add(completeMediaButton);
				Media displayMedia = CompletedMedia[i];
				completeMediaButton.addActionListener(e -> {
					ui.openMediaPage(displayMedia, "home");
				});
			}
		} else {
			System.err.println("No Completed Media");
		}

		JScrollPane finScrollPane = new JScrollPane(finbuttonpanel);
		finScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		finScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel finlabel = new JLabel("Finished", SwingConstants.CENTER);
		finlabel.setFont(Style.BASE_FONT);
		finlabel.setForeground(Style.TEA_GREEN);

		finishPanel.add(finlabel, BorderLayout.PAGE_START);
		finishPanel.add(finbuttonpanel, BorderLayout.CENTER);

		finishPanel.revalidate();
		finishPanel.repaint();
		// Add the finish panel to the content panel
		contentPanel.add(finishPanel);
	}

	public void createWidgets() {
		contentPanel.removeAll();
		backlogPanel();
		currentWatchingPanel();
		finishPanel();
		this.contentPanel.revalidate();
		this.contentPanel.repaint();
		this.add(contentPanel, BorderLayout.CENTER);
		// Refresh the UI
		this.revalidate();
		this.repaint();
	}
}
