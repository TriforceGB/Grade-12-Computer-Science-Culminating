package DTO.API;

/**
 * This is the request object for the TheTVDB API.
 */
public class TheTVDBRequest {
	private String status; // The status of the request (e.g. "success", "error")
	private TVDBData data; // The data returned by the request

	public String getToken() {
		return data.getToken();
	}

	public TVDBData getData() {
		return data;
	}
}
