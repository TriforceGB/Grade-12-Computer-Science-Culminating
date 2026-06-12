package API;

import java.net.http.HttpClient;
import java.time.Duration;

import com.google.gson.Gson;

import DB.DB;
import DTO.API.Response.AniListSearchResponce;
import DTO.API.Response.TheTVDBSearchRespose;

public class API {
	// Constants
	private final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
	// The Client Used to Connect to API
	public final HttpClient CLIENT = HttpClient.newBuilder()
			.connectTimeout(CONNECT_TIMEOUT) // Set the connect timeout
			.followRedirects(HttpClient.Redirect.NORMAL) // Follow redirects normally
			.build();

	// Variables
	private DB db; // Reference to the Local DB
	private Gson gson; // Reference to the Gson library
	private AniList aniList; // Reference to the AniList API
	private TheTVDB theTVDB; // Reference to the TheTVDB API

	public API(DB db) {
		this.db = db;
		this.gson = new Gson();
		this.aniList = new AniList(this.CLIENT, this.gson);
		this.theTVDB = new TheTVDB(this.CLIENT, this.gson);
	}

	/**
	 * Search AniList for x amount of shows related to the given name
	 *
	 * @param name   The name of this show(s) you are Searching for
	 * @param amount The number of show to return
	 * @return
	 */
	public AniListSearchResponce.Data.Page.Anime[] searchAnime(String name, int amount) {
		return aniList.searchAnime(name, amount).getAnime();
	}

	public TheTVDBSearchRespose.Data[] searchMovie(String name, int amount) {
		return theTVDB.Search(name, "Movie", amount).getData();
	}

}
