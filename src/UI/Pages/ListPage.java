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
	private JPanel listPanel;
	private JTable listTable;

	private final String[] colNames = {"Icon", "Name", "Status", "Rating", "Last EP", "Rewatch"};
	private DefaultTableModel listTableModel;

	private final Border border = BorderFactory.createLineBorder(Color.BLACK, 2, true); // true allows for rounded

	/**
	 * Create the List Page
	 *
	 * @param ui The UI object that this page belongs to
	 */
	public ListPage(UI ui) {
		super(ui); // Uses the basic page layout and background color

		contentPanel = new JPanel();
		contentPanel.setBackground(PageColor);
		contentPanel.setLayout(new BorderLayout());

		// create filter panel
		filterPanel = new JPanel();
		filterPanel.setBackground(PageColor);
		filterPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// set rounded borders
		filterPanel.setBorder(border);
		// then simply lock the width
		filterPanel.setSize(new Dimension(150, 0));

		// add components to filter panel

		// three checkboxes in three different rows (type) (all require a label attached)
		// note row comments are not accurate (psa gridy = 0 -> row 1)
		JLabel movieTypeLbl = new JLabel("Movie: ");
		movieTypeLbl.setFont(Style.BASE_FONT);
		JCheckBox movieType = new JCheckBox();

		JLabel showTypeLbl = new JLabel("TV Show: ");
		showTypeLbl.setFont(Style.BASE_FONT);
		JCheckBox showType = new JCheckBox();

		JLabel animeTypeLbl = new JLabel("Anime: ");
		animeTypeLbl.setFont(Style.BASE_FONT);
		JCheckBox animeType = new JCheckBox();

		gbc.gridx = 0; // col 1
		gbc.gridy = 1; // row 1
		//gbc.insets = Style.LABEL_PADS;
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

		// name & status row
		// name is texfield and status is a dropdown
		JLabel nameFilterLbl = new JLabel("Name: ");
		nameFilterLbl.setFont(Style.BASE_FONT);
		JTextField nameFilter = new JTextField(10);
		nameFilter.setSize(new Dimension(100, 10));
		nameFilter.setFont(Style.BASE_FONT);

		JLabel statusFilterLbl = new JLabel("Status: ");
		statusFilterLbl.setFont(Style.BASE_FONT);
		String[] s1 = new String[] {"", "Undecided", "Backlog", "Watching", "Completed", "Dropped"};
		JComboBox<String> statusFilter = new JComboBox<String>(s1);
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

		// rating range row 2x JSpinners
		JLabel minRatingLbl = new JLabel("Min Rating: ");
		minRatingLbl.setFont(Style.BASE_FONT);
		JSpinner minRating = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		minRating.setFont(Style.BASE_FONT);

		JLabel maxRatingLbl = new JLabel("Max Rating: ");
		maxRatingLbl.setFont(Style.BASE_FONT);
		JSpinner maxRating = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
		maxRating.setFont(Style.BASE_FONT);

		minRating.addChangeListener(e -> {
			int minVal = (int) minRating.getValue();
			int maxVal = (int) maxRating.getValue();

			if (minVal > maxVal)
				maxRating.setValue(minVal);
 		});

		maxRating.addChangeListener(e -> {
			int minVal = (int) minRating.getValue();
			int maxVal = (int) maxRating.getValue();

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

		// search button on set row
		JButton searchButton = new JButton("Search");
		searchButton.setFont(Style.BASE_FONT);
		searchButton.addActionListener(e -> {
			// TODO: Implement db. also verify if selector for name and status are blank to not care
			String nameToCheck = nameFilter.getText();
			String statusToCheck = statusFilter.getSelectedItem().toString();
			int minRatingToCheck = (int) minRating.getValue();
			int maxRatingToCheck = (int) maxRating.getValue();
			boolean canBeMovie = movieType.isSelected();
			boolean canBeShow = showType.isSelected();
			boolean canBeAnime = animeType.isSelected();
			System.out.println();
		});

		gbc.gridy = 6; // row 7
		gbc.gridx = 0; // col 1
		filterPanel.add(searchButton);


		// create list panel
		listPanel = new JPanel();
		listPanel.setBackground(PageColor);
		listPanel.setLayout(new BorderLayout());

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
		JScrollPane tableScrollContainer = new JScrollPane(listTable);
		tableScrollContainer.setBorder(border);
		tableScrollContainer.getViewport().setBackground(PageColor);

		contentPanel.add(filterPanel, BorderLayout.WEST);
		contentPanel.add(tableScrollContainer, BorderLayout.CENTER);

		this.add(contentPanel);
	}
}
