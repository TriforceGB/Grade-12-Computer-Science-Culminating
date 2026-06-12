import UI.UI;
import DB.DB;
import API.API;

public class FTS {
	// Settings
	private static String DB_PATH = "db/FTS.db";

	// Variables
	private static UI ui;
	private static DB db;
	private static API api;

	public static void main(String[] args) {
		db = new DB(DB_PATH);
		api = new API(db);
		api.searchMovie("Backroom", 3);
		ui = new UI(db);
	}
}
