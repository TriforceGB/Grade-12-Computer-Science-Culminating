import UI.UI;
import DB.DB;
import DTO.API.Response.AniListSearchResponce;
import DTO.API.Response.TheTVDBSearchRespose;
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
		AniListSearchResponce.Data.Page.Anime[] teest = api.searchAnime("Attack", 5);
		TheTVDBSearchRespose.Data[] movies = api.searchMovie("Backrooms", 2);

		ui = new UI(db);
	}
}
