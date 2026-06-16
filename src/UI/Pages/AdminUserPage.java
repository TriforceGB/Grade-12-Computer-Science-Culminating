package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import UI.Style;
import UI.UI;

public class AdminUserPage extends Page {

	JPanel contentPanel;
	JLabel tableTitleLbl;

	JScrollPane tableScrollPane;
	final String[] colNames = { "Id", "Username", "Password", "Is Admin", "Date Created", "Last Login" };
	JTable userTable;
	DefaultTableModel tableModel;

	JPanel btnPanel;
	JButton editBtn;
	JButton delBtn;

	private final Border BORDER = BorderFactory.createLineBorder(Style.BORDER_COLOR, 4, true); // true allows for
																								// rounded

	public AdminUserPage(UI ui) {
		super(ui);

		createContentPanel();

		createTableTitleLbl();
		addTableTitleLbl();

		createTable();
		addTable();

		createBtnPanel();
		createEditBtn();
		createDelBtn();

		addEditBtn();
		addDelBtn();
		addBtnPanel();

		this.add(contentPanel, BorderLayout.CENTER);
	}

	void createContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
	}

	void createTableTitleLbl() {
		tableTitleLbl = new JLabel("User DB");
		tableTitleLbl.setFont(Style.HEADER_FONT);
	}

	void addTableTitleLbl() {
		contentPanel.add(tableTitleLbl, BorderLayout.NORTH);
	}

	void createTable() {
		tableModel = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		userTable = new JTable(tableModel);
		userTable.getTableHeader().setReorderingAllowed(false);
		userTable.getTableHeader().setResizingAllowed(false);
		userTable.getTableHeader().setBackground(Style.TROPICAL_TEAL);
		userTable.getTableHeader().setForeground(Style.TEA_GREEN);
		userTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR));
		userTable.setRowHeight(24);

		userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable t, Object val,
					boolean isSelected, boolean hasFocus, int row, int col) {

				super.getTableCellRendererComponent(t, val, isSelected, hasFocus, row, col);

				setFont(Style.BASE_FONT);

				setBackground(Style.EMERALD);
				setForeground(Color.WHITE);

				return this;
			}
		});

		userTable.getTableHeader().setFont(Style.HEADER_FONT);
		tableScrollPane = new JScrollPane(userTable);
		tableScrollPane.setBackground(Style.BALTIC_BLUE);
		tableScrollPane.setBorder(BORDER);
		tableScrollPane.getViewport().setBackground(PageColor);
	}

	void addTable() {
		contentPanel.add(tableScrollPane, BorderLayout.CENTER);
	}

	void createBtnPanel() {
		btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
	}

	void createEditBtn() {
		editBtn = new JButton("Edit");
		editBtn.setFont(Style.BASE_FONT);
	}

	void addEditBtn() {
		btnPanel.add(editBtn);
	}

	void createDelBtn() {
		delBtn = new JButton("Del");
		delBtn.setFont(Style.BASE_FONT);
	}

	void addDelBtn() {	
		btnPanel.add(delBtn);
	}

	void addBtnPanel() {
		contentPanel.add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadData() {
		for (int i = 0; i < 50; i++) {
			// { "Id", "Username", "Password", "Is Admin", "Date Created", "Last Login" };
			Object[] data = new Object[] {"42", "Zach", "ZachIsGreat", "true", "07/07/1991", "09/08/2000"};
			tableModel.addRow(data);
		}
	}

	@Override
	protected void pageHeader() {
		JPanel header = new JPanel(); // Create the Main Pane
		// Settings for the Header
		header.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20)); // Sets the Border of the Header
		header.setLayout(new GridLayout(1, 4, 20, 20)); // Set the Layout of the Header
		header.setPreferredSize(new Dimension(0, 50)); // Sets the Size of the Header
		header.setBackground(this.PageColor); // Sets the Color to Match the Page

		// Buttons
		JButton userDbBtn = new JButton("User DB");
		JButton mediaDbBtn = new JButton("Media DB");
		JButton backBtn = new JButton("Back");
		JButton exitBtn = new JButton("Exit");

		// Fonts

		// Colours

		// Images
		ImageIcon userDbIcon = ui.resizeImg(new ImageIcon("assets/UI/userdbicon.png"), 30, 30);
		userDbBtn.setFont(Style.BASE_FONT);
		userDbBtn.setBackground(Style.LIGHT_GREEN);
		userDbBtn.setForeground(Style.BALTIC_BLUE);
		userDbBtn.setIcon(userDbIcon);
		userDbBtn.setHorizontalAlignment(JLabel.RIGHT);
		userDbBtn.setHorizontalAlignment(SwingConstants.CENTER);
		userDbBtn.setVerticalAlignment(SwingConstants.CENTER);
		userDbBtn.setIconTextGap(20);
		userDbBtn.setFocusable(false);

		ImageIcon mediaDbIcon = ui.resizeImg(new ImageIcon("assets/UI/mediadbicon.png"), 30, 30);
		mediaDbBtn.setFont(Style.BASE_FONT);
		mediaDbBtn.setBackground(Style.LIGHT_GREEN);
		mediaDbBtn.setForeground(Style.BALTIC_BLUE);
		mediaDbBtn.setIcon(mediaDbIcon);
		mediaDbBtn.setHorizontalAlignment(JLabel.RIGHT);
		mediaDbBtn.setHorizontalAlignment(SwingConstants.CENTER);
		mediaDbBtn.setVerticalAlignment(SwingConstants.CENTER);
		mediaDbBtn.setIconTextGap(20);
		mediaDbBtn.setFocusable(false);

		ImageIcon backIcon = ui.resizeImg(new ImageIcon("assets/UI/backicon.png"), 30, 30);
		backBtn.setFont(Style.BASE_FONT);
		backBtn.setBackground(Style.LIGHT_GREEN);
		backBtn.setForeground(Style.BALTIC_BLUE);
		backBtn.setIcon(backIcon);
		backBtn.setHorizontalAlignment(JLabel.RIGHT);
		backBtn.setHorizontalAlignment(SwingConstants.CENTER);
		backBtn.setVerticalAlignment(SwingConstants.CENTER);
		backBtn.setIconTextGap(20);
		backBtn.setFocusable(false);

		ImageIcon exiticon = ui.resizeImg(new ImageIcon("assets/UI/exiticon.png"), 30, 30);
		exitBtn.setFont(Style.BASE_FONT);
		exitBtn.setBackground(Style.LIGHT_GREEN);
		exitBtn.setForeground(Style.BALTIC_BLUE);
		exitBtn.setIcon(exiticon);
		exitBtn.setHorizontalAlignment(JLabel.RIGHT);
		exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
		exitBtn.setVerticalAlignment(SwingConstants.CENTER);
		exitBtn.setIconTextGap(20);
		exitBtn.setFocusable(false);

		// Action listener
		userDbBtn.addActionListener(e -> ui.switchPanel("adminUsr"));
		mediaDbBtn.addActionListener(e -> ui.switchPanel("adminMedia"));
		backBtn.addActionListener(e -> ui.switchPanel("setting"));
		exitBtn.addActionListener(e -> ui.logout());

		header.add(userDbBtn);
		header.add(mediaDbBtn);
		header.add(backBtn);
		header.add(exitBtn);

		// add the header to the page
		this.add(header, BorderLayout.NORTH);
	}
}
