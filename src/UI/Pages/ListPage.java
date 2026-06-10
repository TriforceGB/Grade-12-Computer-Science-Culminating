package UI.Pages;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import UI.Style;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

		// three checkboxes in a row (type) (all require a label attached)
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
		gbc.gridy = 0; // row 1
		gbc.insets = Style.LABEL_PADS;
		filterPanel.add(movieTypeLbl, gbc);

		gbc.gridx = 1; // col 2
		filterPanel.add(movieType, gbc);

		gbc.gridx = 2; // col 3
		filterPanel.add(showTypeLbl, gbc);

		gbc.gridx = 3; // col 4
		filterPanel.add(showType, gbc);

		gbc.gridx = 4; // col5
		filterPanel.add(animeTypeLbl, gbc);

		gbc.gridx = 5; // col 6
		filterPanel.add(animeType, gbc);

		// name & status row
		// name is texfield and status is a dropdown

		// rating range row 2x JSpinners

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
