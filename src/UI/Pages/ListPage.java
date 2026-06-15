package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import DTO.LocalDB.Media;
import UI.Style;
import UI.UI;
import Util.MoniagaStringList;

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
	private final char CHECKBOX_CHAR = '☒';
	private final char UNCHECKBOX_CHAR = '☐';
	private final String[] SHOW_STATUS_DEFAULT_OPTIONS = new String[] { "Undecided", "Backlog", "Watching", "Completed",
			"Dropped" };
	private final String[] SHOW_STATUS_COMBO_OPTIONS = new String[] { "All", "Undecided " + CHECKBOX_CHAR,
			"Backlog " + CHECKBOX_CHAR, "Watching " + CHECKBOX_CHAR,
			"Completed " + CHECKBOX_CHAR, "Dropped " + CHECKBOX_CHAR }; // space seperated checkbox representations
	private MoniagaStringList selectedOptions = new MoniagaStringList(SHOW_STATUS_DEFAULT_OPTIONS);

	private JLabel minRatingLbl;
	private JSpinner minRating;

	private JLabel maxRatingLbl;
	private JSpinner maxRating;

	private JButton searchButton;
	private JButton refreshButton;

	private final String PATH_FOR_DEFAULT_IMAGE = "assets/UI/filal.png";
	private final int POSTER_WIDTH = 100;
	private final int POSTER_HEIGHT = 150;

	private final Border border = BorderFactory.createLineBorder(Style.BORDER_COLOR, 4, true); // true allows for
																								// rounded

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
		addNameStatusButtons();
		addRatingSelectorButtons();
		addSearchButton();
		addRefreshButton();

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
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(PageColor);

		// add content panel to main panel
		this.add(contentPanel);
	}

	void addTypeCheckboxes() {
		// three checkboxes in three different rows (type) (all require a label
		// attached)
		// note row comments are not accurate (psa gridy = 0 -> row 1)
		movieTypeLbl = new JLabel("Movie: ");
		movieTypeLbl.setFont(Style.BASE_FONT);
		movieTypeLbl.setForeground(Style.TEA_GREEN); // set the font color of the password label
		movieType = new JCheckBox();
		movieType.setSelected(true);
		movieType.setBackground(Style.BALTIC_BLUE);

		showTypeLbl = new JLabel("TV Show: ");
		showTypeLbl.setFont(Style.BASE_FONT);
		showTypeLbl.setForeground(Style.TEA_GREEN);
		showType = new JCheckBox();
		showType.setSelected(true);
		showType.setBackground(Style.BALTIC_BLUE);

		animeTypeLbl = new JLabel("Anime: ");
		animeTypeLbl.setFont(Style.BASE_FONT);
		animeTypeLbl.setForeground(Style.TEA_GREEN);
		animeType = new JCheckBox();
		animeType.setSelected(true);
		animeType.setBackground(Style.BALTIC_BLUE);

		gbc.gridx = 0; // col 1
		gbc.gridy = 1; // row 1
		gbc.insets = Style.LABEL_PADS;
		gbc.insets = new Insets(0, 0, 0, 0);
		filterPanel.add(movieTypeLbl, gbc);

		gbc.gridx = 1; // col 2
		gbc.insets = new Insets(0, 0, 0, 140);
		filterPanel.add(movieType, gbc);

		gbc.gridx = 0; // col 1
		gbc.gridy = 2; // row 2
		gbc.insets = new Insets(0, 0, 0, 0);
		filterPanel.add(showTypeLbl, gbc);

		gbc.gridx = 1; // col 1
		gbc.insets = new Insets(0, 0, 0, 140);
		filterPanel.add(showType, gbc);

		gbc.gridx = 0; // col 1
		gbc.gridy = 3; // row 3
		gbc.insets = new Insets(0, 0, 0, 0);
		filterPanel.add(animeTypeLbl, gbc);

		gbc.gridx = 1; // col 2
		gbc.insets = new Insets(0, 0, 0, 140);
		filterPanel.add(animeType, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);

	}

	void addNameStatusButtons() {
		// name & status row
		// name is texfield and status is a dropdown
		nameFilterLbl = new JLabel("Name: ");
		nameFilterLbl.setFont(Style.BASE_FONT);
		nameFilterLbl.setForeground(Style.TEA_GREEN);
		nameFilter = new JTextField(10);
		nameFilter.setSize(new Dimension(100, 10));
		nameFilter.setFont(Style.BASE_FONT);
		nameFilter.setBackground(Style.TEA_GREEN);
		nameFilter.setForeground(Style.BALTIC_BLUE);
		nameFilter.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));

		statusFilterLbl = new JLabel("Status: ");
		statusFilterLbl.setFont(Style.BASE_FONT);
		statusFilterLbl.setForeground(Style.TEA_GREEN);
		statusFilter = new JComboBox<String>(SHOW_STATUS_COMBO_OPTIONS);
		statusFilter.setFont(Style.BASE_FONT);
		statusFilter.setBackground(Style.TEA_GREEN);
		statusFilter.setForeground(Style.BALTIC_BLUE);
		statusFilter.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		statusFilter.setFocusable(false);

		statusFilter.addActionListener(e -> {
			// ensure seletecd index is not 0
			if (statusFilter.getSelectedIndex() != 0) {
				// get selected index to swap state of existence
				int indexToMod = statusFilter.getSelectedIndex();
				String nameOfEntry = statusFilter.getSelectedItem().toString().split(" ")[0];

				// add/remove fom msl
				if (selectedOptions.exists(nameOfEntry)) {
					statusFilter.removeItemAt(indexToMod);
					statusFilter.insertItemAt(nameOfEntry + " " + UNCHECKBOX_CHAR, indexToMod);

					selectedOptions.removeWhen(nameOfEntry);
				} else {
					statusFilter.removeItemAt(indexToMod);
					statusFilter.insertItemAt(nameOfEntry + " " + CHECKBOX_CHAR, indexToMod);

					selectedOptions.add(nameOfEntry);
				}

				// if count is 5: All
				// else is first letter of those selected (don't care about order)
				String displaySelected = "None";
				if (selectedOptions.count() == 5) {
					displaySelected = "All";
				} else if (selectedOptions.count() > 0) {
					displaySelected = "";
					// get first letter of all options currently selected
					MoniagaStringList fLetters = new MoniagaStringList();
					for (int i = 0; i < selectedOptions.count(); i++) {
						fLetters.add(selectedOptions.getAt(i).charAt(0) + "");
					}
					for (int i = 0; i < fLetters.count(); i++) {
						displaySelected += fLetters.getAt(i);
						if (i < fLetters.count() - 1)
							displaySelected += ", ";
					}
				}

				// rename to identify current existence
				statusFilter.removeItemAt(0);
				statusFilter.insertItemAt(displaySelected, 0);

				// set selected index 0
				statusFilter.setSelectedIndex(0);
			}
		});

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
		minRatingLbl.setForeground(Style.TEA_GREEN);
		minRating = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		minRating.setFont(Style.BASE_FONT);
		JSpinner.DefaultEditor mineditor = (JSpinner.DefaultEditor) minRating.getEditor();
		JTextField minratingtextfield = mineditor.getTextField();
		minRating.setBackground(Style.TEA_GREEN);
		minRating.setForeground(Style.BALTIC_BLUE);
		minRating.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		minratingtextfield.setBackground(Style.TEA_GREEN);
		minratingtextfield.setForeground(Style.BALTIC_BLUE);
		minratingtextfield.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));

		maxRatingLbl = new JLabel("Max Rating: ");
		maxRatingLbl.setFont(Style.BASE_FONT);
		maxRatingLbl.setForeground(Style.TEA_GREEN);
		maxRating = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
		maxRating.setFont(Style.BASE_FONT);
		maxRating.setBackground(Style.TEA_GREEN);
		maxRating.setForeground(Style.BALTIC_BLUE);
		maxRating.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		JSpinner.DefaultEditor maxeditor = (JSpinner.DefaultEditor) maxRating.getEditor();
		JTextField maxratingtextfield = maxeditor.getTextField();
		maxratingtextfield.setBackground(Style.TEA_GREEN);
		maxratingtextfield.setForeground(Style.BALTIC_BLUE);
		maxratingtextfield.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));

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
		searchButton.setBackground(Style.LIGHT_GREEN);
		searchButton.setForeground(Style.BALTIC_BLUE);
		searchButton.setFont(Style.BASE_FONT);
		ui.addButtonImg(searchButton, new ImageIcon("assets/UI/searchicon.png"), 20, 30, 30);
		searchButton.addActionListener(e -> {
			clearListTable(); // clears the table so ready for adding
			String nameToCheck = nameFilter.getText();
			// TODO make work here
			// refer to selectedOptions moniaga string list (has docs)
			int minRatingToCheck = (int) minRating.getValue();
			int maxRatingToCheck = (int) maxRating.getValue();
			boolean canBeMovie = movieType.isSelected();
			boolean canBeShow = showType.isSelected();
			boolean canBeAnime = animeType.isSelected();

			// Gets all Media that Fits Filter
			Media[] vaildResponse = ui.findMedia(canBeMovie, canBeShow, canBeAnime, true, true, true, true, true,
					nameToCheck,
					minRatingToCheck, maxRatingToCheck);
			// Add the Values to the Table
			for (Media media : vaildResponse) {
				addToListTable(media);

			}
		});

		gbc.gridy = 6; // row 7
		gbc.gridx = 0; // col 1
		filterPanel.add(searchButton, gbc);
	}

	void addRefreshButton() {
		refreshButton = new JButton("Refresh");
		refreshButton.setBackground(Style.LIGHT_GREEN);
		refreshButton.setForeground(Style.BALTIC_BLUE);
		refreshButton.setFont(Style.BASE_FONT);
		ui.addButtonImg(refreshButton, new ImageIcon("assets/UI/changeicon.png"), 20, 30, 30);
		refreshButton.addActionListener(e -> {
			clearListTable();
			addDefaultListToTable();
		});

		gbc.gridy = 6; // row 7
		gbc.gridx = 1; // col 2
		filterPanel.add(refreshButton, gbc);
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
				if (column == 0) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}
		};
		listTable = new JTable(listTableModel);
		listTable.getTableHeader().setReorderingAllowed(false);
		listTable.getTableHeader().setResizingAllowed(false);
		listTable.getTableHeader().setBackground(Style.TROPICAL_TEAL);
		listTable.getTableHeader().setForeground(Style.TEA_GREEN);
		listTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		listTable.setRowHeight(POSTER_HEIGHT); // for poster height accounting

		// column resizizing
		listTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		TableColumnModel cM = listTable.getColumnModel();
		cM.getColumn(0).setPreferredWidth(POSTER_WIDTH);
		cM.getColumn(1).setPreferredWidth(500);
		cM.getColumn(2).setPreferredWidth(90);
		cM.getColumn(3).setPreferredWidth(50);
		cM.getColumn(4).setPreferredWidth(50);
		// cM.getColumn(5).setPreferredWidth(60);

		// set table renderer for main objects
		listTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable t, Object val,
					boolean isSelected, boolean hasFocus, int row, int col) {

				super.getTableCellRendererComponent(t, val, isSelected, hasFocus, row, col);

				setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

				setFont(Style.BASE_FONT);

				setBackground(Style.EMERALD);
				setForeground(Color.WHITE);

				return this;
			}
		});

		// set table renderer for imageicon
		listTable.setDefaultRenderer(ImageIcon.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable t, Object val,
					boolean isSelected, boolean hasFocus, int row, int col) {

				super.getTableCellRendererComponent(t, val, isSelected, hasFocus, row, col);

				setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

				if (val instanceof Icon) {
					setIcon((ImageIcon)val);
					setText("");
				}

				setBackground(Style.EMERALD);
				setForeground(Color.WHITE);

				return this;
			}
		});

		// do header mods
		listTable.getTableHeader().setFont(Style.HEADER_FONT);

		tableScrollContainer = new JScrollPane(listTable);
		tableScrollContainer.setBackground(Style.BALTIC_BLUE);
		tableScrollContainer.setBorder(border);
		tableScrollContainer.getViewport().setBackground(PageColor);
		tableScrollContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 500));

		contentPanel.add(filterPanel, BorderLayout.WEST);
		contentPanel.add(tableScrollContainer, BorderLayout.CENTER);
		JPanel bufferPanel = new JPanel();
		bufferPanel.setBackground(PageColor);
		bufferPanel.setPreferredSize(new Dimension(0, 80));
		contentPanel.add(bufferPanel, BorderLayout.SOUTH);
	}

	public void addDefaultListToTable() {
		Media[] foundMedia = ui.findMedia(true, true, true, true, true, true, true, true, "", 0, 10);
		for (Media media : foundMedia) {
			addToListTable(media);
		}
	}

	void addToListTable(Media media) {
		Object[] toAddToTable = new Object[colNames.length];
		// "Icon", "Name", "Status", "Rating", "Last EP", "Rewatch"
		File posterFile = new File(media.getPosterPath());
		if (posterFile.exists()) {
			toAddToTable[0] = ui.resizeImg(new ImageIcon(posterFile.getPath()), POSTER_WIDTH, POSTER_HEIGHT);
		} else {
			toAddToTable[0] = ui.resizeImg(new ImageIcon(PATH_FOR_DEFAULT_IMAGE), POSTER_WIDTH, POSTER_HEIGHT);
		}

		// TODO verify all data gets pulled properly dependent on media
		toAddToTable[1] = media.getName();
		toAddToTable[2] = media.getStatus();
		toAddToTable[3] = media.getRating();
		toAddToTable[4] = media.getLastEpisode();
		toAddToTable[5] = media.getRewatched();

		listTableModel.addRow(toAddToTable);
	}

	void clearListTable() {
		listTableModel.setRowCount(0);
	}

	void getSelectedItems() {

	}
}
