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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DTO.LocalDB.Media;
import UI.Style;
import UI.UI;

/**
 * Admin page base for Admin Settings.
 * Other pages in the admin settings will use this as a base.
 */
public class AdminMediaPage extends AdminUserPage {

	Media[] MediaTable;

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
		contentPanel.setBackground(Style.TROPICAL_TEAL);
	}

	private void createTableTitleLbl() {
		tableTitleLbl = new JLabel("Media DB");
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
		editBtn = new JButton("Edit Media Data");
		editBtn.setFont(Style.BASE_FONT);
		editBtn.setBackground(Style.LIGHT_GREEN);
		editBtn.setForeground(Style.BALTIC_BLUE); 
		ui.addButtonImg(editBtn, new ImageIcon("assets/UI/editicon.png"), 20, 30, 30);
		editBtn.addActionListener(e -> editRow());
	}

	private void editRow() {
		// { "Id", "Type", "Name", "Ep. Count", "PosterPath", "PosterLink" };
		// TODO get selected row and only create if valid
		if (userTable.getSelectedRow() != -1) {
			Media editedMedia = MediaTable[userTable.getSelectedRow()];
			JDialog editWindow = new JDialog();
			editWindow.setLocationRelativeTo(ui);
			editWindow.setModal(true);

			editWindow.setTitle("Edit Media Data");
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
			idEdit.setText(String.valueOf(editedMedia.getId()));
			idEdit.setEditable(false);
			editWindow.add(idEdit);

			JLabel typeLbl = new JLabel("Type: ");
			typeLbl.setFont(Style.BASE_FONT);
			typeLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(typeLbl);

			JComboBox<String> typeEdit = new JComboBox<String>(new String[] { "Movie", "TV Show", "Anime" });
			typeEdit.setFont(Style.BASE_FONT);
			typeEdit.setBackground(Style.TEA_GREEN);
			typeEdit.setForeground(Style.BALTIC_BLUE);
			typeEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			typeEdit.setSelectedIndex(editedMedia.getType() - 1);
			editWindow.add(typeEdit);

			JLabel nameLbl = new JLabel("Name: ");
			nameLbl.setFont(Style.BASE_FONT);
			nameLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(nameLbl);

			JTextField nameEdit = new JTextField(18);
			nameEdit.setFont(Style.BASE_FONT);
			nameEdit.setBackground(Style.TEA_GREEN);
			nameEdit.setForeground(Style.BALTIC_BLUE);
			nameEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			nameEdit.setText(editedMedia.getName());
			editWindow.add(nameEdit);

			JLabel epCountLbl = new JLabel("Ep Count: ");
			epCountLbl.setFont(Style.BASE_FONT);
			epCountLbl.setForeground(PageColor);
			editWindow.add(epCountLbl);

			JSpinner epCountEdit = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
			JSpinner.DefaultEditor epCountEditor = (JSpinner.DefaultEditor) epCountEdit.getEditor();
			JTextField epCountEditTextfield = epCountEditor.getTextField();
			epCountEditTextfield.setBackground(Style.TEA_GREEN);
			epCountEditTextfield.setForeground(Style.BALTIC_BLUE);
			epCountEditTextfield.setBorder(BorderFactory.createEmptyBorder());
			epCountEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			epCountEdit.setFont(Style.BASE_FONT);
			epCountEdit.setValue(editedMedia.getEpisodeCount());
			editWindow.add(epCountEdit);

			JLabel posterPLbl = new JLabel("Poster Path: ");
			posterPLbl.setFont(Style.BASE_FONT);
      posterPLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(posterPLbl);

			JTextField posterPEdit = new JTextField(18);
			posterPEdit.setFont(Style.BASE_FONT);
      posterPEdit.setBackground(Style.TEA_GREEN);
			posterPEdit.setForeground(Style.BALTIC_BLUE);
			posterPEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			posterPEdit.setText(editedMedia.getPosterPath());
			editWindow.add(posterPEdit);

			JLabel posterLLbl = new JLabel("Poster Link: ");
			posterLLbl.setFont(Style.BASE_FONT);
      posterLLbl.setForeground(Style.TEA_GREEN);
			editWindow.add(posterLLbl);

			JTextField posterLEdit = new JTextField(18);
			posterLEdit.setFont(Style.BASE_FONT);
			posterLEdit.setText(editedMedia.getPosterLink());
			posterLEdit.setBackground(Style.TEA_GREEN);
			posterLEdit.setForeground(Style.BALTIC_BLUE);
			posterLEdit.setBorder(BorderFactory.createLineBorder(Style.BORDER_COLOR, 2));
			editWindow.add(posterLEdit);

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
				editedMedia.setType(typeEdit.getSelectedIndex() + 1);
				editedMedia.setName(nameEdit.getText());
				editedMedia.setEpisodeCount((Integer) epCountEdit.getValue());
				editedMedia.setPosterPath(posterPEdit.getText());
				editedMedia.setPosterLink(posterLEdit.getText());

				ui.editMedia(editedMedia);

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
		delBtn = new JButton("Delete Media");
		delBtn.setFont(Style.BASE_FONT);
		delBtn.setBackground(Style.LIGHT_GREEN);
		delBtn.setForeground(Style.BALTIC_BLUE); 
		ui.addButtonImg(delBtn, new ImageIcon("assets/UI/binicon.png"), 20, 30, 30);

		delBtn.addActionListener(e -> {
			int selectedRow = userTable.getSelectedRow();
			if (selectedRow != -1) {
				Media deleteMedia = MediaTable[selectedRow];

				int result = JOptionPane.showConfirmDialog(this,
						"Are you sure you want to delete %s?".formatted(deleteMedia.getName()), "Delete User",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					ui.deleteMedia(deleteMedia);
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

	@Override
	public void loadData() {
		tableModel.setRowCount(0);
		MediaTable = ui.pullMedia();
		for (Media m : MediaTable) {
			// { "Id", "Type", "Name", "Ep. Count", "PosterPath", "PosterLink" };
			Object[] data = new Object[] { m.getId(), ui.getMeidaTypeFromInt(m.getType()), m.getName(),
					m.getEpisodeCount(),
					m.getPosterPath(),
					m.getPosterLink() };
			tableModel.addRow(data);
		}
	}
}
