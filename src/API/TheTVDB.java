package API;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import DTO.API.Request.TheTVDBLoginRequest;
import DTO.API.Response.TheTVDBLoginResponce;
import DTO.API.Response.TheTVDBSearchRespose;

public class TheTVDB {
	// Constants
	private String ENDPOINT = "https://api4.thetvdb.com/v4";
	private String KEY_PATH = "key/TVDB_KEY.txt";

	// Variables
	private HttpClient client; // Reference to the HttpClient instance
	private Gson gson; // Reference to the Gson instance
	private String token; // Token used for auth

	public TheTVDB(HttpClient client, Gson gson) {
		this.client = client;
		this.gson = gson;
		this.token = login();

	}

	private String getKey() {
		try (BufferedReader br = new BufferedReader(new FileReader(KEY_PATH))) {
			String key = br.readLine();
			return key;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private String login() {
		TheTVDBLoginRequest requestBody = new TheTVDBLoginRequest(getKey()); // Create the Json as an Object
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(ENDPOINT + "/login"))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Parse the response and return the token
			if (response.statusCode() == 200) {
				return gson.fromJson(response.body(), TheTVDBLoginResponce.class).getToken();
			} else {
				System.out.println(response.statusCode());
				System.out.println(response.body());
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public TheTVDBSearchRespose Search(String query, String type, int limit) {
		query = query.replace(" ", "%20"); // Replace spaces with %20 for URL encoding
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
			return gson.fromJson(response.body(), TheTVDBSearchRespose.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
