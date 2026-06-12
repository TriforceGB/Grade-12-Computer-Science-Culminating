package DTO.API.Response;

/**
 * This is the request object for the TheTVDB API.
 */
public class TheTVDBSearchRespose {
	private Data[] data; // The data returned by the request
	private String status; // The status of the request (e.g. "success", "error")

	public class Data {
		// TODO add more Variables Here
		private String id; // The ID of the data
		private String image_url; // The URL of the image associated with the data
		private String name; // The name of the data
	}

	public Data[] getData() {
		return data;
	}

	public String getStatus() {
		return status;
	}
}
