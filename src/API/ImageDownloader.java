package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class ImageDownloader {

	// Variables
	private HttpClient client; // Reference to the HttpClient instance

	public ImageDownloader(HttpClient client) {
		this.client = client;
	}

	public boolean downloadImage(String imageURL, String imageLocation) {
		Path imagePath = Path.of(imageLocation);
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(imageURL))
					.GET()
					.build();

			HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(imagePath));
			// Print Output if Issue Comes Up
			if (response.statusCode() != 200) {
				System.out.println(response.statusCode());
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
