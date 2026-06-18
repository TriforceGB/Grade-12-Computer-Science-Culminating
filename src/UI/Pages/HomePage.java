package UI.Pages;

import UI.Style;
import UI.UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

import DTO.LocalDB.Media;

/**
 * The Home Page Class. Used to Create the Main Page of the UI.
 * This will display some basic information about the user and their media.
 */
public class HomePage extends Page {
	// Variables
	private JPanel contentPanel;
	private final JPanel SOUTH_PADDING_PANEL = new JPanel();

	private JPanel backlogPanel;
	private JPanel cWatchPanel;
	private JPanel finPanel;

	private JPanel backlogBtnPanel;
	private JPanel cWatchBtnPanel;

	private JScrollPane backlogScrollPane;
	private JScrollPane cWatchScrollPane;
	private JScrollPane finScrollPane;

	private final int HGAP = 20;
	private final int COLS = 3;

	// constants used for layouts
	private final int POSTER_WIDTH = 150;
	private final int POSTER_HEIGHT = 225;
	private final Dimension STANDARD_WIDGET_SIZE = new Dimension(POSTER_WIDTH, POSTER_HEIGHT);
	private final FlowLayout WRAPPER_PANEL_LAYOUT = new FlowLayout(FlowLayout.LEFT, 60, 50);

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
		contentPanel.setBackground(PageColor);
		contentPanel.setLayout(new GridLayout(1, 3, -1, 0));
		add(contentPanel, BorderLayout.CENTER);

		SOUTH_PADDING_PANEL.setBackground(PageColor);
		add(SOUTH_PADDING_PANEL, BorderLayout.SOUTH);
	}

	/**
	 * Create the Backlog Panel Widget for the User
	 */
	private void backlogPanel() {
		backlogPanel = new JPanel();
		backlogPanel.setLayout(new BorderLayout());
		backlogPanel.setBackground(this.PageColor);

		backlogBtnPanel = new JPanel();
		backlogBtnPanel.setBackground(this.PageColor);
		backlogBtnPanel.setLayout(new GridLayout(0, COLS, HGAP, 20));

		// Pull Backlogged Media
		Media[] backloggedMedia = ui.findMedia(true, true, true, false, false, true,
				false, false, "", 0, 10);
		if (backloggedMedia != null) {
			// then dynamically add buttons for media access.
			// all the three other panels follow the same method
			for (int i = 0; i < backloggedMedia.length; i++) {
				JButton backLogBtn = new JButton();
				backLogBtn.setPreferredSize(STANDARD_WIDGET_SIZE);
				Media displayMedia = backloggedMedia[i];
				backLogBtn.addActionListener(e -> {
					ui.openMediaPage(displayMedia, "home");
				});
				backLogBtn.setIcon(
						ui.resizeImg(new ImageIcon(displayMedia.getPosterPath()), POSTER_WIDTH, POSTER_HEIGHT));

				backLogBtn.setContentAreaFilled(false);
				backLogBtn.setBorderPainted(false);

				backlogBtnPanel.add(backLogBtn);
			}
		} else {
			System.out.println("No Backlogged Media");
		}

		JPanel wrapperPanel = new JPanel(WRAPPER_PANEL_LAYOUT);
		wrapperPanel.setBackground(this.PageColor);
		wrapperPanel.add(backlogBtnPanel);

		backlogScrollPane = new JScrollPane(wrapperPanel);
		backlogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		backlogScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		backlogScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		backlogScrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
		backlogScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() { // changing scroll bar color
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Style.BORDER_COLOR;
				this.trackColor = Style.TEA_GREEN;
			}
		});
		JLabel backlogLbl = new JLabel("Backlog", SwingConstants.CENTER);
		backlogLbl.setForeground(Style.TEA_GREEN);
		backlogLbl.setFont(Style.BASE_FONT);

		backlogPanel.add(backlogLbl, BorderLayout.NORTH);
		backlogPanel.add(backlogScrollPane, BorderLayout.CENTER);

		backlogPanel.revalidate();
		backlogPanel.repaint();
		// Add the backlog panel to the content panel
		contentPanel.add(backlogPanel);
	}

	/**
	 * Create the Current Watching Panel Widget for the User
	 */
	private void currentWatchingPanel() {
		cWatchPanel = new JPanel();
		cWatchPanel.setLayout(new BorderLayout());
		cWatchPanel.setBackground(this.PageColor);

		cWatchBtnPanel = new JPanel();
		cWatchBtnPanel.setBackground(this.PageColor);
		cWatchBtnPanel.setLayout(new GridLayout(0, COLS, HGAP, 20));

		Media[] currentWatchMedia = ui.findMedia(true, true, true, false, false, false, true, false, "", 0, 10);
		if (currentWatchMedia != null) {
			for (int i = 0; i < currentWatchMedia.length; i++) {
				JButton cWatchingBtn = new JButton(String.valueOf(i));
				cWatchingBtn.setPreferredSize(STANDARD_WIDGET_SIZE);
				Media displayMedia = currentWatchMedia[i];
				cWatchingBtn.addActionListener(e -> {
					ui.openMediaPage(displayMedia, "home");
				});
				cWatchingBtn.setIcon(
						ui.resizeImg(new ImageIcon(displayMedia.getPosterPath()), POSTER_WIDTH, POSTER_HEIGHT));

				cWatchingBtn.setContentAreaFilled(false);
				cWatchingBtn.setBorderPainted(false);

				cWatchBtnPanel.add(cWatchingBtn);
			}
		} else {
			System.out.println("No Watching Media");
		}

		JPanel wrapperPanel = new JPanel(WRAPPER_PANEL_LAYOUT);
		wrapperPanel.setBackground(this.PageColor);
		wrapperPanel.add(cWatchBtnPanel);

		cWatchScrollPane = new JScrollPane(wrapperPanel);
		cWatchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		cWatchScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cWatchScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		cWatchScrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
		cWatchScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() { // changing scroll bar color
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Style.BORDER_COLOR;
				this.trackColor = Style.TEA_GREEN;
			}
		});
		JLabel cWatchLbl = new JLabel("Currently Watching", SwingConstants.CENTER);
		cWatchLbl.setFont(Style.BASE_FONT);
		cWatchLbl.setForeground(Style.TEA_GREEN);

		cWatchPanel.add(cWatchLbl, BorderLayout.NORTH);
		cWatchPanel.add(cWatchScrollPane, BorderLayout.CENTER);

		cWatchPanel.revalidate();
		cWatchPanel.repaint();
		// Add the current panel to the content panel
		contentPanel.add(cWatchPanel);
	}

	/**
	 * Create the Finish Panel Widget for the User
	 */
	private void finishPanel() {
		finPanel = new JPanel();
		finPanel.setLayout(new BorderLayout());
		finPanel.setBackground(this.PageColor);

		JPanel finBtnPanel = new JPanel();
		finBtnPanel.setBackground(this.PageColor);
		finBtnPanel.setLayout(new GridLayout(0, COLS, HGAP, 20));

		Media[] completedMedia = ui.findMedia(true, true, true, false, false, false, false, true, "", 0, 10);
		if (completedMedia != null) {
			for (int i = 0; i < completedMedia.length; i++) {
				JButton finBtn = new JButton(String.valueOf(i));
				finBtn.setPreferredSize(STANDARD_WIDGET_SIZE);
				Media displayMedia = completedMedia[i];
				finBtn.addActionListener(e -> {
					ui.openMediaPage(displayMedia, "home");
				});
				finBtn.setIcon(ui.resizeImg(new ImageIcon(displayMedia.getPosterPath()), POSTER_WIDTH, POSTER_HEIGHT));

				finBtn.setContentAreaFilled(false);
				finBtn.setBorderPainted(false);

				finBtnPanel.add(finBtn);
			}
		} else {
			System.out.println("No Completed Media");
		}

		JPanel wrapperPanel = new JPanel(WRAPPER_PANEL_LAYOUT);
		wrapperPanel.setBackground(this.PageColor);
		wrapperPanel.add(finBtnPanel);

		finScrollPane = new JScrollPane(wrapperPanel);
		finScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		finScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		finScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		finScrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
		finScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() { // changing scroll bar color
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Style.BORDER_COLOR;
				this.trackColor = Style.TEA_GREEN;
			}
		});
		JLabel finLbl = new JLabel("Finished", SwingConstants.CENTER);
		finLbl.setFont(Style.BASE_FONT);
		finLbl.setForeground(Style.TEA_GREEN);

		finPanel.add(finLbl, BorderLayout.NORTH);
		finPanel.add(finScrollPane, BorderLayout.CENTER);

		finPanel.revalidate();
		finPanel.repaint();
		// Add the finish panel to the content panel
		contentPanel.add(finPanel);
	}

	public void createWidgets() {
		contentPanel.removeAll();
		backlogPanel();
		currentWatchingPanel();
		finishPanel();
		contentPanel.revalidate();
		contentPanel.repaint();

		// Refresh the UI
		contentPanel.revalidate();
		contentPanel.repaint();
	}
}
