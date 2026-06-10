package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 * Style constants for the application UI.
 */
public class Style {
	public static final String APP_TITLE = "Filal's Tracking Service";
	public static final String NEW_USER_TITLE = "Create New User";

	public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 32);
	public static final Font BASE_FONT = new Font("Arial", Font.PLAIN, 24);

	public static final Insets LABEL_PADS = new Insets(0, 50, 5, 15);
	public static final Insets FIELD_PADS = new Insets(0, 0, 5, 50);
	public static final Insets BTN_PADS = new Insets(15, 0, 0, 0);

	public static final Color BACKGROUND_COLOR = Color.GRAY; // Background color of the window
}
