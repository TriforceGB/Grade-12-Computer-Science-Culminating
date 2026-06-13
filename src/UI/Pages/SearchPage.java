package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
	private final String[] types = new String[] { "Movie", "TV Show", "Anime" };
	private JButton searchBtn;

	private JScrollPane listScrollContainer;

	private JPanel scrollContentPanel;

	private final int posterWidth = 200;
	private final int posterHeight = 200;

	private final String testUrlString = "https://images.unsplash.com/photo-1450558415837-1f5e21a17709?ixid=M3w4MjcwNjd8MHwxfHNlYXJjaHwzfHxqZXN1c3xlbnwwfHx8fDE3ODEyMjk4ODh8MA&ixlib=rb-4.1.0&fit=max&q=80";

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

		// for testing purposes simply
		for (int i = 0; i < 3; i++) {
			scrollContentPanel.add(getSearchResultPanel());
		}
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
		searchTypeBox = new JComboBox<String>(types);
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

		searchBtn.addActionListener(e -> procureSearches(1));

		searchPanel.add(searchBtn, gbc);
	}

	void addListScrollContainer() {
		scrollContentPanel = new JPanel(new GridLayout(0, 1, posterHeight + 20, 0));
		listScrollContainer = new JScrollPane(scrollContentPanel);
		listScrollContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScrollContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		listPanel.add(listScrollContainer);
	}

	// poster
	// name (big)
	// desc. (small)
	// button to add to local db

	JPanel getSearchResultPanel() {
		JPanel result = new JPanel();
		result.setBackground(PageColor);
		result.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints(); // reset gbc to ensure ready to go

		// get url in try catch
		URL url;
		try {
			url = new URI(testUrlString).toURL();
		} catch (Exception e) {
			url = null;
		}
		// attempt to create and scale poster
		ImageIcon poster;
		if (url != null)
			poster = getSearchResultPoster(url);
		else
			poster = null;

		// create poster
		gbc.gridy = 0; // row is always 0
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(posterHeight / 2, posterWidth / 2, posterHeight / 2, posterWidth / 2);

		result.add(new JLabel(poster));

		// add name
		JLabel name = new JLabel("Testing");
		name.setFont(Style.BASE_FONT);

		gbc.gridx = 1;
		result.add(name);

		// add desc.
		JLabel desc = new JLabel("<html>Testing description.<br>With second line too.</hmtl>");
		desc.setFont(Style.BASE_FONT);

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
			return ui.resizeImg(new ImageIcon(ImageIO.read(url)), posterWidth, posterHeight);
		} catch (Exception e) {
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
		}
		scrollContentPanel.revalidate();
		scrollContentPanel.repaint();

		listScrollContainer.revalidate();
		listScrollContainer.repaint();
	}
}
