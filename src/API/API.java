package API;

import java.net.http.HttpClient;
import java.time.Duration;

import com.google.gson.Gson;

import DB.DB;
import DTO.API.Response.TheTVDBSearchResponse.Data;
import DTO.API.Response.AniListSearchResponse.Data.Page.Anime;
import DTO.LocalDB.Media;

public class API {
	// Constants
	private final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
	// The Client Used to Connect to API
	public final HttpClient CLIENT = HttpClient.newBuilder()
			.connectTimeout(CONNECT_TIMEOUT) // Set the connect timeout
			.followRedirects(HttpClient.Redirect.NORMAL) // Follow redirects normally
			.build();

	// Variables
	private Gson gson; // Reference to the Gson library
	private AniList aniList; // Reference to the AniList API
	private TheTVDB theTVDB; // Reference to the TheTVDB API

	public API(String tvdb_api_key) {
		this.gson = new Gson();
		this.aniList = new AniList(this.CLIENT, this.gson);
		this.theTVDB = new TheTVDB(this.CLIENT, this.gson, tvdb_api_key);
	}

	/**
	 * Search AniList for x amount of shows related to the given name
	 *
	 * @param name   The name of this show(s) you are Searching for
	 * @param amount The number of show to return
	 * @return
	 */
	public Media[] searchAnime(String name, int amount) {
		Anime[] foundAnime = aniList.searchAnime(name, amount).getAnime();
		// Turns the Anime Class into a Media Class
		Media[] returnMedia = new Media[foundAnime.length];
		for (int i = 0; i < foundAnime.length; i++) {
			returnMedia[i] = new Media(foundAnime[i]);
		}
		return returnMedia; // Return Info as Media
	}

	public Media[] searchMovie(String name, int amount) {
		Data[] foundMovie = theTVDB.Search(name, "Movie", amount).getData();
		// Turns the Data Class into a Media Class
		Media[] returnMedia = new Media[foundMovie.length];
		for (int i = 0; i < foundMovie.length; i++) {
			returnMedia[i] = new Media(foundMovie[i]);
		}
		return returnMedia; // Return Info as Media

	}

	public Media[] searchShow(String name, int amount) {
		Data[] foundShow = theTVDB.Search(name, "Movie", amount).getData();
		// Turns the Data Class into a Media Class
		Media[] returnMedia = new Media[foundShow.length];
		for (int i = 0; i < foundShow.length; i++) {
			returnMedia[i] = new Media(foundShow[i]);
		}
		return returnMedia; // Return Info as Media
	}

}
