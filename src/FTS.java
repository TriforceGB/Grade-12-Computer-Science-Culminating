import UI.UI;
import DB.DB;
import TableClass.*;

public class FTS {
	// Settings
	private static String DB_PATH = "db/FTS.db";

	// Variables
	private static UI ui;
	private static DB db;

	public static void main(String[] args) {
		db = new DB(DB_PATH);
		User user = db.login("admin", "admin");

		if (user != null) {
			System.out.println("Login Successful: " + user.getUsername());
		}
		// ui = new UI(db);
	}
}
