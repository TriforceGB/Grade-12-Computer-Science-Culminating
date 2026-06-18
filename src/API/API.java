package API;

import java.net.http.HttpClient;
import java.time.Duration;

import com.google.gson.Gson;

import DTO.API.Response.AniListSearchResponse.Data.Page.Anime;
import DTO.API.Response.TheTVDBSearchResponse.Data;
import DTO.LocalDB.Media;

public class API {
	// Constants
	private final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
	// The Client Used to Connect to API
	public final HttpClient CLIENT = HttpClient.newBuilder()
			.connectTimeout(CONNECT_TIMEOUT) // Set the connect timeout
			.followRedirects(HttpClient.Redirect.NORMAL) // Follow redirects normally
			.build(); // Makes

	// Variables
	private Gson gson; // Reference to the Gson library
	private AniList aniList; // Reference to the AniList API
	private TheTVDB theTVDB; // Reference to the TheTVDB API
	private ImageDownloader imageDownloader; // Reference to the ImageDownloader
	private String key_path; // Path to API Key

	/**
	 * Create the API Connection to all API Used
	 *
	 * @param tvdb_api_key The path to where we store the Key for TVDB (String)
	 * @param gson         a reference to the Gson library (gson)
	 */
	public API(String tvdb_api_key, Gson gson) {
		// Add Variables
		this.key_path = tvdb_api_key;
		this.gson = gson;
		// Create instances of the API classes
		this.aniList = new AniList(this.CLIENT, this.gson);
		this.theTVDB = new TheTVDB(this.CLIENT, this.gson, tvdb_api_key);
		this.imageDownloader = new ImageDownloader(this.CLIENT);
	}

	/**
	 * Search and Returns a List of Movies
	 *
	 * @param name   The Name of the Movie you are Looking for (String)
	 * @param amount The Number of Movies to Return (Int)
	 * @return a Media Array
	 */
	public Media[] searchMovie(String name, int amount) {
		Data[] foundMovie = theTVDB.Search(name, "Movie", amount).getData(); // Pull Movies from API
		// Turns the Data Class into a Media Class
		Media[] returnMedia = new Media[foundMovie.length];
		for (int i = 0; i < foundMovie.length; i++) {
			returnMedia[i] = new Media(foundMovie[i]);
		}
		return returnMedia; // Return Info as Media
	}

	/**
	 * Search and Returns TV Shows
	 *
	 * @param name   The Name of the Movie you are Looking for (String)
	 * @param amount The Number of Movies to Return (Int)
	 * @return A Media Array
	 */
	public Media[] searchShow(String name, int amount) {
		Data[] foundShow = theTVDB.Search(name, "Series", amount).getData(); // Pulls Data for API
		// Turns the Data Class into a Media Class
		Media[] returnMedia = new Media[foundShow.length];
		for (int i = 0; i < foundShow.length; i++) {
			returnMedia[i] = new Media(foundShow[i]);
		}
		return returnMedia; // Return Info as Media
	}

	/**
	 * Search AniList for x amount of shows related to the given name
	 *
	 * @param name   The name of this show(s) you are Searching for
	 * @param amount The number of show to return
	 * @return A Media Array
	 */
	public Media[] searchAnime(String name, int amount) {
		Anime[] foundAnime = aniList.searchAnime(name, amount).getAnime(); // Pulls Anime from API
		// Turns the Anime Class into a Media Class
		Media[] returnMedia = new Media[foundAnime.length];
		for (int i = 0; i < foundAnime.length; i++) {
			returnMedia[i] = new Media(foundAnime[i]);
		}
		return returnMedia; // Return Info as Media
	}

	/**
	 * Downloads an Image for a Given Media Object
	 *
	 * @param media A media Object with a PosterURL value
	 * @return If the Image was Downloaded
	 */
	public boolean downloadImage(Media media) {
		return imageDownloader.downloadImage(media.getPosterLink(), media.getPosterPath()); // Downloads the Image
	}

	/**
	 * Update the TheTVDB file with a new key and re-init the Token
	 *
	 * @param key The New Key
	 * @return if the File was Made
	 */
	public boolean updateKey(String key) {
		return theTVDB.updateKey(key, this.key_path); // Update the Key
	}
}
