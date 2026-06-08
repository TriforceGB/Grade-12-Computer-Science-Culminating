import UI.UI;
import DB.DB;

public class FTS {
	// Settings
	private static String DB_PATH = "db/FTS.db";

	// Variables
	private static UI ui;
	private static DB db;

	public static void main(String[] args) {
		System.out.println("START");
		db = new DB(DB_PATH);
		// ui = new UI(db);
	}
}
