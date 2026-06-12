package DTO.API;

/**
 * Represents a message from the TVDB API.
 * Not used for Login because that is just a 1 line json
 */
public class TheTVDBMessage {
	private String query;
	private String type;
	private int limit;

	/**
	 * Create the Json as an Object
	 * 
	 * @param query The query to search for (String)
	 * @param type  The type of search to perform (String)
	 * @param limit The maximum number of results to return (int)
	 */
	public TheTVDBMessage(String query, String type, int limit) {
		this.query = query;
		this.type = type;
		this.limit = limit;
	}

}
