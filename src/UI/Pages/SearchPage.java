package UI.Pages;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.net.URI;
import java.net.URL;

import UI.Style;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

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

	private final int POSTER_WIDTH = 200;
	private final int POSTER_HEIGHT = 200;

	private final int DESCRIPTION_CPERLINE = 40;
	private final int MAX_PASS = 5;

	private final String PATH_FOR_DEFAULT_IMAGE = "assets/UI/adminicon.png";

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

		searchBtn.addActionListener(e -> procureSearches(5));

		searchPanel.add(searchBtn, gbc);
	}

	void addListScrollContainer() {
		scrollWrapperPanel = new JPanel(new BorderLayout());
		scrollContentPanel = new JPanel(new GridLayout(0, 1, 0, 20));
		scrollWrapperPanel.add(scrollContentPanel, BorderLayout.NORTH);
		listScrollPane = new JScrollPane(scrollWrapperPanel);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setPreferredSize(new Dimension(1200, 900));
		listScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		listPanel.add(listScrollPane);
	}

	// poster
	// name (big)
	// desc. (small)
	// button to add to local db

	JPanel getSearchResultPanel() {
		JPanel result = new JPanel();
		result.setBackground(PageColor);
		result.setBorder(BorderFactory.createLineBorder(Color.black, 4, true));
		result.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints(); // reset gbc to ensure ready to go

		String urlString = "";

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
		gbc.insets = new Insets(POSTER_HEIGHT / 2, POSTER_WIDTH / 2, POSTER_HEIGHT / 2, POSTER_WIDTH / 2);

		result.add(new JLabel(poster));

		// add name
		JLabel name = new JLabel("Testing");
		name.setFont(Style.BASE_FONT);

		gbc.gridx = 1;
		result.add(name);

		// add desc.
		// description is mounted via singular line
		String testingDesc = "Sir James Bond 007, a legendary British spy who retired from the secret service 20 years previously, is visited by the head of British Secret Intelligence Service, M (James Bond), CIA representative Ransome, KGB representative Smernov, and Deuxième Bureau representative Le Grand. All implore Bond to come out of retirement to deal with SMERSH (James Bond) who have been eliminating agents: Bond spurns all their pleas. When Bond continues to stand firm, his mansion is destroyed by a mortar attack at the orders of M, who is, however, killed in the explosion.";
		JLabel desc = new JLabel(ui.getHtmlFormatText(testingDesc, DESCRIPTION_CPERLINE, MAX_PASS));
		desc.setFont(Style.DESC_FONT);

		gbc.gridx = 2;

		result.add(desc);

		// add button
		JButton addToDb = new JButton("+");
		addToDb.setFont(Style.BASE_FONT);

		gbc.gridx = 3;

		result.add(addToDb);

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
			return ui.resizeImg(new ImageIcon(PATH_FOR_DEFAULT_IMAGE), WIDTH, HEIGHT);
		} catch (Exception e) {
			System.out.println(System.getProperty("user.dir"));
			return null;
		}
	}

	void procureSearches(int test) {
		// empty existing
		scrollContentPanel.removeAll();

		// get serach number
		// and do search itself
		
		// for testing purposes simply
		for (int i = 0; i < test; i++) {
			scrollContentPanel.add(getSearchResultPanel());
			listScrollPane.getViewport().revalidate();
		}
		scrollContentPanel.revalidate();
		scrollContentPanel.repaint();

		listScrollPane.revalidate();
		listScrollPane.repaint();
	}
}
