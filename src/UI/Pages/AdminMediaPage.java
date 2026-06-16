package UI.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import UI.Style;
import UI.UI;

/**
 * Admin page base for Admin Settings.
 * Other pages in the admin settings will use this as a base.
 */
public class AdminMediaPage extends AdminUserPage {

	JPanel contentPanel;
	JLabel tableTitleLbl;

	JScrollPane tableScrollPane;
	final String[] colNames = { "Id", "Type", "Name", "Ep. Count", "PosterPath", "PosterLink" };
	JTable userTable;
	DefaultTableModel tableModel;

	JPanel btnPanel;
	JButton editBtn;
	JButton delBtn;

	public AdminMediaPage(UI ui) {
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
		tableTitleLbl = new JLabel("Media DB");
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
		// { "Id", "Type", "Name", "Ep. Count", "PosterPath", "PosterLink" };
		// TODO get selected row and only create if valid
		if (userTable.getSelectedRow() != -1) {
			JDialog editWindow = new JDialog();
			editWindow.setTitle("Edit Media Data");
			editWindow.setSize(new Dimension(800, 600));
			editWindow.setResizable(false);
			editWindow.setLayout(new GridLayout(7, 2, 20, 20));

			JLabel idLbl = new JLabel("Id: ");
			idLbl.setFont(Style.BASE_FONT);
			editWindow.add(idLbl);

			JTextField idEdit = new JTextField(18);
			idEdit.setFont(Style.BASE_FONT);
			editWindow.add(idEdit);

			JLabel typeLbl = new JLabel("Type: ");
			typeLbl.setFont(Style.BASE_FONT);
			editWindow.add(typeLbl);

			JTextField typeEdit = new JTextField(18);
			typeEdit.setFont(Style.BASE_FONT);
			editWindow.add(typeEdit);

			JLabel nameLbl = new JLabel("Name: ");
			nameLbl.setFont(Style.BASE_FONT);
			editWindow.add(nameLbl);

			JTextField nameEdit = new JTextField(18);
			nameEdit.setFont(Style.BASE_FONT);
			editWindow.add(nameEdit);

			JLabel epCountLbl = new JLabel("Ep Count: ");
			epCountLbl.setFont(Style.BASE_FONT);
			editWindow.add(epCountLbl);

			JSpinner epCountEdit = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
			epCountEdit.setFont(Style.BASE_FONT);
			editWindow.add(epCountEdit);

			JLabel dateCLbl = new JLabel("Poster Path: ");
			dateCLbl.setFont(Style.BASE_FONT);
			editWindow.add(dateCLbl);

			JTextField dateCEdit = new JTextField(18);
			dateCEdit.setFont(Style.BASE_FONT);
			editWindow.add(dateCEdit);

			JLabel dateLLbl = new JLabel("Poster Link: ");
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

	@Override
	public void loadData() {
		for (int i = 0; i < 50; i++) {
			// { "Id", "Type", "Name", "Ep. Count", "PosterPath", "PosterLink" };
			Object[] data = new Object[] { "42", "Movie", "RIP Zach", "12", "/dev/sda1", "www.spongebob.com" };
			tableModel.addRow(data);
		}
	}
}
