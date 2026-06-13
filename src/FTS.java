import UI.UI;
import DB.DB;
import DTO.API.Response.AniListSearchResponse;
import DTO.API.Response.TheTVDBSearchResponse;
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
		ui = new UI(db);
	}
}
