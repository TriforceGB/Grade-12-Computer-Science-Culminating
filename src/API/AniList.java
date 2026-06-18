package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import DTO.API.Request.AniListSearchRequest;
import DTO.API.Response.AniListSearchResponse;

class AniList {
	// Constants
	private String API_URL = "https://graphql.anilist.co"; // Link to AniList API

	// The Query Used for AniList
	// Searches for a show based off of name
	private String SEARCH_QUERY = """
			query ($show: String, $amount: Int) {
				Page (page: 1, perPage: $amount) {
					media (search: $show, type: ANIME) {
						id
						title {
							english
							romaji
						}
						episodes
						description
						coverImage {
							large
						}
					}
				}
			}
			""";

	// Variables
	private HttpClient client; // Reference to the HttpClient used to connect to the API
	private Gson gson; // Reference to the Gson used to parse the API response

	/**
	 * Create the client for the AniList API
	 */
	public AniList(HttpClient client, Gson gson) {
		this.client = client;
		this.gson = gson;
	}

	/**
	 * Take a Given Search and Returns what it gets from the API
	 *
	 * @param name   The name of the show you are looking for (String)
	 * @param amount The amount of results you want back (int)
	 * @return The response from the API (AniListSearchResponse)
	 */
	public AniListSearchResponse searchAnime(String name, int amount) {
		// Creating json
		AniListSearchRequest message = new AniListSearchRequest(SEARCH_QUERY, name, amount);

		// Try and Make a http POST Request
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(API_URL)) // URL
					.header("Content-Type", "application/json") // What we are expecting back
					.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(message))) // Our Message
					.build();

			// Take Response as String
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) { // If not 200 Return Null
				return null;
			} else { // If 200 Then Return in for the form of the AniListSearchResponse Object
				return gson.fromJson(response.body(), AniListSearchResponse.class);
			}
		} catch (Exception e) { // Exception
			System.err.println("Exception While Searching on AniList:");
			e.printStackTrace();
			return null;
		}
	}
}
