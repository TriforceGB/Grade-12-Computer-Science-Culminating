import UI.UI;
import DB.DB;

import com.google.gson.Gson;

import API.API;
import DB.DB;
import UI.UI;

public class FTS {
	// Settings
	private static String DB_PATH = "db/FTS.db";
	private static String TVDB_API_KEY = "key/TVDB_KEY.txt"; // Dir to TVDB API key

	// Variables
	@SuppressWarnings("unused")
	private static UI ui;
	private static DB db;
	private static API api;
	private static Gson gson;


	public static void main(String[] args) {
		gson = new Gson();
		db = new DB(DB_PATH);
		api = new API(TVDB_API_KEY, gson);
		ui = new UI(db, api, gson);
	}
}
