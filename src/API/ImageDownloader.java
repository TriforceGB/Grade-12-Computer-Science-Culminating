package API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class ImageDownloader {

	// Variables
	private HttpClient client; // Reference to the HttpClient instance

	/**
	 * Create the Object that Lets User Download Images from the Internet
	 *
	 * @param client A reference to the HttpClient instance
	 */
	public ImageDownloader(HttpClient client) {
		// Passing Variables
		this.client = client;
	}

	/**
	 * Download an Image with the Given URL
	 *
	 * @param imageURL      The Image we want to download
	 * @param imageLocation Where the Program should store the image
	 * @return If the Image was Saved
	 */
	public boolean downloadImage(String imageURL, String imageLocation) {
		Path imagePath = Path.of(imageLocation); // Create the Path to where the Image is stored

		// Create a HTTP GET Request for the Image
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(imageURL)) // Link to the Image
					.GET()
					.build();

			// Take the File as a Response
			// Tells it where to store the file in the response
			HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(imagePath));
			// Print Output if Issue Comes Up
			if (response.statusCode() != 200) {
				System.out.println(response.statusCode());
				return false;
			} else {
				return true; // Everything Worked
			}
		} catch (Exception e) {
			System.out.println("Exception While Downloading Image:");
			e.printStackTrace();
			return false;
		}
	}
}
