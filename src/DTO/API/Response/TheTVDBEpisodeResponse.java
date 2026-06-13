package DTO.API.Response;

/**
 * The Sole Purpose of this class is to hold the number of item in the Episode
 * array and just get a length of that. everything else is secondary
 */
public class TheTVDBEpisodeResponse {
	private data data;
	private String status;

	public class data {
		private Episode[] episodes;

		public class Episode {
			private int absoluteNumber;
		}
	}

	public int getEpisodeCount() {
		return data.episodes.length;
	}
}
