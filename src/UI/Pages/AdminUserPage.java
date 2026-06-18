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

	private User[] userList;

	private JPanel contentPanel;
	private JLabel tableTitleLbl;

	private JScrollPane tableScrollPane;
	private final String[] colNames = { "Id", "Username", "Password", "Is Admin", "Date Created", "Last Login" };
	private JTable userTable;
	private DefaultTableModel tableModel;

	private JPanel btnPanel;
	private JButton editBtn;
	private JButton delBtn;
	private JButton wipeUsersBtn;

	// true allows for rounded
	protected final Border BORDER = BorderFactory.createLineBorder(Style.BORDER_COLOR, 2, true);

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
		createWipeBtn();

		addEditBtn();
		addDelBtn();
		addWipeBtn();
		addBtnPanel();

		this.add(contentPanel, BorderLayout.CENTER);
	}

	private void createContentPanel() {
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(Style.TROPICAL_TEAL);
	}

	private void createTableTitleLbl() {
		tableTitleLbl = new JLabel("User DB");
		tableTitleLbl.setFont(Style.HEADER_FONT);
		tableTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		tableTitleLbl.setFont(Style.HEADER_FONT);

		tableTitleLbl.setForeground(Style.TEA_GREEN);
		tableTitleLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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
		btnPanel.setBackground(Style.BALTIC_BLUE);
	}

	private void createEditBtn() {
		editBtn = new JButton("Edit User Data");
		editBtn.setFont(Style.BASE_FONT);
		editBtn.setBackground(Style.LIGHT_GREEN);
		editBtn.setForeground(Style.BALTIC_BLUE);
		ui.addButtonImg(editBtn, new ImageIcon("assets/UI/editicon.png"), 20, 30, 30);
		editBtn.addActionListener(e -> editRow(userTable.getSelectedRow()));
	}

	private void createWipeBtn() {
		wipeUsersBtn = new JButton("Wipe User Data");
		wipeUsersBtn.setFont(Style.BASE_FONT);
		wipeUsersBtn.setBackground(Style.LIGHT_GREEN);
		wipeUsersBtn.setForeground(Style.BALTIC_BLUE);
		ui.addButtonImg(wipeUsersBtn, new ImageIcon("assets/UI/shredicon.png"), 20, 30, 30);
		wipeUsersBtn.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to wipe all user data? This Will Remove Every Account Including You and Log you Out",
					"Confirm Wipe",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				if (ui.remakeUserTable()) {
					JOptionPane.showMessageDialog(this, "User data wiped successfully. You will be logged out.",
							"Success", JOptionPane.INFORMATION_MESSAGE);
					ui.logout();
				}
			}
		});
	}

	private void addWipeBtn() {
		btnPanel.add(wipeUsersBtn);
	}

	private void editRow(int selectedRow) {
		// { "Id", "Username", "Password", "Is Admin", "Date Created", "Last Login" };
		// TODO get selected row and only create if valid
		if (selectedRow != -1) {
			User editedUser = userList[selectedRow];
			JDialog editWindow = new JDialog();
			editWindow.setLocationRelativeTo(ui);
			editWindow.setModal(true);

			editWindow.setTitle("Edit User Data");
			editWindow.setSize(new Dimension(800, 600));
			editWindow.setResizable(false);
			editWindow.setLayout(new GridLayout(7, 2, 20, 20));
			editWindow.getContentPane().setBackground(Style.BALTIC_BLUE);

			JLabel idLbl = new JLabel("Id: ");
			idLbl.setFont(Style.BASE_FONT);
			idLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(idLbl);

			JTextField idEdit = new JTextField(18);
			idEdit.setFont(Style.BASE_FONT);
			idEdit.setBackground(Style.TEA_GREEN);
			idEdit.setForeground(Style.BALTIC_BLUE);
			idEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			idEdit.setText(String.valueOf(editedUser.getId()));
			idEdit.setEditable(false);
			editWindow.add(idEdit);

			JLabel usrLbl = new JLabel("Username: ");
			usrLbl.setFont(Style.BASE_FONT);
			usrLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(usrLbl);

			JTextField usrEdit = new JTextField(18);
			usrEdit.setFont(Style.BASE_FONT);
			usrEdit.setBackground(Style.TEA_GREEN);
			usrEdit.setForeground(Style.BALTIC_BLUE);
			usrEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			usrEdit.setText(editedUser.getUsername());
			editWindow.add(usrEdit);

			JLabel pwdLbl = new JLabel("Password: ");
			pwdLbl.setFont(Style.BASE_FONT);
			pwdLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(pwdLbl);

			JTextField pwdEdit = new JTextField(18);
			pwdEdit.setFont(Style.BASE_FONT);
			pwdEdit.setBackground(Style.TEA_GREEN);
			pwdEdit.setForeground(Style.BALTIC_BLUE);
			pwdEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			pwdEdit.setText(editedUser.getPassword());
			editWindow.add(pwdEdit);

			JLabel isAdminLbl = new JLabel("Is Admin: ");
			isAdminLbl.setFont(Style.BASE_FONT);
			isAdminLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(isAdminLbl);

			JComboBox<String> isAdminEdit = new JComboBox<String>(new String[] { "true", "false" });
			isAdminEdit.setFont(Style.BASE_FONT);
			isAdminEdit.setBackground(Style.TEA_GREEN);
			isAdminEdit.setForeground(Style.BALTIC_BLUE);
			isAdminEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			isAdminEdit.setFocusable(false);
			if (editedUser.getIsAdmin()) {
				isAdminEdit.setSelectedIndex(0);
			} else {
				isAdminEdit.setSelectedIndex(1);
			}
			editWindow.add(isAdminEdit);

			JLabel dateCLbl = new JLabel("Date Created: ");
			dateCLbl.setFont(Style.BASE_FONT);
			dateCLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(dateCLbl);

			JTextField dateCEdit = new JTextField(18);
			dateCEdit.setFont(Style.BASE_FONT);
			dateCEdit.setBackground(Style.TEA_GREEN);
			dateCEdit.setForeground(Style.BALTIC_BLUE);
			dateCEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			dateCEdit.setText(editedUser.getCreated());
			dateCEdit.setEditable(false);
			editWindow.add(dateCEdit);

			JLabel dateLLbl = new JLabel("Last Login: ");
			dateLLbl.setFont(Style.BASE_FONT);
			dateLLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(dateLLbl);

			JTextField dateLEdit = new JTextField(18);
			dateLEdit.setFont(Style.BASE_FONT);
			dateLEdit.setBackground(Style.TEA_GREEN);
			dateLEdit.setForeground(Style.BALTIC_BLUE);
			dateLEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			dateLEdit.setText(editedUser.getLastLogin());
			dateLEdit.setEditable(false);
			editWindow.add(dateLEdit);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.setFont(Style.BASE_FONT);
			cancelButton.setBackground(Style.LIGHT_GREEN);
			cancelButton.setForeground(Style.BALTIC_BLUE);
			ui.addButtonImg(cancelButton, new ImageIcon("assets/UI/xicon.png"), 20, 30, 30);
			cancelButton.addActionListener(e -> {
				editWindow.dispose();
			});
			editWindow.add(cancelButton);

			JButton okButton = new JButton("Ok");
			okButton.setFont(Style.BASE_FONT);
			okButton.setBackground(Style.LIGHT_GREEN);
			okButton.setForeground(Style.BALTIC_BLUE);
			ui.addButtonImg(okButton, new ImageIcon("assets/UI/okicon.png"), 20, 40, 40);

			okButton.addActionListener(e -> {
				if (ui.getId() == editedUser.getId()) {
					JOptionPane.showMessageDialog(this,
							"Unable to Edit Yourself", "Error",
							JOptionPane.ERROR_MESSAGE);
					editWindow.dispose();
				}
				editedUser.setUsername(usrEdit.getText());
				editedUser.setPassword(pwdEdit.getText());

				editedUser.setAdmin(isAdminEdit.getSelectedIndex() == 0);
				if (ui.editUser(editedUser)) {
					JOptionPane.showMessageDialog(this,
							"Change to User was Made", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,
							"Failed to Update User", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				loadData();
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
		delBtn = new JButton("Delete User");
		delBtn.setFont(Style.BASE_FONT);
		delBtn.setBackground(Style.LIGHT_GREEN);
		delBtn.setForeground(Style.BALTIC_BLUE);
		ui.addButtonImg(delBtn, new ImageIcon("assets/UI/binicon.png"), 20, 30, 30);

		delBtn.addActionListener(e -> {
			int selectedRow = userTable.getSelectedRow();
			if (selectedRow != -1) {
				User deleteUser = userList[selectedRow];

				int result = JOptionPane.showConfirmDialog(this,
						"Are you sure you want to delete %s?".formatted(deleteUser.getUsername()), "Delete User",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					ui.deleteUser(deleteUser);
					loadData();
				}

			}
		});
	}

	private void addDelBtn() {
		btnPanel.add(delBtn);
	}

	private void addBtnPanel() {
		contentPanel.add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadData() {
		tableModel.setRowCount(0);
		userList = ui.pullUsers();
		for (User user : userList) {
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
		userDbBtn.addActionListener(e -> {
			ui.switchPanel("adminUsr");
		});
		mediaDbBtn.addActionListener(e -> {
			ui.switchPanel("adminMedia");
		});
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
