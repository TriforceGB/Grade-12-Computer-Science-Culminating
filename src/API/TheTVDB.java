package API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import DTO.API.Request.TheTVDBLoginRequest;
import DTO.API.Response.TheTVDBEpisodeResponse;
import DTO.API.Response.TheTVDBLoginResponse;
import DTO.API.Response.TheTVDBSearchResponse;

/**
 * Our Connection to the TVDB Server
 */
public class TheTVDB {
	// Constants
	private String ENDPOINT = "https://api4.thetvdb.com/v4";

	// Variables
	private HttpClient client; // Reference to the HttpClient instance
	private Gson gson; // Reference to the Gson instance
	private String token; // Token used for auth

	/**
	 * Init the Connection to the Server
	 *
	 * @param client   Reference to the HttpClient instance
	 * @param gson     Reference to the Gson instance
	 * @param key_path The Path to the File with the Key for TVDB
	 */
	public TheTVDB(HttpClient client, Gson gson, String key_path) {
		// Passing Variables
		this.client = client;
		this.gson = gson;
		// Getting a Token for the DB
		this.token = login(key_path);

	}

	/**
	 * Gets the Key Written into the Key path
	 *
	 * @param key_path The Location of the Key
	 * @return The Key Read from the File
	 */
	private String getKey(String key_path) {
		// Create a File Reader
		try (BufferedReader br = new BufferedReader(new FileReader(key_path))) {
			String key = br.readLine(); // Read the First Line
			return key;
		} catch (Exception e) {
			System.out.println("Error while Reading Key:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Update The key that is being used and re runs the login Code
	 *
	 * @param newKey   The New Key to Write to the File
	 * @param key_path The Path to the Key File
	 * @return if the token was gotten
	 */
	public boolean updateKey(String newKey, String key_path) {
		// Create a Writer
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(key_path))) {
			writer.write(newKey); // Write the Key to File
		} catch (Exception e) {
			System.out.println("Error while Writing Key:");
			e.printStackTrace();
		}
		token = this.login(key_path); // Get the Token
		return token != null; // Returns if we got a Token
	}

	/**
	 * Logs into the TVDB with our API Key
	 *
	 * @param key_path the location of the Key
	 * @return The Token which is needed for every other API Call
	 */
	private String login(String key_path) {
		TheTVDBLoginRequest requestBody = new TheTVDBLoginRequest(getKey(key_path)); // Create the Json as an Object

		// Make a HTTP POST Request
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/login")) // Goes to TVDB/login
					.header("Content-Type", "application/json") // We want a JSON Back
					// What we are sending with the Request
					.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Parse the response and return the token
			if (response.statusCode() == 200) {
				// return the Json of the item found
				return gson.fromJson(response.body(), TheTVDBLoginResponse.class).getToken();
			} else {
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception on Login:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Request A List of Movies or Shows Base on the given Variables
	 *
	 * @param query The Search Term for the Media
	 * @param type  If its a Movie or Show
	 * @param limit How many to Return
	 * @return The Response from the API
	 */
	public TheTVDBSearchResponse Search(String query, String type, int limit) {

		// Replaces Spaces with %20
		query = query.replace(" ", "%20"); // Replace spaces with %20 for URL encoding
		// Does a GET Request
		try {
			HttpRequest request = HttpRequest.newBuilder()
					// Add the Name, Type, Limit
					.uri(new URI(ENDPOINT + "/search?" + "query=" + query + "&type=" + type + "&limit=" + limit))
					.header("Authorization", "Bearer " + this.token) // Give the Token
					.header("Content-Type", "application/json") // We want a Json
					.GET()
					.build();

			// Get the Response as a String
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			// Print Output if Issue Comes Up
			if (response.statusCode() != 200) {
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return null;
			}
			// Turn String into Object
			TheTVDBSearchResponse formattedResponse = gson.fromJson(response.body(), TheTVDBSearchResponse.class);
			setEpisodeCount(formattedResponse, type); // Find the total EP count for the Show / Movie
			return formattedResponse; // return the Media
		} catch (Exception e) {
			System.out.println("Exception on Search:");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * A Shell method that finds all shows in a Response and sets their episode
	 * count
	 *
	 * @param searchResponse The Request that we are finding the episode count for
	 * @param type           if the request is a movie or series
	 */
	private void setEpisodeCount(TheTVDBSearchResponse searchResponse, String type) {
		// Loop through the data and set the episode count for each show
		for (int i = 0; i < searchResponse.getData().length; i++) {
			// Gets the TVDB ID of the show
			int tvdb_id = searchResponse.getData()[i].getId();
			// Gets the episode count for the show from the API
			if (type.equals("Movie")) {
				searchResponse.getData()[i].setEpisodeCount(1); // Movie have 1 EP
			} else {
				int episode_count = getEpisodeCount(tvdb_id);
				// Sets the episode count for the show in the search response
				searchResponse.getData()[i].setEpisodeCount(episode_count);
			}
		}
	}

	/**
	 * Find the Episode Count for a show with the Given ID
	 *
	 * @param tvdb_id the ID of the show to find the episode count for
	 * @return the number of episodes for the show with the given ID
	 */
	private int getEpisodeCount(int tvdb_id) {
		// Create a HTTPS GET Request
		try {
			HttpRequest request = HttpRequest.newBuilder()
					// Find the Show with the extra Episode info
					.uri(new URI(ENDPOINT + "/series/" + tvdb_id + "/extended?meta=episodes"))
					.header("Authorization", "Bearer " + this.token) // Gives it the Token
					.header("Content-Type", "application/json")
					.GET()
					.build();

			// Take the Response and Convert it to a String
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// If Unable to find a response
			if (response.statusCode() != 200) {
				System.out.println("Unable to find episode count for show with ID: " + tvdb_id);
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return 1;
			}
			// Formate the Response into a Object
			TheTVDBEpisodeResponse formattedResponse = gson.fromJson(response.body(), TheTVDBEpisodeResponse.class);
			return formattedResponse.getEpisodeCount(); // return the total Count
		} catch (Exception e) {
			System.out.println("Error while getting episode count for show with ID: " + tvdb_id);
			e.printStackTrace();
			return 1;
		}
	}
}
