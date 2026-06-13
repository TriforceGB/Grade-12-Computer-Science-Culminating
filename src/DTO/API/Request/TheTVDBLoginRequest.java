package DTO.API.Request;

/**
 * Used to Represent a Login Message to the TVDB API
 */
public class TheTVDBLoginRequest {
	@SuppressWarnings("unused")
	private String apikey;

	public TheTVDBLoginRequest(String apikey) {
		this.apikey = apikey;
	}

}
