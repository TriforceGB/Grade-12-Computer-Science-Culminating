package DTO.API;

/**
 * Object that holds the values needed for the request for Anilist
 * The class is formatted for Gson usage
 */
public class AniListMessage {
	private String query; // The query for the request, holds what information we want
	private variables variables; // the values we give to help find the information we want

	/**
	 * Create the Message Object that can be send to API
	 *
	 * @param query  The query for the request, holds what information we want
	 *               (String)
	 * @param show   Name of Anime (String)
	 * @param amount Number of Anime to Return (int)
	 */
	public AniListMessage(String query, String show, int amount) {
		this.query = query;
		this.variables = new variables(show, amount);
	}

	private static class variables {
		private String show; // Name of Anime
		private int amount; // Number of Anime to Return

		variables(String show, int amount) {
			this.show = show;
			this.amount = amount;
		}
	}
}
