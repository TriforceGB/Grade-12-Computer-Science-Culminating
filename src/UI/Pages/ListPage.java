package UI.Pages;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import UI.Style;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.SpinnerNumberModel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import UI.UI;

/**
 * The List Page Class. Used to display a list of media for the user.
 */
public class ListPage extends Page {
	// Variables
	private JPanel contentPanel;
	private JPanel filterPanel;

	// table list variables
	private JPanel listPanel;
	private JTable listTable;
	private JScrollPane tableScrollContainer;

	private final String[] colNames = { "Icon", "Name", "Status", "Rating", "Last EP", "Rewatch" };
	private DefaultTableModel listTableModel;

	private GridBagConstraints gbc;

	// movie filter objects
	private JLabel movieTypeLbl;
	private JCheckBox movieType;

	private JLabel showTypeLbl;
	private JCheckBox showType;

	private JLabel animeTypeLbl;
	private JCheckBox animeType;

	private JLabel nameFilterLbl;
	private JTextField nameFilter;

	private JLabel statusFilterLbl;

	private JComboBox<String> statusFilter;
	private String[] showStatus = new String[] { "", "Undecided", "Backlog", "Watching", "Completed", "Dropped" };

	private JLabel minRatingLbl;
	private JSpinner minRating;

	private JLabel maxRatingLbl;
	private JSpinner maxRating;

	private JButton searchButton;

	private final Border border = BorderFactory.createLineBorder(Color.BLACK, 2, true); // true allows for rounded

	/**
	 * Create the List Page
	 *
	 * @param ui The UI object that this page belongs to
	 */
	public ListPage(UI ui) {
		super(ui); // Uses the basic page layout and background color

		createContentPanel();

		// add components to filter panel
		createFilterPanel();

		// add filter buttons
		addTypeCheckboxes();
		addNameSatusButtons();
		addRatingSelectorButtons();
		addSearchButton();

		createListPanel();

		addTableToListPanel();
	}

	void createFilterPanel() {
		// create filter panel
		filterPanel = new JPanel();
		filterPanel.setBackground(PageColor);
		filterPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		// set rounded borders
		filterPanel.setBorder(border);
		// then simply lock the width
		filterPanel.setSize(new Dimension(150, 0));
	}

	void createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBackground(PageColor);
		contentPanel.setLayout(new BorderLayout());
		
		// add content panel to main panel
		this.add(contentPanel);
	}

	void addTypeCheckboxes() {
		// three checkboxes in three different rows (type) (all require a label
		// attached)
		// note row comments are not accurate (psa gridy = 0 -> row 1)
		movieTypeLbl = new JLabel("Movie: ");
		movieTypeLbl.setFont(Style.BASE_FONT);
		movieType = new JCheckBox();

		showTypeLbl = new JLabel("TV Show: ");
		showTypeLbl.setFont(Style.BASE_FONT);
		showType = new JCheckBox();

		animeTypeLbl = new JLabel("Anime: ");
		animeTypeLbl.setFont(Style.BASE_FONT);
		animeType = new JCheckBox();

		gbc.gridx = 0; // col 1
		gbc.gridy = 1; // row 1
		// gbc.insets = Style.LABEL_PADS;
		filterPanel.add(movieTypeLbl, gbc);

		gbc.gridx = 1; // col 2
		filterPanel.add(movieType, gbc);

		gbc.gridx = 0; // col 1
		gbc.gridy = 2; // row 2
		filterPanel.add(showTypeLbl, gbc);

		gbc.gridx = 1; // col 1
		filterPanel.add(showType, gbc);

		gbc.gridx = 0; // col 1
		gbc.gridy = 3; // row 3
		filterPanel.add(animeTypeLbl, gbc);

		gbc.gridx = 1; // col 2
		filterPanel.add(animeType, gbc);
	}

	void addNameSatusButtons() {
		// name & status row
		// name is texfield and status is a dropdown
		nameFilterLbl = new JLabel("Name: ");
		nameFilterLbl.setFont(Style.BASE_FONT);
		nameFilter = new JTextField(10);
		nameFilter.setSize(new Dimension(100, 10));
		nameFilter.setFont(Style.BASE_FONT);

		statusFilterLbl = new JLabel("Status: ");
		statusFilterLbl.setFont(Style.BASE_FONT);
		statusFilter = new JComboBox<String>(showStatus);
		statusFilter.setFont(Style.BASE_FONT);

		gbc.gridy = 4; // row 5
		gbc.gridx = 0; // col 1
		// insets still exist
		filterPanel.add(nameFilterLbl, gbc);

		gbc.gridx = 1; // col 2
		filterPanel.add(nameFilter, gbc);

		gbc.gridx = 2; // col 3
		filterPanel.add(statusFilterLbl, gbc);

		gbc.gridx = 3; // col 4
		filterPanel.add(statusFilter, gbc);

	}

	void addRatingSelectorButtons() {
		// rating range row 2x JSpinners
		minRatingLbl = new JLabel("Min Rating: ");
		minRatingLbl.setFont(Style.BASE_FONT);
		minRating = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		minRating.setFont(Style.BASE_FONT);

		maxRatingLbl = new JLabel("Max Rating: ");
		maxRatingLbl.setFont(Style.BASE_FONT);
		maxRating = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
		maxRating.setFont(Style.BASE_FONT);

		// change listeners for both ratings
		// adds the listeners that do the things to ensure bounds are set properly
		// ex. min is always <= max
		// and max is always >= min
		minRating.addChangeListener(e -> {
			// both cases pull the values by getting value casted to an int directly
			// the spinners themselves only allow numbers due to the SpinnerNumberModel
			int minVal = (int) minRating.getValue();
			int maxVal = (int) maxRating.getValue();

			// then check if min is greater than max
			if (minVal > maxVal)
				// in which case, max must match to go up
				maxRating.setValue(minVal);
		});

		maxRating.addChangeListener(e -> {
			int minVal = (int) minRating.getValue();
			int maxVal = (int) maxRating.getValue();

			// in this case, min must follow max
			if (maxVal < minVal)
				minRating.setValue(maxVal);
		});

		gbc.gridy = 5; // row 6
		gbc.gridx = 0; // col 1
		filterPanel.add(minRatingLbl, gbc);

		gbc.gridx = 1; // col 2
		filterPanel.add(minRating, gbc);

		gbc.gridx = 2; // col 3
		filterPanel.add(maxRatingLbl, gbc);

		gbc.gridx = 3; // col 4
		filterPanel.add(maxRating, gbc);

	}

	void addSearchButton() {
		// search button on set row
		searchButton = new JButton("Search");
		searchButton.setFont(Style.BASE_FONT);
		searchButton.addActionListener(e -> {
			// TODO: Implement db. also verify if selector for name and status are blank to
			// not care
			String nameToCheck = nameFilter.getText();
			String statusToCheck = statusFilter.getSelectedItem().toString();
			int minRatingToCheck = (int) minRating.getValue();
			int maxRatingToCheck = (int) maxRating.getValue();
			boolean canBeMovie = movieType.isSelected();
			boolean canBeShow = showType.isSelected();
			boolean canBeAnime = animeType.isSelected();

			
		});

		gbc.gridy = 6; // row 7
		gbc.gridx = 0; // col 1
		filterPanel.add(searchButton, gbc);
	}

	void createListPanel() {
		// create list panel
		listPanel = new JPanel();
		listPanel.setBackground(PageColor);
		listPanel.setLayout(new BorderLayout());
	}

	void addTableToListPanel() {
		// create JTable to display on list panel
		listTableModel = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			// allows for column 1 to be an icon
			// images have to be pre-scaled or modified before.
			// might have to modify row size
			@Override
			public Class<?> getColumnClass(int column) {
				// Tell the table that column index 1 contains Icons
				if (column == 1) {
					return Icon.class;
				}
				return super.getColumnClass(column);
			}
		};
		listTable = new JTable(listTableModel);
		tableScrollContainer = new JScrollPane(listTable);
		tableScrollContainer.setBorder(border);
		tableScrollContainer.getViewport().setBackground(PageColor);

		contentPanel.add(filterPanel, BorderLayout.WEST);
		contentPanel.add(tableScrollContainer, BorderLayout.CENTER);
	}
}
