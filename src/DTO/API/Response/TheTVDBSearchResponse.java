package DTO.API.Response;

/**
 * This is the request object for the TheTVDB API.
 */
public class TheTVDBSearchResponse {
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

		/**
		 * This is needed as the Base Query can't give EP count so this has to be found
		 * in a different call
		 *
		 * @param episodes The Number of Episodes in a Show
		 */
		public void setEpisodeCount(int episodes) {
			episode_count = episodes;
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
