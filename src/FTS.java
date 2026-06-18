//Name:Zach.S, Michael.R, Ryan.B, Bilal.F
//Date: June 5th 2026 - June 18th 2026
//Description: A Media Tracker that allows the user to find Shows Movies and Anime and Save them to a local database to rate and keep track of them
//Purpose: To Satisfy the clients craving for a Media Tracker

import UI.UI;
import DB.DB;
import API.API;

import com.google.gson.Gson;

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
		api = new API(TVDB_API_KEY, gson);
		db = new DB(DB_PATH);
		ui = new UI(db, api, gson);
	}
}
