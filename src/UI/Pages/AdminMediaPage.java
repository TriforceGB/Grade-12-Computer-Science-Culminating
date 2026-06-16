package UI.Pages;

import javax.swing.JLabel;

import UI.UI;

/**
 * Admin page base for Admin Settings.
 * Other pages in the admin settings will use this as a base.
 */
public class AdminMediaPage extends AdminUserPage {
	public AdminMediaPage(UI ui) {
		super(ui);

		this.add(new JLabel("Media page"));
	}
}
