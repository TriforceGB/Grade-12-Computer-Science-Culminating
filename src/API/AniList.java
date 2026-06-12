package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import DTO.API.AniListMessage;
import DTO.API.AniListRequest;

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
			}""";

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

	public AniListRequest searchAnime(String name, int amount) {
		// Creating json
		AniListMessage message = new AniListMessage(SEARCH_QUERY, name, amount);

		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(API_URL))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(message)))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers
					.ofString());

			if (response.statusCode() != 200) {
				return null;
			} else {
				System.out.println(response.body());

			}
			return gson.fromJson(response.body(), AniListRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
}
