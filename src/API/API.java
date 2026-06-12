package API;

import java.net.http.HttpClient;
import java.time.Duration;

import com.google.gson.Gson;

import DB.DB;
import DTO.API.AniListRequest;
import DTO.API.Anime;

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

	public API(DB db) {
		this.db = db;
		this.gson = new Gson();
		this.aniList = new AniList(this.CLIENT, this.gson);
	}

	/**
	 * Search AniList for x amount of shows related to the given name
	 *
	 * @param name   The name of this show(s) you are Searching for
	 * @param amount The number of show to return
	 * @return
	 */
	public Anime[] searchAnime(String name, int amount) {
		return aniList.searchAnime(name, amount).getAnime();
	}
}
