package UI.Pages;

import UI.Style;
import UI.UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

	public MediaPage(UI ui) {
		super(ui); // Uses the basic page layout and background color
		JPanel Westsidepanel = new JPanel(new GridLayout(5,1)); 
		JPanel Eastsidepanel = new JPanel(new BorderLayout());
		JPanel SouthEastsidepanel = new JPanel(new GridLayout(1,3, 10, 0));


		JLabel poster = new JLabel();
		poster.setPreferredSize(new Dimension(POSTER_WIDTH, POSTER_HEIGHT));
		poster.setIcon(ui.resizeImg(new ImageIcon(POSTER_IMAGE_PATH), POSTER_WIDTH, POSTER_HEIGHT));

		JLabel StartDateLabel = new JLabel("Start Date: ");
		StartDateLabel.setFont(Style.BASE_FONT);

		JLabel EndDateLabel = new JLabel("End Date: ");
		EndDateLabel.setFont(Style.BASE_FONT);


		JTextField StartDateField = new JTextField();
		StartDateField.setText("YYYY-MM-DD");
		StartDateField.setFont(Style.BASE_FONT);
		StartDateField.setEditable(false);
		JTextField EndDateField = new JTextField();
		EndDateField.setText("YYYY-MM-DD");
		EndDateField.setFont(Style.BASE_FONT);
		EndDateField.setEditable(false);



		Eastsidepanel.add(poster);
		Eastsidepanel.add(StartDateLabel);
		Eastsidepanel.add(StartDateField);
		Eastsidepanel.add(EndDateLabel);
		Eastsidepanel.add(EndDateField);
		



		JPanel InformationEastSidePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();








		JLabel TitleLabel = new JLabel();
		TitleLabel.setFont(Style.TITLE_FONT);

		gbc.gridy = 0;
		gbc.gridx = 0;
		InformationEastSidePanel.add(TitleLabel,gbc);

		JLabel ShowType = new JLabel();
		ShowType.setFont(Style.BASE_FONT);

		gbc.gridy = 0;
		gbc.gridx = 1;
		InformationEastSidePanel.add(ShowType,gbc);

		JLabel DescriptionLabel = new JLabel();
		DescriptionLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 1;
		gbc.gridx = 0;
		InformationEastSidePanel.add(DescriptionLabel,gbc);

		JLabel StatusLabel = new JLabel();
		StatusLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 2;
		gbc.gridx = 0;
		InformationEastSidePanel.add(StatusLabel,gbc);

		JTextField Status = new JTextField();
		Status.setFont(Style.BASE_FONT);
		Status.setEditable(false);



		gbc.gridy = 3;
		gbc.gridx = 0;
		InformationEastSidePanel.add(Status,gbc);

		JLabel UserRatingLabel = new JLabel("Your Rating: ");
		UserRatingLabel.setFont(Style.BASE_FONT);


		gbc.gridy = 2;
		gbc.gridx = 1;
		InformationEastSidePanel.add(UserRatingLabel,gbc);

		JTextField UserRating = new JTextField();
		UserRating.setFont(Style.BASE_FONT);
		UserRating.setEditable(false);

		gbc.gridy = 3;
		gbc.gridx = 1;
		InformationEastSidePanel.add(UserRating,gbc);

		JLabel RewatchLabel = new JLabel("Rewatch: ");
		RewatchLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 2;
		gbc.gridx = 2;
		InformationEastSidePanel.add(RewatchLabel,gbc);

		JTextField Rewatch = new JTextField();
		Rewatch.setFont(Style.BASE_FONT);
		Rewatch.setEditable(false);

		gbc.gridy = 3;
		gbc.gridx = 2;
		InformationEastSidePanel.add(Rewatch,gbc);

		JLabel CurrentEpisodeLabel = new JLabel("Current Episode: ");
		CurrentEpisodeLabel.setFont(Style.BASE_FONT);

		gbc.gridy = 4;
		gbc.gridx = 0;
		InformationEastSidePanel.add(CurrentEpisodeLabel,gbc);

		JTextField CurrentEpisode = new JTextField();
		CurrentEpisode.setFont(Style.BASE_FONT);
		CurrentEpisode.setEditable(false);

		gbc.gridy = 5;
		gbc.gridx = 0;
		InformationEastSidePanel.add(CurrentEpisode,gbc);



		JPanel userreviewsEastSidePanel = new JPanel(new BorderLayout());

		JLabel UserReviewsLabel = new JLabel("User Reviews: ");
		UserReviewsLabel.setFont(Style.BASE_FONT);
		JScrollPane UserReviewsScrollPane = new JScrollPane();

		userreviewsEastSidePanel.add(UserReviewsLabel,BorderLayout.NORTH);
		userreviewsEastSidePanel.add(UserReviewsScrollPane,BorderLayout.CENTER);





		JButton AddEditReviewButton = new JButton("Add/Edit Review");
		AddEditReviewButton.setFont(Style.BASE_FONT);
		SouthEastsidepanel.add(AddEditReviewButton);
		JButton BackButton = new JButton("Back");
		BackButton.setFont(Style.BASE_FONT);
		SouthEastsidepanel.add(BackButton);
		JButton SaveButton = new JButton("Save");
		SaveButton.setFont(Style.BASE_FONT);
		SouthEastsidepanel.add(SaveButton);











		this.add(Westsidepanel, BorderLayout.WEST);
		this.add(Eastsidepanel,BorderLayout.NORTH);
		this.add(SouthEastsidepanel, BorderLayout.SOUTH);




	}
}
