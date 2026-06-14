package DTO.API.Request;

/**
 * Object that holds the values needed for the request for Anilist
 * The class is formatted for Gson usage
 */
public class AniListSearchRequest {
	@SuppressWarnings("unused")
	private String query; // The query for the request, holds what information we want
	@SuppressWarnings("unused")
	private variables variables; // the values we give to help find the information we want

	/**
	 * Create the Message Object that can be send to API
	 *
	 * @param query  The query for the request, holds what information we want
	 *               (String)
	 * @param show   Name of Anime (String)
	 * @param amount Number of Anime to Return (int)
	 */
	public AniListSearchRequest(String query, String show, int amount) {
		this.query = query;
		this.variables = new variables(show, amount);
	}

	/**
	 * Send Item of the Json that holds the Value used in the Search
	 */
	private static class variables {
		@SuppressWarnings("unused")
		private String show; // Name of Anime
		@SuppressWarnings("unused")
		private int amount; // Number of Anime to Return

		/**
		 * Create the variables part of the Json
		 *
		 * @param show   taken from the Main Class (String)
		 * @param amount taken from the Main Class (int)
		 */
		variables(String show, int amount) {
			this.show = show;
			this.amount = amount;
		}
	}
}
