import UI.UI;
import DB.DB;
import DTO.API.Response.TheTVDBSearchResponse.Data;
import DTO.LocalDB.Media;
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
		Data[] anime = api.searchShow("Lucky Star", 3);
		Media[] media = new Media[anime.length];
		for (int i = 0; i < anime.length; i++) {
			media[i] = new Media(anime[i]);
		}
		for (Media media2 : media) {
			System.out.println(media2.getName());
		}
		ui = new UI(db);
	}
}
