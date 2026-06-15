package UI.Pages;

import UI.Style;
import UI.UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 * The Media Page Class. Used to display the media for the user.
 */
public class MediaPage extends Page {
	/**
	 * Create the Media Page
	 *
	 * @param ui The UI object that this page belongs to
	 * 
	 *
	 */

	private final int POSTER_WIDTH = 150;
	private final int POSTER_HEIGHT = 225;
	private final String POSTER_IMAGE_PATH = "assets/UI/filal.png";

	// TODO refactor to functions and have public function to modify vals

	public MediaPage(UI ui) {
		super(ui); // Uses the basic page layout and background color
		JPanel westSidePanel = new JPanel(new GridBagLayout()); 
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel eastSidePanel = new JPanel(new BorderLayout());
		JPanel southEastSidePanel = new JPanel(new GridBagLayout());

		JLabel poster = new JLabel();
		poster.setPreferredSize(new Dimension(POSTER_WIDTH, POSTER_HEIGHT));
		poster.setIcon(ui.resizeImg(new ImageIcon(POSTER_IMAGE_PATH), POSTER_WIDTH, POSTER_HEIGHT));

		JLabel startDateLabel = new JLabel("Start Date: ");
		startDateLabel.setFont(Style.BASE_FONT);

		JLabel endDateLabel = new JLabel("End Date: ");
		endDateLabel.setFont(Style.BASE_FONT);


		JTextField startDateField = new JTextField(12);
		startDateField.setText("YYYY-MM-DD");
		startDateField.setFont(Style.BASE_FONT);
		startDateField.setEditable(false);
		JTextField endDateField = new JTextField(12);
		endDateField.setText("YYYY-MM-DD");
		endDateField.setFont(Style.BASE_FONT);
		endDateField.setEditable(false);

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
		westSidePanel.add(endDateLabel, gbc);

		gbc.gridy = 4;
		gbc.insets = new Insets(0, 50, 0, 50);
		westSidePanel.add(endDateField, gbc);
		

		JPanel infEastSidePanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints(); // refresh components


		JLabel titleLabel = new JLabel("Testing Title");
		titleLabel.setFont(Style.TITLE_FONT);

		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.insets = new Insets(20, 40, 0, 0);
		infEastSidePanel.add(titleLabel,gbc);

		JLabel showType = new JLabel("Some type");
		showType.setFont(Style.BASE_FONT);

		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.insets = new Insets(20, 20, 0, 0);
		infEastSidePanel.add(showType,gbc);

		JLabel descLabel = new JLabel("This is a long description of events....");
		descLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 40, 80, 0);
		infEastSidePanel.add(descLabel,gbc);

		JLabel statusLabel = new JLabel("Status");
		statusLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(statusLabel,gbc);

		JTextField statusField = new JTextField(10);
		statusField.setFont(Style.BASE_FONT);
		statusField.setEditable(false);

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(statusField,gbc);

		JLabel usrRatingLabel = new JLabel("Your Rating: ");
		usrRatingLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(usrRatingLabel,gbc);

		JTextField usrRatingField = new JTextField(10);
		usrRatingField.setFont(Style.BASE_FONT);
		usrRatingField.setEditable(false);

		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 20, 10, 0);
		infEastSidePanel.add(usrRatingField,gbc);

		JLabel rewatchLabel = new JLabel("Rewatch: ");
		rewatchLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 2;
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 20, 10, 0);
		infEastSidePanel.add(rewatchLabel,gbc);

		JTextField rewatchField = new JTextField(10);
		rewatchField.setFont(Style.BASE_FONT);
		rewatchField.setEditable(false);

		gbc.gridy = 3;
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 0, 10, 0);
		infEastSidePanel.add(rewatchField,gbc);

		JLabel cEpLabel = new JLabel("Current Episode: ");
		cEpLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(cEpLabel,gbc);

		JTextField cEpField = new JTextField(10);
		cEpField.setFont(Style.BASE_FONT);
		cEpField.setEditable(false);

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 40, 10, 0);
		infEastSidePanel.add(cEpField,gbc);

		JPanel usrReviewsSidePanel = new JPanel(new BorderLayout());

		JLabel usrReviewsTitleLabel = new JLabel("User Reviews: ");
		usrReviewsTitleLabel.setFont(Style.BASE_FONT);
		JPanel scrollContentPanel = new JPanel(new GridLayout(0, 1, 0, 10));
		JScrollPane usrReviewsScrollPane = new JScrollPane(scrollContentPanel);
		usrReviewsScrollPane.setPreferredSize(new Dimension(250, 0));
		usrReviewsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		// TODO load user reviews
		int test = 100;
		for (int i = 0; i < test; i++) {
			scrollContentPanel.add(getReviewPanel());
		}

		usrReviewsSidePanel.add(usrReviewsTitleLabel, BorderLayout.NORTH);
		usrReviewsSidePanel.add(usrReviewsScrollPane, BorderLayout.CENTER);

		eastSidePanel.add(infEastSidePanel, BorderLayout.WEST);
		eastSidePanel.add(usrReviewsSidePanel, BorderLayout.CENTER);

		gbc = new GridBagConstraints();

		JButton backButton = new JButton("Back");
		backButton.setFont(Style.BASE_FONT);
		backButton.setPreferredSize(new Dimension(300, 50));
		backButton.addActionListener(e -> {
			// TODO verify what panel was last
			ui.switchPanel("home");
		});

		gbc.gridy = 0; // constant
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 870, 0, 20);

		southEastSidePanel.add(backButton, gbc);

		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(300, 50));
		saveButton.setFont(Style.BASE_FONT);

		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 0, 20);

		southEastSidePanel.add(saveButton, gbc);

		JButton addEditReviewButton = new JButton("Add/Edit Review");
		addEditReviewButton.setPreferredSize(new Dimension(300, 50));
		addEditReviewButton.setFont(Style.BASE_FONT);

		gbc.gridx = 2;
		gbc.insets = new Insets(0, 0, 0, 0);

		southEastSidePanel.add(addEditReviewButton, gbc);

		this.add(westSidePanel, BorderLayout.WEST);
		this.add(eastSidePanel,BorderLayout.CENTER);
		this.add(southEastSidePanel, BorderLayout.SOUTH);
	}

	JPanel getReviewPanel() {
		JPanel result = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();

		String name = "Bilal Faruqi";
		JLabel usrName = new JLabel(name);

		int rating = 8;
		JLabel usrRating = new JLabel(String.valueOf(rating));

		String comment = "This show has too many bad boys to be a good show...";
		JLabel usrComment = new JLabel(comment);

		gbc2.gridy = 0;
		gbc2.gridx = 0;
		result.add(usrName, gbc2);

		gbc2.gridy = 1;
		gbc2.gridx = 0;
		result.add(usrComment, gbc2);

		gbc2.gridy = 1;
		gbc2.gridx = 1;
		result.add(usrRating, gbc2);

		return result;
	}
}
