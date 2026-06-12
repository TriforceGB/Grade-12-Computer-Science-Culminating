package DTO.API.Response;

public class TheTVDBLoginResponce {
	private Data data; // The data returned by the request
	private String status; // The status of the request (e.g. "success", "error")

	public class Data {
		private String token;

	}

	public String getToken() {
		return data.token;
	}
}
