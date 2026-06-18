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
	private JScrollPane titleScrollPane;
	private JLabel titleText;
	private JLabel showType;
	private JScrollPane descScrollPane;
	private JLabel descText;

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

	private final int CPERLINE_DESC = 40;
	private final int MAXPASS_DESC = 5;
	private final int CPERLINE_TITLE = 20;
	private final int MAXPASS_TITLE = 3;
	private final int CPERLINE_REVIEW_COMMENT = 80;
	private final int MAXPASS_REVIEW_COMMENT = 5;

	private String panelToSendBackTo;

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
		gbc = new GridBagConstraints();
		eastSidePanel = new JPanel(new BorderLayout());
		southEastSidePanel = new JPanel(new GridBagLayout());
	}

	void createEastDisplayComponents() {
		poster = new JLabel();
		poster.setPreferredSize(new Dimension(POSTER_WIDTH, POSTER_HEIGHT));
		poster.setIcon(ui.resizeImg(new ImageIcon(DEFAULT_POSTER_IMAGE_PATH), POSTER_WIDTH, POSTER_HEIGHT));

		startDateLabel = new JLabel("Start Date: ");
		startDateLabel.setFont(Style.BASE_FONT);

		finishDateLabel = new JLabel("End Date: ");
		finishDateLabel.setFont(Style.BASE_FONT);

		startDateField = new JTextField(12);
		startDateField.setText("YYYY-MM-DD");
		startDateField.setFont(Style.BASE_FONT);
		startDateField.setEditable(false);
		finishDateField = new JTextField(12);
		finishDateField.setText("YYYY-MM-DD");
		finishDateField.setFont(Style.BASE_FONT);
		finishDateField.setEditable(false);
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
		gbc = new GridBagConstraints(); // refresh components

		titleText = new JLabel("Place holder title");
		titleText.setFont(Style.TITLE_FONT);

		titleScrollPane = new JScrollPane(titleText);
		titleScrollPane.setBorder(BorderFactory.createEmptyBorder());
		titleScrollPane.setPreferredSize(new Dimension(500, 200));

		showType = new JLabel("Blank Type");
		showType.setFont(Style.BASE_FONT);

		descText = new JLabel("Place Holder desc");
		descText.setFont(Style.SMALL_DESC_FONT);

		descScrollPane = new JScrollPane(descText);
		descScrollPane.setBorder(BorderFactory.createEmptyBorder());
		descScrollPane.setPreferredSize(new Dimension(700, 400));

		statusLabel = new JLabel("Status");
		statusLabel.setFont(Style.BASE_FONT);
		statusLabel.setPreferredSize(new Dimension(250, 30));

		statusSelector = new JComboBox<String>(TYPES);
		statusSelector.setFont(Style.BASE_FONT);
		statusSelector.setPreferredSize(new Dimension());

		// When you Change the Status, Update UI
		statusSelector.addActionListener(e -> {
			int newStatus = statusSelector.getSelectedIndex();
			editStatus(newStatus, media);
		});

		usrRatingLabel = new JLabel("Your Rating: ");
		usrRatingLabel.setFont(Style.BASE_FONT);

		usrRatingSelector = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		usrRatingSelector.setFont(Style.BASE_FONT);

		rewatchLabel = new JLabel("Rewatch: ");
		rewatchLabel.setFont(Style.BASE_FONT);

		rewatchesSelector = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		rewatchesSelector.setFont(Style.BASE_FONT);

		cEpLabel = new JLabel("Current Episode: ");
		cEpLabel.setFont(Style.BASE_FONT);

		cEpSpinnerModel = new SpinnerNumberModel(0, 0, 0, 0);
		cEpSelector = new JSpinner(cEpSpinnerModel);
		cEpSelector.setFont(Style.BASE_FONT);
	}

	void formatMainInfDisplayComponents() {
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.insets = new Insets(20, 0, 0, 0);
		infEastSidePanel.add(titleScrollPane, gbc);

		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.insets = new Insets(20, 20, 0, 0);
		infEastSidePanel.add(showType, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 190, 80, 0);
		infEastSidePanel.add(descScrollPane, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(statusLabel, gbc);

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
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
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(cEpLabel, gbc);

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(cEpSelector, gbc);
	}

	void createFormatUsrReviewsSidePanel() {
		usrReviewsSidePanel = new JPanel(new BorderLayout());

		usrReviewsTitleLabel = new JLabel("User Reviews: ");
		usrReviewsTitleLabel.setFont(Style.BASE_FONT);
		scrollContentPanel = new JPanel(new GridLayout(0, 1, 0, 10));
		usrReviewsScrollPane = new JScrollPane(scrollContentPanel);
		usrReviewsScrollPane.setPreferredSize(new Dimension(250, 0));
		usrReviewsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	}

	void addSidePanelsToMain() {
		usrReviewsSidePanel.add(usrReviewsTitleLabel, BorderLayout.NORTH);
		usrReviewsSidePanel.add(usrReviewsScrollPane, BorderLayout.CENTER);

		eastSidePanel.add(infEastSidePanel, BorderLayout.WEST);
		eastSidePanel.add(usrReviewsSidePanel, BorderLayout.CENTER);
	}

	void addButtonsToSouth() {
		gbc = new GridBagConstraints();

		backButton = new JButton("Back");
		backButton.setFont(Style.BASE_FONT);
		backButton.setPreferredSize(new Dimension(300, 50));
		backButton.addActionListener(e -> {
			ui.switchPanel(panelToSendBackTo);
		});

		gbc.gridy = 0; // constant
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 870, 0, 20);

		southEastSidePanel.add(backButton, gbc);

		saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(300, 50));
		saveButton.setFont(Style.BASE_FONT);

		saveButton.addActionListener(e -> {
			if (updateUserData()) {
				JOptionPane.showMessageDialog(this, "Successfully Saved!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				media = ui.locateMedia(media);
			} else {
				JOptionPane.showMessageDialog(this, "Failed to Save!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 0, 20);

		southEastSidePanel.add(saveButton, gbc);

		addEditReviewButton = new JButton("Add/Edit Review");
		addEditReviewButton.setPreferredSize(new Dimension(300, 50));
		addEditReviewButton.setFont(Style.BASE_FONT);

		gbc.gridx = 2;
		gbc.insets = new Insets(0, 0, 0, 0);

		southEastSidePanel.add(addEditReviewButton, gbc);
	}

	JPanel getReviewPanel(String[] review) {
		JPanel result = new JPanel(new GridBagLayout());
		result.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
		GridBagConstraints gbc2 = new GridBagConstraints();

		String name = review[0];
		JLabel usrName = new JLabel(name);

		String comment = review[1];
		JLabel usrComment = new JLabel(ui.getHtmlFormatText(comment, CPERLINE_REVIEW_COMMENT, MAXPASS_REVIEW_COMMENT));
		usrComment.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

		JLabel usrRating = new JLabel(review[2] + "/10");

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
		titleText.setText(ui.getHtmlFormatText(obj.getName(), CPERLINE_TITLE, MAXPASS_TITLE));
		int showTypeInt = obj.getType();
		showType.setText(ui.getMovieTypeFromInt(showTypeInt));
		descText.setText(ui.getHtmlFormatText(obj.getDescription(), CPERLINE_DESC, MAXPASS_DESC));
		statusSelector.setSelectedIndex(obj.getStatus()); // but we love you for this one now. only for now
		usrRatingSelector.setValue(obj.getRating());
		rewatchesSelector.setValue(obj.getRewatched());
		cEpSelector.setModel(new SpinnerNumberModel(obj.getLastEpisode(), 0, obj.getEpisodeCount(), 1));

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
		if (media.getStatus() == 0) { // Create the User Data
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
