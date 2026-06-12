package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import DTO.API.TheTVDBMessage;
import DTO.API.TheTVDBRequest;

public class TheTVDB {
	// Constants
	private String ENDPOINT = "https://api4.thetvdb.com/v4";

	// Variables
	private HttpClient client; // Reference to the HttpClient instance
	private Gson gson; // Reference to the Gson instance
	private String token; // Token used for auth

	public TheTVDB(HttpClient client, Gson gson) {
		this.client = client;
		this.gson = gson;
		this.token = login();

	}

	private String login() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/login"))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString("{\"apikey\": \"" + API_KEY + "\"}"))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Parse the response and return the token
			return gson.fromJson(response.body(), TheTVDBRequest.class).getToken();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public TheTVDBRequest Search(String query, String type, int limit) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/search?" + "query=" + query + "&type=" + type + "&limit=" + limit))
					.header("Authorization", "Bearer " + this.token)
					.header("Content-Type", "application/json")
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.statusCode());
			System.out.println(response.body());
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
