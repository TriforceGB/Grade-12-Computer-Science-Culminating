package DTO.API;

public class TVDBData {
	// stuff for /login
	private String token; // Used for Login

	// stuff for /search
	private String id; // The ID of the data
	private String image_url; // The URL of the image associated with the data
	private String name; // The name of the data

	// Getters
	public String getToken() {
		return token;
	}

	public String getId() {
		return id;
	}

	public String getImage_url() {
		return image_url;
	}

	public String getName() {
		return name;
	}
}
