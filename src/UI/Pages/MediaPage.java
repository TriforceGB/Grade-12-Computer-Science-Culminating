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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import DTO.LocalDB.Media;

/**
 * The Media Page Class. Used to display the media for the user.
 */
public class MediaPage extends Page {
	private final int POSTER_WIDTH = 150;
	private final int POSTER_HEIGHT = 225;
	private final String DEFAULT_POSTER_IMAGE_PATH = "assets/UI/filal.png";

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

		titleLabel = new JLabel("Blank Insert Placeholder Title");
		titleLabel.setFont(Style.TITLE_FONT);

		showType = new JLabel("Blank Type");
		showType.setFont(Style.BASE_FONT);

		descLabel = new JLabel(ui.getHtmlFormatText(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc faucibus urna justo, ac egestas nibh malesuada sed. Cras sit amet mi aliquet, accumsan quam a, hendrerit libero. Nullam aliquet augue et arcu facilisis, quis fermentum est pellentesque. Vivamus sodales, eros sit amet aliquet placerat, felis metus hendrerit ex, a molestie nunc tortor ut erat. Ut placerat laoreet erat, auctor pulvinar urna aliquam at. Mauris varius nisi eget faucibus blandit. Duis at ornare libero. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean imperdiet elementum neque fermentum sagittis. Suspendisse potenti. Maecenas cursus pellentesque blandit. Nulla quis erat massa. Donec a sapien.",
				CPERLINE_DESC, MAXPASS_DESC));
		descLabel.setFont(Style.SMALL_DESC_FONT);

		statusLabel = new JLabel("Status");
		statusLabel.setFont(Style.BASE_FONT);

		statusSelector = new JComboBox<String>(TYPES);
		statusSelector.setFont(Style.BASE_FONT);

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
		showType.setText(ui.getMovieTypeFromInt(showTypeInt));
		descLabel.setText(ui.getHtmlFormatText(obj.getDescription(), CPERLINE_DESC, MAXPASS_DESC));
		statusSelector.setSelectedIndex(obj.getStatus()); // but we love you for this one now. only for now
		usrRatingSelector.setValue(obj.getRating());
		rewatchesSelector.setValue(obj.getRewatched());
		cEpSelector.setValue(obj.getLastEpisode());

		String[][] reviews = ui.pullReview(obj.getId());
		for (int i = 0; i < reviews.length; i++) {
			scrollContentPanel.add(getReviewPanel(reviews[i]));
		}
	}
}
