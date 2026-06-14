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

	void createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBackground(PageColor);
		contentPanel.setLayout(new BorderLayout());

		this.add(contentPanel);
	}

	void createSearchPanel() {
		searchPanel = new JPanel();
		searchPanel.setBackground(PageColor);
		searchPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		contentPanel.add(searchPanel, BorderLayout.NORTH);
	}

	void createListPanel() {
		listPanel = new JPanel();
		listPanel.setBackground(PageColor);
		listPanel.setPreferredSize(new Dimension(400, 400));

		contentPanel.add(listPanel, BorderLayout.CENTER);
	}

	void addSearchLabel() {
		searchLbl = new JLabel("Search: ");
		searchLbl.setFont(Style.BASE_FONT);

		gbc.gridy = 0; // only one row
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(0, 20, 10, 0);

		searchPanel.add(searchLbl, gbc);
	}

	void addSearchField() {
		searchField = new JTextField(20);
		searchField.setFont(Style.BASE_FONT);

		gbc.gridy = 0; // only one row
		gbc.gridx = 1; // col 2

		// 20 px padding on left
		gbc.insets = new Insets(0, 0, 10, 0);

		searchPanel.add(searchField, gbc);
	}

	void addSearchTypeBox() {
		searchTypeBox = new JComboBox<String>(TYPES);
		searchTypeBox.setFont(Style.BASE_FONT);

		gbc.gridy = 0; // only one row
		gbc.gridx = 2; // col 3

		// only padding on bottom for spacing
		gbc.insets = new Insets(0, 0, 10, 0);

		searchPanel.add(searchTypeBox, gbc);
	}

	void addSearchBtn() {
		searchBtn = new JButton("Search");
		searchBtn.setFont(Style.BASE_FONT);

		gbc.gridy = 0; // only one row
		gbc.gridx = 3; // col 4

		// padding for 20px right to match offset from searchField
		gbc.insets = new Insets(0, 0, 10, 20);

		searchBtn.addActionListener(e -> procureSearches(10, searchField.getText(), searchTypeBox.getSelectedIndex()));

		searchPanel.add(searchBtn, gbc);
	}

	void addListScrollContainer() {
		scrollWrapperPanel = new JPanel(new BorderLayout());
		scrollContentPanel = new JPanel(new GridLayout(0, 1, 0, 20));
		scrollWrapperPanel.add(scrollContentPanel, BorderLayout.NORTH);
		listScrollPane = new JScrollPane(scrollWrapperPanel);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setPreferredSize(new Dimension(1500, 800));
		listScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		listPanel.add(listScrollPane);
	}

	JPanel getSearchResultPanel(Media obj) {
		JPanel result = new JPanel();
		result.setBackground(PageColor);
		result.setBorder(BorderFactory.createLineBorder(Color.black, 4, true));
		result.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints(); // reset gbc to ensure ready to go

		String urlString = obj.getPosterLink();

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
		String titleString = obj.getName();
		JLabel titleLbl = new JLabel(ui.getHtmlFormatText(titleString, TITLE_CPERLINE, MAX_PASS));
		titleLbl.setFont(Style.TITLE_FONT);

		gbc.gridx = 1; // col 2
		gbc.insets = new Insets(20, 0, 20, 100);
		result.add(titleLbl, gbc);

		// add desc.
		// description is mounted via singular line
		String descString = obj.getDescription();
		JLabel descLbl = new JLabel(ui.getHtmlFormatText(descString, DESCRIPTION_CPERLINE, MAX_PASS));
		descLbl.setFont(Style.DESC_FONT);

		gbc.gridx = 2; // col 3
		gbc.insets = new Insets(20, 0, 20, 70);

		result.add(descLbl, gbc);

		// add button
		JButton addToDb = new JButton("+");
		addToDb.setFont(Style.BASE_FONT);

		addToDb.addActionListener(e -> {
			boolean addedToDb = false;

			// TODO Add to db and check whether that failed or not
			// for testing below remove
			addedToDb = true;

			if (addedToDb) {
				JOptionPane.showMessageDialog(this, "Successfully added show to local database.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				// update the current panel and replace the button with the dropdown

				result.remove(addToDb);
				GridBagConstraints gbc2 = new GridBagConstraints();

				JComboBox<String> showStatus = new JComboBox<String>(SHOW_STATUS_OPTIONS);
				showStatus.setFont(Style.BASE_FONT);
				showStatus.setFocusable(false);

				// on picking new option
				showStatus.addActionListener(event -> {
					// TODO Knowing existing search data and current user data, find the show again,
					// and change user information based on:
					String showStatusToUpdate = showStatus.getSelectedItem().toString();
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

	ImageIcon getSearchResultPoster(URL url) {
		try {
			return ui.resizeImg(new ImageIcon(ImageIO.read(url)), POSTER_WIDTH, POSTER_HEIGHT);
		} catch (Exception e) {
			return null;
		}
	}

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
