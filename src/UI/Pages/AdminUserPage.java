package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO.LocalDB.User;
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

	protected final Border BORDER = BorderFactory.createLineBorder(Style.BORDER_COLOR, 4, true); // true allows for
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

	private void createContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
	}

	private void createTableTitleLbl() {
		tableTitleLbl = new JLabel("User DB");
		tableTitleLbl.setFont(Style.HEADER_FONT);
	}

	private void addTableTitleLbl() {
		contentPanel.add(tableTitleLbl, BorderLayout.NORTH);
	}

	private void createTable() {
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
		userTable.setRowHeight(30);

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

	private void addTable() {
		contentPanel.add(tableScrollPane, BorderLayout.CENTER);
	}

	private void createBtnPanel() {
		btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
	}

	private void createEditBtn() {
		editBtn = new JButton("Edit");
		editBtn.setFont(Style.BASE_FONT);
		editBtn.addActionListener(e -> editRow());
	}

	private void editRow() {
		// { "Id", "Username", "Password", "Is Admin", "Date Created", "Last Login" };
		// TODO get selected row and only create if valid
		if (userTable.getSelectedRow() != -1) {
			JDialog editWindow = new JDialog();
			editWindow.setTitle("Edit User Data");
			editWindow.setSize(new Dimension(800, 600));
			editWindow.setResizable(false);
			editWindow.setLayout(new GridLayout(7, 2, 20, 20));

			JLabel idLbl = new JLabel("Id: ");
			idLbl.setFont(Style.BASE_FONT);
			editWindow.add(idLbl);

			JTextField idEdit = new JTextField(18);
			idEdit.setFont(Style.BASE_FONT);
			editWindow.add(idEdit);

			JLabel usrLbl = new JLabel("Username: ");
			usrLbl.setFont(Style.BASE_FONT);
			editWindow.add(usrLbl);

			JTextField usrEdit = new JTextField(18);
			usrEdit.setFont(Style.BASE_FONT);
			editWindow.add(usrEdit);

			JLabel pwdLbl = new JLabel("Password: ");
			pwdLbl.setFont(Style.BASE_FONT);
			editWindow.add(pwdLbl);

			JTextField pwdEdit = new JTextField(18);
			pwdEdit.setFont(Style.BASE_FONT);
			editWindow.add(pwdEdit);

			JLabel isAdminLbl = new JLabel("Is Admin: ");
			isAdminLbl.setFont(Style.BASE_FONT);
			editWindow.add(isAdminLbl);

			JComboBox<String> isAdminEdit = new JComboBox<String>(new String[] { "true", "false" });
			isAdminEdit.setFont(Style.BASE_FONT);
			editWindow.add(isAdminEdit);

			JLabel dateCLbl = new JLabel("Date Created: ");
			dateCLbl.setFont(Style.BASE_FONT);
			editWindow.add(dateCLbl);

			JTextField dateCEdit = new JTextField(18);
			dateCEdit.setFont(Style.BASE_FONT);
			editWindow.add(dateCEdit);

			JLabel dateLLbl = new JLabel("Last Login: ");
			dateLLbl.setFont(Style.BASE_FONT);
			editWindow.add(dateLLbl);

			JTextField dateLEdit = new JTextField(18);
			dateLEdit.setFont(Style.BASE_FONT);
			editWindow.add(dateLEdit);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.setFont(Style.BASE_FONT);
			cancelButton.addActionListener(e -> {
				editWindow.dispose();
			});
			editWindow.add(cancelButton);

			JButton okButton = new JButton("Ok");
			okButton.setFont(Style.BASE_FONT);
			okButton.addActionListener(e -> {
				// TODO edit and update real variables

				editWindow.dispose();
			});
			editWindow.add(okButton);

			editWindow.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this,
					"No row selected. Please select a row of a show you would like to open.", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void addEditBtn() {
		btnPanel.add(editBtn);
	}

	private void createDelBtn() {
		delBtn = new JButton("Del");
		delBtn.setFont(Style.BASE_FONT);
	}

	private void addDelBtn() {
		btnPanel.add(delBtn);
	}

	private void addBtnPanel() {
		contentPanel.add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadData() {
		User[] users = ui.pullUsers();
		for (User user : users) {
			// { "Id", "Username", "Password", "Is Admin", "Date Created", "Last Login" };
			Object[] data = new Object[] { user.getId(), user.getUsername(), user.getPassword(), user.getIsAdmin(),
					user.getCreated(), user.getLastLogin() };
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
