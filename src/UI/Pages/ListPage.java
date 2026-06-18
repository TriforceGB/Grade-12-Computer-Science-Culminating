package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
	private Media[] Response;

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
	// used for the multi-select combo box
	private final char CHECKBOX_CHAR = '☒';
	private final char UNCHECKBOX_CHAR = '☐';
	private final String[] SHOW_STATUS_DEFAULT_OPTIONS = new String[] { "Undecided", "Dropped", "Backlog", "Watching",
			"Completed" };
	private final String[] SHOW_STATUS_COMBO_OPTIONS = new String[] { "All", "Undecided " + CHECKBOX_CHAR,
			"Backlog " + CHECKBOX_CHAR, "Watching " + CHECKBOX_CHAR,
			"Completed " + CHECKBOX_CHAR, "Dropped " + CHECKBOX_CHAR }; // space seperated checkbox representations
	private MoniagaStringList selectedOptions = new MoniagaStringList(SHOW_STATUS_DEFAULT_OPTIONS);
	// see MoniagaStringList file for more specifications on how the internals of
	// that array work

	private JLabel minRatingLbl;
	private JSpinner minRating;

	private JLabel maxRatingLbl;
	private JSpinner maxRating;

	private JPanel btnWrapper;
	private JButton searchButton;
	private JButton resetButton;
	private JButton openMedia;

	private final String PATH_FOR_DEFAULT_IMAGE = "assets/UI/filal.png";
	private final int POSTER_WIDTH = 100;
	private final int POSTER_HEIGHT = 150;

	private final Border BORDER = BorderFactory.createLineBorder(Style.BORDER_COLOR, 4, true); // true allows for
																								// rounded

	private final int CPERLINE_TITLE = 38;
	private final int MAXPASS = 5;

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
		// create a wrraper to store the buttons in better layout
		btnWrapper = new JPanel(new GridLayout());
		addSearchButton();
		addResetButton();
		addOpenMedia();

		// standard gridbaglayout settings to place in correct location
		gbc.gridy = 8;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		// add it along with everything else
		filterPanel.add(btnWrapper, gbc);

		gbc = new GridBagConstraints(); // reset for safety
		createListPanel();

		addTableToListPanel();
	}

	private void createFilterPanel() {
		// create filter panel
		filterPanel = new JPanel();
		filterPanel.setBackground(PageColor);
		filterPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		// set rounded borders
		filterPanel.setBorder(BORDER);
		// then simply lock the width
		filterPanel.setSize(new Dimension(150, 0));
	}

	private void createContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(PageColor);

		// add content panel to main panel
		this.add(contentPanel);
	}

	private void addTypeCheckboxes() {
		// three checkboxes in three different rows (type) (all require a label
		// attached)
		// note row comments are not accurate (psa gridy = 0 -> row 1)
		movieTypeLbl = new JLabel("Movie: ");
		movieTypeLbl.setFont(Style.BASE_FONT);
		movieTypeLbl.setForeground(Style.TEA_GREEN);
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

	private void addNameStatusButtons() {
		// name & status row
		// name is textfield and status is a dropdown
		nameFilterLbl = new JLabel("Name: ");
		nameFilterLbl.setFont(Style.BASE_FONT);
		nameFilterLbl.setForeground(Style.TEA_GREEN);
		nameFilter = new JTextField(10);
		nameFilter.setSize(new Dimension(100, 10));
		nameFilter.setFont(Style.BASE_FONT);
		nameFilter.setBackground(Style.TEA_GREEN);
		nameFilter.setForeground(Style.BALTIC_BLUE);
		nameFilter.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));

		statusFilterLbl = new JLabel("Status: ");
		statusFilterLbl.setFont(Style.BASE_FONT);
		statusFilterLbl.setForeground(Style.TEA_GREEN);
		statusFilter = new JComboBox<String>(SHOW_STATUS_COMBO_OPTIONS);
		statusFilter.setFont(Style.BASE_FONT);
		statusFilter.setBackground(Style.TEA_GREEN);
		statusFilter.setForeground(Style.BALTIC_BLUE);
		statusFilter.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
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
		gbc.insets = new Insets(40, 0, 0, 0);
		filterPanel.add(nameFilterLbl, gbc);

		gbc.gridx = 1; // col 2
		gbc.fill = GridBagConstraints.HORIZONTAL;
		filterPanel.add(nameFilter, gbc);

		gbc.gridy = 5; // row 6
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.fill = GridBagConstraints.NONE;
		filterPanel.add(statusFilterLbl, gbc);

		gbc.gridx = 1; // col 2
		gbc.fill = GridBagConstraints.HORIZONTAL;
		filterPanel.add(statusFilter, gbc);

		gbc = new GridBagConstraints();
	}

	private void addRatingSelectorButtons() {
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
		minRating.setBorder(BorderFactory.createEmptyBorder());
		minratingtextfield.setBackground(Style.TEA_GREEN);
		minratingtextfield.setForeground(Style.BALTIC_BLUE);
		minratingtextfield.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));

		maxRatingLbl = new JLabel("Max Rating: ");
		maxRatingLbl.setFont(Style.BASE_FONT);
		maxRatingLbl.setForeground(Style.TEA_GREEN);
		maxRating = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
		maxRating.setFont(Style.BASE_FONT);
		maxRating.setBackground(Style.TEA_GREEN);
		maxRating.setForeground(Style.BALTIC_BLUE);
		maxRating.setBorder(BorderFactory.createEmptyBorder());
		JSpinner.DefaultEditor maxeditor = (JSpinner.DefaultEditor) maxRating.getEditor();
		JTextField maxratingtextfield = maxeditor.getTextField();
		maxratingtextfield.setBackground(Style.TEA_GREEN);
		maxratingtextfield.setForeground(Style.BALTIC_BLUE);
		maxratingtextfield.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));

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

		gbc.gridy = 6; // row 7
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(40, 0, 0, 0);
		filterPanel.add(minRatingLbl, gbc);

		gbc.gridx = 1; // col 2
		filterPanel.add(minRating, gbc);

		gbc.gridy = 7; // row 8
		gbc.gridx = 0; // col 1
		gbc.insets = new Insets(0, 0, 40, 0);
		filterPanel.add(maxRatingLbl, gbc);

		gbc.gridx = 1; // col 2
		gbc.insets = new Insets(0, 0, 40, 0);
		filterPanel.add(maxRating, gbc);

		gbc = new GridBagConstraints(); // reset for safety
	}

	private void addSearchButton() {
		// search button on set row
		searchButton = new JButton("Search");
		searchButton.setBackground(Style.LIGHT_GREEN);
		searchButton.setForeground(Style.BALTIC_BLUE);
		searchButton.setFont(Style.BASE_FONT);
		ui.addButtonImg(searchButton, new ImageIcon("assets/UI/searchicon.png"), 20, 30, 30);
		searchButton.setFocusable(false);
		searchButton.addActionListener(e -> {
			clearListTable(); // clears the table so ready for adding
			String nameToCheck = nameFilter.getText();

			// Fix Until we Fix the Box
			boolean isUndecided = false;
			boolean isDropped = false;
			boolean isBacklog = false;
			boolean isWatched = false;
			boolean isCompleted = false;
			// identify what is currently selected from msl
			for (int i = 0; i < selectedOptions.count(); i++) {
				char c = selectedOptions.getAt(i).toLowerCase().charAt(0);
				switch (c) {
					case 'u':
						isUndecided = true;
						break;
					case 'd':
						isDropped = true;
						break;
					case 'b':
						isBacklog = true;
						break;
					case 'w':
						isWatched = true;
						break;
					case 'c':
						isCompleted = true;
						break;
				}
			}
			// refer to selectedOptions Moniaga string list (has docs)
			int minRatingToCheck = (int) minRating.getValue();
			int maxRatingToCheck = (int) maxRating.getValue();
			boolean canBeMovie = movieType.isSelected();
			boolean canBeShow = showType.isSelected();
			boolean canBeAnime = animeType.isSelected();

			// Gets all Media that Fits Filter
			Response = ui.findMedia(canBeMovie, canBeShow, canBeAnime, isUndecided, isDropped, isBacklog,
					isWatched, isCompleted,
					nameToCheck,
					minRatingToCheck, maxRatingToCheck);
			// Add the Values to the Table
			for (Media media : Response) {
				addToListTable(media);
			}
		});

		btnWrapper.add(searchButton);
	}

	// reset button applies default search filters in casse filters get broken
	private void addResetButton() {
		resetButton = new JButton("Reset");
		resetButton.setBackground(Style.LIGHT_GREEN);
		resetButton.setForeground(Style.BALTIC_BLUE);
		resetButton.setFont(Style.BASE_FONT);
		ui.addButtonImg(resetButton, new ImageIcon("assets/UI/changeicon.png"), 20, 30, 30);
		resetButton.setFocusable(false);
		resetButton.addActionListener(e -> resetfunction());

		btnWrapper.add(resetButton);
	}

	public void resetfunction() {
		clearListTable();
		addDefaultListToTable();
	}

	private void addOpenMedia() {
		openMedia = new JButton("Open Media");
		openMedia.setBackground(Style.LIGHT_GREEN);
		openMedia.setForeground(Style.BALTIC_BLUE);
		openMedia.setFont(Style.BASE_FONT);
		ui.addButtonImg(openMedia, new ImageIcon("assets/UI/exporticon.png"), 20, 30, 30);
		openMedia.addActionListener(e -> {
			int row = listTable.getSelectedRow();

			if (row != -1) {
				Media show = this.Response[row]; // Gets Show User has Selected

				ui.openMediaPage(show, "list");
			} else { // no row selected
				JOptionPane.showMessageDialog(this,
						"No row selected. Please select a row of a show you would like to open.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		btnWrapper.add(openMedia);
	}

	private void createListPanel() {
		// create list panel
		listPanel = new JPanel();
		listPanel.setBackground(PageColor);
		listPanel.setLayout(new BorderLayout());
	}

	private void addTableToListPanel() {
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

		// column resizing
		listTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnModel cM = listTable.getColumnModel();
		cM.getColumn(0).setPreferredWidth(POSTER_WIDTH);
		cM.getColumn(1).setPreferredWidth(450);
		cM.getColumn(2).setPreferredWidth(90);
		cM.getColumn(3).setPreferredWidth(50);
		cM.getColumn(4).setPreferredWidth(50);
		cM.getColumn(5).setPreferredWidth(80);

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
					setIcon((ImageIcon) val);
					setText("");
				}

				setBackground(Style.EMERALD);
				setForeground(Color.WHITE);

				return this;
			}
		});

		// do on double click open media btn
		// borrowed code implenation from gemini adding in direct functionality to what is needed on our open media btn
		listTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check if it's a double click
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					Point point = e.getPoint();
					int row = target.rowAtPoint(point); // Find visual row index clicked

					// Verify the click happened on a valid row item
					if (row != -1) {
						// Convert visual view row index to data model row index
						int modelRow = target.convertRowIndexToModel(row);

						Media show = Response[modelRow]; // Gets Show User has Selected

						ui.openMediaPage(show, "list");
					}
				}
			}
		});

		// do header mods
		listTable.getTableHeader().setFont(Style.HEADER_FONT);

		tableScrollContainer = new JScrollPane(listTable);
		tableScrollContainer.setBackground(Style.BORDER_COLOR);
		tableScrollContainer.setBorder(BORDER);
		tableScrollContainer.getViewport().setBackground(PageColor);
		tableScrollContainer.setMaximumSize(new Dimension(Short.MAX_VALUE, 500));
		// apply styling to the vertial scroll bar
		tableScrollContainer.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Style.BORDER_COLOR;
				this.trackColor = Style.TEA_GREEN;
			}
		});

		tableScrollContainer.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));

		contentPanel.add(filterPanel, BorderLayout.WEST);
		contentPanel.add(tableScrollContainer, BorderLayout.CENTER);
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, false));
		contentPanel.setBackground(Style.TEA_GREEN);

		JPanel bufferPanel = new JPanel();
		bufferPanel.setBackground(PageColor);
		bufferPanel.setPreferredSize(new Dimension(0, 80));
		contentPanel.add(bufferPanel, BorderLayout.SOUTH);
	}

	public void addDefaultListToTable() {
		this.Response = ui.findMedia(true, true, true, true, true, true, true, true, "", 0, 10);
		for (Media media : this.Response) {
			addToListTable(media);
		}
	}

	private void addToListTable(Media media) {
		Object[] toAddToTable = new Object[colNames.length];
		// "Icon", "Name", "Status", "Rating", "Last EP", "Rewatch"
		File posterFile = new File(media.getPosterPath());
		if (posterFile.exists()) {
			toAddToTable[0] = ui.resizeImg(new ImageIcon(posterFile.getPath()), POSTER_WIDTH, POSTER_HEIGHT);
		} else {
			toAddToTable[0] = ui.resizeImg(new ImageIcon(PATH_FOR_DEFAULT_IMAGE), POSTER_WIDTH, POSTER_HEIGHT);
		}

		toAddToTable[1] = ui.getHtmlFormatText(media.getName(), CPERLINE_TITLE, MAXPASS, 300);
		toAddToTable[2] = ui.getStatusString(media.getStatus());
		toAddToTable[3] = media.getRating();
		toAddToTable[4] = media.getLastEpisode();
		toAddToTable[5] = media.getRewatched();

		listTableModel.addRow(toAddToTable);
	}

	private void clearListTable() {
		listTableModel.setRowCount(0);
	}
}
