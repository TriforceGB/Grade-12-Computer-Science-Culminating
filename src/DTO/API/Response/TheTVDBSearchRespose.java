package DTO.API.Response;

/**
 * This is the request object for the TheTVDB API.
 */
public class TheTVDBSearchRespose {
	private Data[] data; // The data returned by the request
	private String status; // The status of the request (e.g. "success", "error")

	public class Data {
		private int tvdb_id; // The ID of the data
		private String name; // The name of the data
		private String overview; // The description of the data
		private int episode_count; // The number of episodes (has to be added in a separate request)
		private String image_url; // The URL of the image associated with the data

		public int getId() {
			return tvdb_id;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return overview;
		}

		public int getEpisodeCount() {
			return episode_count;
		}

		public String getImageUrl() {
			return image_url;
		}
	}

	public Data[] getData() {
		return data;
	}

	public String getStatus() {
		return status;
	}
}
