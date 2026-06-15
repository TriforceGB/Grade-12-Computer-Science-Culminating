import UI.UI;
import DB.DB;
import API.API;

public class FTS {
	// Settings
	private static String DB_PATH = "db/FTS.db";
	private static String TVDB_API_KEY = "key/TVDB_KEY.txt"; // Dir to TVDB API key

	// Variables
	@SuppressWarnings("unused")
	private static UI ui;
	private static DB db;
	private static API api;

	public static void main(String[] args) {
		db = new DB(DB_PATH);
		api = new API(TVDB_API_KEY);
		ui = new UI(db, api);
	}
}
