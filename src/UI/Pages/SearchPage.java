package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

import DTO.LocalDB.Media;
import UI.Style;
import UI.UI;

/**
 * The Search Page Class. Used to search for media to add to the DB
 */
public class SearchPage extends Page {

	private JPanel contentPanel;
	private JPanel searchPanel;
	private JPanel listPanel;

	private GridBagConstraints gbc;

	private JLabel searchLbl;
	private JTextField searchField;
	private JComboBox<String> searchTypeBox;
	private final String[] TYPES = new String[] { "Movie", "TV Show", "Anime" };
	private JButton searchBtn;

	private JScrollPane listScrollPane;
	private JPanel scrollWrapperPanel;
	private JPanel scrollContentPanel;

	private final int POSTER_WIDTH = 133;
	private final int POSTER_HEIGHT = 200;
	private final Dimension POSTER_SIZE = new Dimension(POSTER_WIDTH, POSTER_HEIGHT);

	private final int DESCRIPTION_CPERLINE = 50;
	private final int TITLE_CPERLINE = 12;
	private final int MAX_PASS = 5;

	private final String PATH_FOR_DEFAULT_IMAGE = "assets/UI/filal.png";

	private final String[] SHOW_STATUS_OPTIONS = new String[] { "Undecided", "Dropped", "Backlog", "Watching",
			"Completed" };

	/**
	 * Create the Search Page
	 *
	 * @param ui The UI object that this page belongs to
	 */
	public SearchPage(UI ui) {
		super(ui); // Uses the basic page layout and background color

		createContentPanel();

		createSearchPanel();

		addSearchLabel();
		addSearchField();
		addSearchTypeBox();
		addSearchBtn();

		createListPanel();

		addListScrollContainer();
	}

	/**
	 * Creates content panel that will contain all the components of the page
	 *
	 * Includes border styling and coloring
	 */
	void createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBackground(PageColor);
		contentPanel.setLayout(new BorderLayout());

		this.add(contentPanel);
	}

	/**
	 * Creates the search panel that will contain all the search components
	 *
	 * Includes border styling and coloring
	 */
	void createSearchPanel() {
		searchPanel = new JPanel();
		searchPanel.setBackground(PageColor);
		searchPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		contentPanel.add(searchPanel, BorderLayout.NORTH);
	}

	/**
	 * Creates the list panel that will contain all the list components
	 *
	 * Includes border styling and coloring
	 */
	void createListPanel() {
		listPanel = new JPanel();
		listPanel.setBackground(PageColor);
		listPanel.setPreferredSize(new Dimension(400, 400));

		contentPanel.add(listPanel, BorderLayout.CENTER);
	}

	/**
	 * Creates search label for the search panel
	 *
	 * Includes styling and formatting
	 */
	void addSearchLabel() {
		searchLbl = new JLabel("Search: ");
		searchLbl.setFont(Style.BASE_FONT);
		searchLbl.setBackground(this.PageColor);
		searchLbl.setForeground(Style.TEA_GREEN);

		gbc.gridy = 0; // only one row
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(0, 20, 10, 0);

		searchPanel.add(searchLbl, gbc);
	}

	/**
	 * Adds the search bar for the search panel
	 *
	 * Includes styling and formatting
	 */
	void addSearchField() {
		searchField = new JTextField(20);

		// Styling
		searchField.setFont(Style.BASE_FONT);
		searchField.setBackground(Style.TEA_GREEN);
		searchField.setForeground(Style.BALTIC_BLUE);
		searchField.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));

		searchField.addActionListener(e -> runSearch());

		gbc.gridy = 0; // only one row
		gbc.gridx = 1; // col 2

		// 20 px padding on left
		gbc.insets = new Insets(0, 0, 10, 0);

		searchPanel.add(searchField, gbc);
	}

	/**
	 * Adds the media search type dropdown box, switches button icons depending on
	 * the type of media being searched for the search panel
	 *
	 * Includes stlying and formatting
	 */
	void addSearchTypeBox() {
		searchTypeBox = new JComboBox<String>(TYPES);

		// Styling
		searchTypeBox.setFont(Style.BASE_FONT);
		searchTypeBox.setBackground(Style.TEA_GREEN);
		searchTypeBox.setForeground(Style.BALTIC_BLUE);
		searchTypeBox.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		searchTypeBox.setFocusable(false);

		gbc.gridy = 0; // only one row
		gbc.gridx = 2; // col 3

		// only padding on bottom for spacing
		gbc.insets = new Insets(0, 0, 10, 0);

		searchTypeBox.addActionListener(e -> { // changing search button icon depending on media type
			String showtype = searchTypeBox.getSelectedItem().toString();
			if (showtype.equals("Movie")) {
				ui.addButtonImg(searchBtn, new ImageIcon("assets/UI/moviesearchicon.png"), 20, 30, 30);
			} else if (showtype.equals("TV Show")) {
				ui.addButtonImg(searchBtn, new ImageIcon("assets/UI/tvsearchicon.png"), 20, 30, 30);
			} else if (showtype.equals("Anime")) {
				ui.addButtonImg(searchBtn, new ImageIcon("assets/UI/animesearchicon.png"), 20, 30, 30);
			} else {
				ui.addButtonImg(searchBtn, new ImageIcon("assets/UI/searchicon.png"), 20, 30, 30);
			}

		});
		searchPanel.add(searchTypeBox, gbc);
	}

	/**
	 * Adds search button for the search panel
	 *
	 * Includes styling and formatting
	 */
	void addSearchBtn() {

		// Styling (background, text font and color, image)
		searchBtn = new JButton("Search");
		searchBtn.setFont(Style.BASE_FONT);
		searchBtn.setBackground(Style.LIGHT_GREEN);
		searchBtn.setForeground(Style.BALTIC_BLUE);
		searchBtn.setFont(Style.BASE_FONT);
		ui.addButtonImg(searchBtn, new ImageIcon("assets/UI/moviesearchicon.png"), 20, 30, 30);
		searchBtn.setFocusable(false);

		gbc.gridy = 0; // only one row
		gbc.gridx = 3; // col 4

		// padding for 20px right to match offset from searchField
		gbc.insets = new Insets(0, 0, 10, 20);

		searchBtn.addActionListener(e -> runSearch());

		searchPanel.add(searchBtn, gbc);
	}

	/**
	 * This is a Shell function that
	 */
	void runSearch() {
		procureSearches(10, searchField.getText(), searchTypeBox.getSelectedIndex());
	}

	/**
	 * Creates the scrolling area for all the media searched
	 *
	 * Includes styling and some formatting
	 */
	void addListScrollContainer() {
		// creating the panel where all the searched media is put in
		scrollWrapperPanel = new JPanel(new BorderLayout());
		scrollContentPanel = new JPanel(new GridLayout(0, 1, 0, 20));
		scrollWrapperPanel.add(scrollContentPanel, BorderLayout.NORTH);

		// creating the scroll area
		listScrollPane = new JScrollPane(scrollWrapperPanel);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setPreferredSize(new Dimension(1700, 800));
		listScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollContentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, false));
		scrollContentPanel.setBackground(Style.TEA_GREEN);
		listScrollPane.setBackground(Style.BORDER_COLOR);
		listScrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		scrollWrapperPanel.setBackground(Style.BORDER_COLOR);

		listScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() { // changing scroll bar color
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Style.BORDER_COLOR;
				this.trackColor = Style.TEA_GREEN;
			}
		});
		listPanel.add(listScrollPane);
	}

	JPanel getSearchResultPanel(Media givenMedia) {
		JPanel result = new JPanel();
		result.setBackground(Style.BORDER_COLOR);
		result.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints(); // reset gbc to ensure ready to go

		String urlString = givenMedia.getPosterLink();

		// get url in try catch
		URL url;
		try {
			url = new URI(urlString).toURL();
		} catch (Exception e) {
			url = null;
		}
		// attempt to create and scale poster
		ImageIcon poster;
		if (url != null)
			poster = getSearchResultPoster(url);
		else // if fails to grab poster, set poster to Filal Baruqi
			poster = getDefaultPoster();

		// create poster
		gbc.gridy = 0; // row is always 0
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(20, 20, 20, 200);

		JLabel posterLbl = new JLabel();
		posterLbl.setIcon(poster);
		posterLbl.setPreferredSize(POSTER_SIZE);
		posterLbl.setMinimumSize(POSTER_SIZE);
		posterLbl.setMaximumSize(POSTER_SIZE);
		result.add(posterLbl, gbc);

		// add name
		String titleString = givenMedia.getName();
		JLabel titleLbl = new JLabel(ui.getHtmlFormatText(titleString, TITLE_CPERLINE, MAX_PASS, 300));
		titleLbl.setFont(Style.TITLE_FONT);
		titleLbl.setForeground(Style.TEA_GREEN);

		gbc.gridx = 1; // col 2
		gbc.insets = new Insets(20, 0, 20, 50);
		result.add(titleLbl, gbc);

		// add desc.
		// description is mounted via singular line
		String descString = givenMedia.getDescription();
		JLabel descLbl = new JLabel(ui.getHtmlFormatText(descString, DESCRIPTION_CPERLINE, MAX_PASS, 400));
		descLbl.setFont(Style.DESC_FONT);
		descLbl.setForeground(Style.TEA_GREEN);

		gbc.gridx = 2; // col 3
		gbc.insets = new Insets(20, 0, 20, 50);

		result.add(descLbl, gbc);

		// add button
		JButton addToDb = new JButton();
		addToDb.setFont(Style.BASE_FONT);
		addToDb.setBackground(Style.LIGHT_GREEN);
		addToDb.setForeground(Style.BALTIC_BLUE);
		addToDb.setFont(Style.BASE_FONT);
		ui.addButtonImg(addToDb, new ImageIcon("assets/UI/plus.png"), 20, 30, 30);

		addToDb.addActionListener(e -> {

			if (ui.createMedia(givenMedia)) {
				JOptionPane.showMessageDialog(this, "Successfully added show to local database.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				// update the current panel and replace the button with the dropdown

				result.remove(addToDb);
				GridBagConstraints gbc2 = new GridBagConstraints();

				JComboBox<String> showStatus = new JComboBox<String>(SHOW_STATUS_OPTIONS);
				showStatus.setFont(Style.BASE_FONT);
				showStatus.setFocusable(false);

				// re create the Media Object with ID
				Media locatedMedia = ui.locateMedia(givenMedia);
				// on picking new option
				showStatus.addActionListener(event -> {
					// TODO Knowing existing search data and current user data, find the show again,
					// and change user information based on:
					int showStatusToUpdate = showStatus.getSelectedIndex();
					ui.editStatus(showStatusToUpdate, locatedMedia);
				});

				gbc2.gridy = 0;
				gbc2.gridx = 3; // col 4
				gbc2.insets = new Insets(20, 0, 20, 20);

				result.add(showStatus);

				// repaint scroll view
				listScrollPane.revalidate();
				listScrollPane.repaint();
			} else {
				JOptionPane.showMessageDialog(this, "Failed to add show to local database.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 3; // col 4
		gbc.insets = new Insets(20, 0, 20, 20);

		result.add(addToDb, gbc);

		return result;
	}

	/**
	 * Takes the url path for the poster image and gets the media's poster image
	 *
	 * @param url url that leads toward the poster image
	 * @return poster image
	 */
	ImageIcon getSearchResultPoster(URL url) {
		try {
			return ui.resizeImg(new ImageIcon(ImageIO.read(url)), POSTER_WIDTH, POSTER_HEIGHT);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * If there is no image found that gets updated, this method returns a set
	 * placeholder image
	 *
	 * @return the placeholder image (our mascot Filal Baruqi)
	 */
	ImageIcon getDefaultPoster() {
		try {
			return ui.resizeImg(new ImageIcon(PATH_FOR_DEFAULT_IMAGE), POSTER_WIDTH, POSTER_HEIGHT);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * With the Given info, display info to the User to add to Local DB
	 *
	 * @param numResults how many media to Return
	 * @param query      What is the Text to Search by
	 * @param type       What is the Type of Media (0 = Movies, 1 = Shows, 2 =
	 *                   Anime)
	 */
	void procureSearches(int numResults, String query, int type) {
		// empty existing
		scrollContentPanel.removeAll();

		Media[] Results; // Create Array of Media Object
		// Runs Different Method Based on Media
		switch (type) {
			case 0: // Movies
				Results = ui.searchMovie(query, numResults);
				break;
			case 1: // Shows
				Results = ui.searchShow(query, numResults);
				break;
			case 2: // Anime
				Results = ui.searchAnime(query, numResults);
				break;
			default:
				Results = new Media[0];
		}

		for (int i = 0; i < Results.length; i++) {
			scrollContentPanel.add(getSearchResultPanel(Results[i]));
			listScrollPane.getViewport().revalidate();
		}

		// Re make THe Scroll Content
		scrollContentPanel.revalidate();
		scrollContentPanel.repaint();

		// Re makes the List Pane
		listScrollPane.revalidate();
		listScrollPane.repaint();
	}
}
