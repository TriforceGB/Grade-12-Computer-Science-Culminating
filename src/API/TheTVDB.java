package API;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import DTO.API.Request.TheTVDBLoginRequest;
import DTO.API.Response.TheTVDBEpisodeResponse;
import DTO.API.Response.TheTVDBLoginResponse;
import DTO.API.Response.TheTVDBSearchResponse;

public class TheTVDB {
	// Constants
	private String ENDPOINT = "https://api4.thetvdb.com/v4";

	// Variables
	private HttpClient client; // Reference to the HttpClient instance
	private Gson gson; // Reference to the Gson instance
	private String token; // Token used for auth

	public TheTVDB(HttpClient client, Gson gson, String key_path) {
		this.client = client;
		this.gson = gson;
		this.token = login(key_path);

	}

	private String getKey(String key_path) {
		try (BufferedReader br = new BufferedReader(new FileReader(key_path))) {
			String key = br.readLine();
			return key;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private String login(String key_path) {
		TheTVDBLoginRequest requestBody = new TheTVDBLoginRequest(getKey(key_path)); // Create the Json as an Object
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/login"))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Parse the response and return the token
			if (response.statusCode() == 200) {
				return gson.fromJson(response.body(), TheTVDBLoginResponse.class).getToken();
			} else {
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public TheTVDBSearchResponse Search(String query, String type, int limit) {
		query = query.replace(" ", "%20"); // Replace spaces with %20 for URL encoding
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/search?" + "query=" + query + "&type=" + type + "&limit=" + limit))
					.header("Authorization", "Bearer " + this.token)
					.header("Content-Type", "application/json")
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			// Print Output if Issue Comes Up
			if (response.statusCode() != 200) {
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return null;
			}
			TheTVDBSearchResponse formattedResponse = gson.fromJson(response.body(), TheTVDBSearchResponse.class);
			setEpisodeCount(formattedResponse, type);
			return formattedResponse;
		} catch (Exception e) {
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
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/series/" + tvdb_id + "/extended?meta=episodes"))
					.header("Authorization", "Bearer " + this.token)
					.header("Content-Type", "application/json")
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) {
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return 1;
			}
			TheTVDBEpisodeResponse formattedResponse = gson.fromJson(response.body(), TheTVDBEpisodeResponse.class);
			return formattedResponse.getEpisodeCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
}
