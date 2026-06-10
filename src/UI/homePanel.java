package UI;

import java.util.EventListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DB.DB;

import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * This is the Page that display after Login. It will show a summary of the
 * user's media library.
 */
public class homePanel extends JPanel implements EventListener {
	private UI ui; // Reference to the main UI
	private DB db; // Reference to the database

	/**
	 * Create the Home Panel
	 *
	 * @param ui The UI to associate with the panel
	 * @param db The database reference
	 */
	public homePanel(UI ui, DB db) {
		this.ui = ui;
		this.db = db;
		
		int hgap = 20;
		int cols = 3;
		this.setLayout(new GridLayout(1, 3, hgap, 0));
		

		JPanel backlogpanel = new JPanel();
		JPanel currentpanel = new JPanel();
		JPanel finishpanel = new JPanel();

		// BACKLOG PANEL
		backlogpanel.setLayout(new BorderLayout());
		JPanel blbuttonpanel = new JPanel();
		blbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));
		for (int i = 1; i <= 25; i++) {
			blbuttonpanel.add(new JButton(String.valueOf(i)));
		}

		JScrollPane blScrollPane = new JScrollPane(blbuttonpanel);
		blScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        blScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel bllabel = new JLabel("Backlog", SwingConstants.CENTER);
		bllabel.setFont(style.BASE_FONT);

		backlogpanel.add(bllabel, BorderLayout.PAGE_START);
		backlogpanel.add(blbuttonpanel, BorderLayout.CENTER);

		// CURRENTLY WATCHING PANEL
		currentpanel.setLayout(new BorderLayout());
		JPanel crbuttonpanel = new JPanel();
		crbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));
		for (int i = 1; i <= 25; i++) {
			crbuttonpanel.add(new JButton(String.valueOf(i)));
		}

		JScrollPane crScrollPane = new JScrollPane(crbuttonpanel);
		crScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        crScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel crlabel = new JLabel("Currently Watching", SwingConstants.CENTER);
		crlabel.setFont(style.BASE_FONT);

		currentpanel.add(crlabel, BorderLayout.PAGE_START);
		currentpanel.add(crbuttonpanel, BorderLayout.CENTER);


		// FINISH PANEL

		finishpanel.setLayout(new BorderLayout());
		JPanel finbuttonpanel = new JPanel();
		finbuttonpanel.setLayout(new GridLayout(0, cols, hgap, 20));
		for (int i = 1; i <= 25; i++) {
			finbuttonpanel.add(new JButton(String.valueOf(i)));
		}

		JScrollPane finScrollPane = new JScrollPane(finbuttonpanel);
		finScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        finScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel finlabel = new JLabel("Backlog", SwingConstants.CENTER);
		finlabel.setFont(style.BASE_FONT);

		finishpanel.add(finlabel, BorderLayout.PAGE_START);
		finishpanel.add(finbuttonpanel, BorderLayout.CENTER);
		this.add(backlogpanel);
		this.add(currentpanel);
		this.add(finishpanel);


		
	}
}
