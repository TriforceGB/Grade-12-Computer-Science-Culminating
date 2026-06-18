package UI.Pages;

import UI.Style;
import UI.UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import DTO.LocalDB.Media;
import DTO.LocalDB.Media.UserData;

/**
 * The Media Page Class. Used to display the media for the user.
 */
public class MediaPage extends Page {
	private final int POSTER_WIDTH = 150;
	private final int POSTER_HEIGHT = 225;
	private final String DEFAULT_POSTER_IMAGE_PATH = "assets/UI/filal.png";

	// Variables
	Media media; // Media that is being displayed

	private JPanel westSidePanel;
	private GridBagConstraints gbc;
	private JPanel eastSidePanel;
	private JPanel southEastSidePanel;

	private JLabel poster;
	private JLabel startDateLabel;
	private JLabel finishDateLabel;
	private JTextField startDateField;
	private JTextField finishDateField;

	private JPanel infEastSidePanel;

	private JLabel titleLabel;
	private JLabel showType;
	private JLabel descLabel;
	private final int CPERLINE_DESC = 40;
	private final int MAXPASS_DESC = 5;
	private JLabel statusLabel;
	private final String[] TYPES = new String[] { "Undecided", "Dropped", "Backlog", "Watching", "Completed" };
	private JComboBox<String> statusSelector;
	private JLabel usrRatingLabel;
	private JSpinner usrRatingSelector;
	private JLabel rewatchLabel;
	private JSpinner rewatchesSelector;
	private JLabel cEpLabel;
	private JSpinner cEpSelector;
	private SpinnerNumberModel cEpSpinnerModel;

	private JPanel usrReviewsSidePanel;
	private JLabel usrReviewsTitleLabel;
	private JPanel scrollContentPanel;
	private JScrollPane usrReviewsScrollPane;

	private JButton backButton;
	private JButton saveButton;
	private JButton addEditReviewButton;

	private final int CPERLINE_REVIEW_COMMENT = 80;
	private final int MAXPASS_REVIEW_COMMENT = 5;

	private String panelToSendBackTo;

	private int gap = 20;
	private int imagedimensions = 30;

	/**
	 * Create the Media Page
	 *
	 * @param ui The UI object that this page belongs to
	 *
	 *
	 */

	public MediaPage(UI ui) {
		super(ui); // Uses the basic page layout and background color

		createPagePanels();

		createEastDisplayComponents();
		formatEastSideDisplayComponents();

		createMainInfDisplayComponents();
		formatMainInfDisplayComponents();

		createFormatUsrReviewsSidePanel();

		addSidePanelsToMain();

		addButtonsToSouth();

		this.add(westSidePanel, BorderLayout.WEST);
		this.add(eastSidePanel, BorderLayout.CENTER);
		this.add(southEastSidePanel, BorderLayout.SOUTH);
	}

	void createPagePanels() {
		westSidePanel = new JPanel(new GridBagLayout());
		westSidePanel.setBackground(Style.BORDER_COLOR);
		gbc = new GridBagConstraints();
		eastSidePanel = new JPanel(new BorderLayout());
		eastSidePanel.setBackground(Style.BORDER_COLOR);
		southEastSidePanel = new JPanel(new GridLayout(1, 3, 20, 0));
		southEastSidePanel.setBackground(Style.BALTIC_BLUE);
	}

	void createEastDisplayComponents() {
		poster = new JLabel();
		poster.setPreferredSize(new Dimension(POSTER_WIDTH, POSTER_HEIGHT));
		poster.setIcon(ui.resizeImg(new ImageIcon(DEFAULT_POSTER_IMAGE_PATH), POSTER_WIDTH, POSTER_HEIGHT));

		startDateLabel = new JLabel("Start Date: ");
		startDateLabel.setFont(Style.BASE_FONT);
		startDateLabel.setForeground(Style.TEA_GREEN);

		finishDateLabel = new JLabel("End Date: ");
		finishDateLabel.setFont(Style.BASE_FONT);
		finishDateLabel.setForeground(Style.TEA_GREEN);

		startDateField = new JTextField(12);
		startDateField.setText("YYYY-MM-DD");
		startDateField.setFont(Style.BASE_FONT);
		startDateField.setEditable(false);
		startDateField.setFocusable(false);
		startDateField.setBackground(Style.TEA_GREEN);
		startDateField.setForeground(Style.BALTIC_BLUE);

		finishDateField = new JTextField(12);
		finishDateField.setText("YYYY-MM-DD");
		finishDateField.setFont(Style.BASE_FONT);
		finishDateField.setEditable(false);
		finishDateField.setFocusable(false);
		finishDateField.setBackground(Style.TEA_GREEN);
		finishDateField.setForeground(Style.BALTIC_BLUE);
	}

	void formatEastSideDisplayComponents() {
		gbc.gridy = 0;
		gbc.gridx = 0; // constant
		gbc.insets = new Insets(50, 50, 30, 50);
		westSidePanel.add(poster, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(0, 50, 0, 50);
		westSidePanel.add(startDateLabel, gbc);

		gbc.gridy = 2;
		gbc.insets = new Insets(0, 50, 0, 50);
		westSidePanel.add(startDateField, gbc);

		gbc.gridy = 3;
		gbc.insets = new Insets(0, 50, 0, 50);
		westSidePanel.add(finishDateLabel, gbc);

		gbc.gridy = 4;
		gbc.insets = new Insets(0, 50, 0, 50);
		westSidePanel.add(finishDateField, gbc);
	}

	void createMainInfDisplayComponents() {
		infEastSidePanel = new JPanel(new GridBagLayout());
		infEastSidePanel.setBackground(Style.BORDER_COLOR);
		gbc = new GridBagConstraints(); // refresh components

		titleLabel = new JLabel("Blank Insert Placeholder Title");
		titleLabel.setForeground(Style.TEA_GREEN);
		titleLabel.setFont(Style.TITLE_FONT);

		showType = new JLabel("Blank Type");
		showType.setForeground(Style.TEA_GREEN);
		showType.setFont(Style.BASE_FONT);

		descLabel = new JLabel(ui.getHtmlFormatText(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc faucibus urna justo, ac egestas nibh malesuada sed. Cras sit amet mi aliquet, accumsan quam a, hendrerit libero. Nullam aliquet augue et arcu facilisis, quis fermentum est pellentesque. Vivamus sodales, eros sit amet aliquet placerat, felis metus hendrerit ex, a molestie nunc tortor ut erat. Ut placerat laoreet erat, auctor pulvinar urna aliquam at. Mauris varius nisi eget faucibus blandit. Duis at ornare libero. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean imperdiet elementum neque fermentum sagittis. Suspendisse potenti. Maecenas cursus pellentesque blandit. Nulla quis erat massa. Donec a sapien.",
				CPERLINE_DESC, MAXPASS_DESC));
		descLabel.setFont(Style.SMALL_DESC_FONT);
		descLabel.setForeground(Style.TEA_GREEN);

		statusLabel = new JLabel("Status");
		statusLabel.setFont(Style.BASE_FONT);
		statusLabel.setForeground(Style.TEA_GREEN);

		statusSelector = new JComboBox<String>(TYPES);
		statusSelector.setFont(Style.BASE_FONT);
		statusSelector.setFocusable(false);
		statusSelector.setBackground(Style.TEA_GREEN);
		statusSelector.setForeground(Style.BALTIC_BLUE);

		// When you Change the Status, Update UI
		statusSelector.addActionListener(e -> {
			int newStatus = statusSelector.getSelectedIndex();
			editStatus(newStatus, media);
		});

		usrRatingLabel = new JLabel("Your Rating: ");
		usrRatingLabel.setFont(Style.BASE_FONT);
		usrRatingLabel.setForeground(Style.TEA_GREEN);

		usrRatingSelector = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		usrRatingSelector.setFont(Style.BASE_FONT);
		usrRatingSelector.setBackground(Style.TEA_GREEN);
		usrRatingSelector.setForeground(Style.BALTIC_BLUE);
		usrRatingSelector.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JSpinner.DefaultEditor userRatingEditor = (JSpinner.DefaultEditor) usrRatingSelector.getEditor();
		JTextField usrRatingTextfield = userRatingEditor.getTextField();
		usrRatingTextfield.setBackground(Style.TEA_GREEN);
		usrRatingTextfield.setForeground(Style.BALTIC_BLUE);
		usrRatingTextfield.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		rewatchLabel = new JLabel("Rewatch: ");
		rewatchLabel.setFont(Style.BASE_FONT);
		rewatchLabel.setForeground(Style.TEA_GREEN);

		rewatchesSelector = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		rewatchesSelector.setFont(Style.BASE_FONT);
		rewatchesSelector.setBackground(Style.TEA_GREEN);
		rewatchesSelector.setForeground(Style.BALTIC_BLUE);
		rewatchesSelector.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JSpinner.DefaultEditor rewatchesEditor = (JSpinner.DefaultEditor) rewatchesSelector.getEditor();
		JTextField rewatchesTextfield = rewatchesEditor.getTextField();
		rewatchesTextfield.setBackground(Style.TEA_GREEN);
		rewatchesTextfield.setForeground(Style.BALTIC_BLUE);
		rewatchesTextfield.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		cEpLabel = new JLabel("Current Episode: ");
		cEpLabel.setFont(Style.BASE_FONT);
		cEpLabel.setForeground(Style.TEA_GREEN);

		cEpSpinnerModel = new SpinnerNumberModel(0, 0, 0, 0);
		cEpSelector = new JSpinner(cEpSpinnerModel);
		cEpSelector.setFont(Style.BASE_FONT);
		cEpSelector.setBackground(Style.TEA_GREEN);
		cEpSelector.setForeground(Style.BALTIC_BLUE);
		cEpSelector.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// had to put the textfield color setter where the model is set
	}

	void formatMainInfDisplayComponents() {
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.insets = new Insets(20, 40, 0, 0);
		infEastSidePanel.add(titleLabel, gbc);

		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.insets = new Insets(20, 20, 0, 0);
		infEastSidePanel.add(showType, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 40, 80, 0);
		infEastSidePanel.add(descLabel, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(statusLabel, gbc);

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(statusSelector, gbc);

		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(usrRatingLabel, gbc);

		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 20, 10, 0);
		infEastSidePanel.add(usrRatingSelector, gbc);

		gbc.gridy = 2;
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 20, 10, 0);
		infEastSidePanel.add(rewatchLabel, gbc);

		gbc.gridy = 3;
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(rewatchesSelector, gbc);

		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(cEpLabel, gbc);

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(cEpSelector, gbc);
	}

	void createFormatUsrReviewsSidePanel() {
		usrReviewsSidePanel = new JPanel(new BorderLayout());
		usrReviewsSidePanel.setBackground(Style.BORDER_COLOR);
		usrReviewsTitleLabel = new JLabel("User Reviews");
		usrReviewsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usrReviewsTitleLabel.setFont(Style.BASE_FONT);
		usrReviewsTitleLabel.setForeground(Style.TEA_GREEN);
		scrollContentPanel = new JPanel(new GridLayout(0, 1, 0, 10));
		scrollContentPanel.setBackground(Style.BALTIC_BLUE);
		usrReviewsScrollPane = new JScrollPane(scrollContentPanel);
		usrReviewsScrollPane.setPreferredSize(new Dimension(250, 0));
		usrReviewsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	}

	private void addSidePanelsToMain() {
		usrReviewsSidePanel.add(usrReviewsTitleLabel, BorderLayout.NORTH);
		usrReviewsSidePanel.add(usrReviewsScrollPane, BorderLayout.CENTER);

		eastSidePanel.add(infEastSidePanel, BorderLayout.WEST);
		eastSidePanel.add(usrReviewsSidePanel, BorderLayout.CENTER);
	}

	void addButtonsToSouth() {

		backButton = new JButton("Back");
		backButton.setFont(Style.BASE_FONT);
		backButton.setBackground(Style.LIGHT_GREEN);
		backButton.setForeground(Style.BALTIC_BLUE);
		backButton.setPreferredSize(new Dimension(600, 50));
		backButton.addActionListener(e -> {
			ui.switchPanel(panelToSendBackTo);
		});
		ui.addButtonImg(backButton, new ImageIcon("assets/UI/backicon.png"), gap, imagedimensions, imagedimensions);
		southEastSidePanel.add(backButton);

		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(600, 50));
		saveButton.setFont(Style.BASE_FONT);
		saveButton.setBackground(Style.LIGHT_GREEN);
		saveButton.setForeground(Style.BALTIC_BLUE);

		saveButton.addActionListener(e -> {
			if (updateUserData()) {
				JOptionPane.showMessageDialog(this, "Successfully Saved!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				media = ui.locateMedia(media);
			} else {
				JOptionPane.showMessageDialog(this, "Failed to Save!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ui.callreset();
		});
		ui.addButtonImg(saveButton, new ImageIcon("assets/UI/saveicon.png"), gap, imagedimensions, imagedimensions);

		southEastSidePanel.add(saveButton);

		addEditReviewButton = new JButton("Add/Edit Review");
		addEditReviewButton.setPreferredSize(new Dimension(600, 50));
		addEditReviewButton.setFont(Style.BASE_FONT);
		addEditReviewButton.setBackground(Style.LIGHT_GREEN);
		addEditReviewButton.setForeground(Style.BALTIC_BLUE);

		ui.addButtonImg(addEditReviewButton, new ImageIcon("assets/UI/reviewicon.png"), gap, imagedimensions,
				imagedimensions);

		southEastSidePanel.add(addEditReviewButton);
	}

	JPanel getReviewPanel(String[] review) {
		JPanel result = new JPanel(new GridBagLayout());
		result.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		result.setBackground(Style.BORDER_COLOR);

		GridBagConstraints gbc2 = new GridBagConstraints();

		String name = review[0];
		JLabel usrName = new JLabel(name);
		usrName.setFont(Style.BASE_FONT_BIG);
		usrName.setForeground(Style.TEA_GREEN);

		String comment = review[1];
		JLabel usrComment = new JLabel(ui.getHtmlFormatText(comment, CPERLINE_REVIEW_COMMENT, MAXPASS_REVIEW_COMMENT));
		usrComment.setForeground(Style.TEA_GREEN);
		usrComment.setFont(Style.BASE_FONT);

		JLabel usrRating = new JLabel(review[2] + "/10");
		usrRating.setForeground(Style.TEA_GREEN);
		usrRating.setFont(Style.BASE_FONT);

		gbc2.gridy = 0;
		gbc2.gridx = 0;
		result.add(usrName, gbc2);

		gbc2.gridy = 1;
		gbc2.gridx = 0;
		gbc2.insets = new Insets(5, 0, 0, 20);
		result.add(usrComment, gbc2);

		gbc2.gridy = 1;
		gbc2.gridx = 1;
		gbc2.insets = new Insets(5, 0, 0, 0);
		result.add(usrRating, gbc2);

		return result;
	}

	public void setupMediaPanel(Media obj, String panelName) {
		media = obj; // Stores it for the Rest of the UI to Use
		panelToSendBackTo = panelName;

		// then load data
		File posterFile = new File(obj.getPosterPath());
		if (posterFile.exists())
			poster.setIcon(ui.resizeImg(new ImageIcon(obj.getPosterPath()), POSTER_WIDTH, POSTER_HEIGHT));
		else
			poster.setIcon(ui.resizeImg(new ImageIcon(DEFAULT_POSTER_IMAGE_PATH), POSTER_WIDTH, POSTER_HEIGHT));
		startDateField.setText(obj.getStartDate());
		finishDateField.setText(obj.getFinishDate());
		titleLabel.setText(obj.getName());
		int showTypeInt = obj.getType();
		showType.setText(ui.getMeidaTypeFromInt(showTypeInt));
		descLabel.setText(ui.getHtmlFormatText(obj.getDescription(), CPERLINE_DESC, MAXPASS_DESC));
		statusSelector.setSelectedIndex(obj.getStatus()); // but we love you for this one now. only for now
		usrRatingSelector.setValue(obj.getRating());
		rewatchesSelector.setValue(obj.getRewatched());
		cEpSelector.setModel(new SpinnerNumberModel(obj.getLastEpisode(), 0, obj.getEpisodeCount(), 1));
		JSpinner.DefaultEditor cEpEditor = (JSpinner.DefaultEditor) cEpSelector.getEditor();
		JTextField cEpTextField = cEpEditor.getTextField();
		cEpTextField.setBackground(Style.TEA_GREEN);
		cEpTextField.setForeground(Style.BALTIC_BLUE);
		cEpTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		scrollContentPanel.removeAll();
		String[][] reviews = ui.pullReview(obj.getId());
		for (int i = 0; i < reviews.length; i++) {
			if (reviews[i][1] != null) {
				scrollContentPanel.add(getReviewPanel(reviews[i]));
			}
		}
	}

	/**
	 * Handle the Logic for if to Edit or Create or Remove Status
	 *
	 * @param newStatus The new status to set
	 * @param refMedia  The Media to edit
	 * @return If the Status was Edited
	 */
	private void editStatus(int newStatus, Media refMedia) {
		if (newStatus == 3) { // Watching == Set Start Date
			startDateField.setText(LocalDate.now().toString());
		} else if (newStatus == 4) { // Finished == Set Finish Date
			finishDateField.setText(LocalDate.now().toString());
			cEpSelector.setValue(refMedia.getEpisodeCount());
		}
	}

	/**
	 * Runs when the save button is pressed. Give all the Values the User has
	 * created
	 *
	 * @return If adding it worked
	 */
	private boolean updateUserData() {
		UserData newUserData = new UserData(statusSelector.getSelectedIndex(), startDateField.getText(),
				finishDateField.getText(), (Integer) usrRatingSelector.getValue(), (Integer) cEpSelector.getValue(),
				null,
				(Integer) rewatchesSelector.getValue());
		if (newUserData.getStatus() == 0 && media.getStatus() == 0) {
			JOptionPane.showMessageDialog(this,
					"Please Set Status to Non Undecided before Saving", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (media.getStatus() == 0) { // Create the User Data
			return ui.createUserData(media.getId(), newUserData);
		} else if (newUserData.getStatus() == 0) {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to set as Undecided? this will Remove all Info you have entered (Start Date, Rating, Review etc)");
			if (confirm == JOptionPane.YES_OPTION) {
				return ui.deleteUserData(media.getId());
			} else {
				return false;
			}
		} else {
			return ui.editUserData(media.getId(), newUserData);
		}
	}
}
