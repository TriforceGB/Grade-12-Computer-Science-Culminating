package DTO.LocalDB;

public class Media {
	// Base Info
	private int id; // The ID in the database
	private int type; // The type of media 1 = TV, 2 = Movie, 3 = Anime
	private int externalId; // The external ID from the API
	private String name; // Name of Media
	private String description; // Description of Media
	private int episodeCount;
	private String posterPath;
	private String posterLink;

	// Extra User Info
	private int status;
	private int rating;
	private int lastEpisode;
	private String review;
	private int rewatched;

	// Just Media Info
	public Media(int id, int type, int externalId, String name, String description, String posterPath,
			String posterLink) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
		this.posterLink = posterLink;
	}

	// Everything
	public Media(int id, int type, int externalId, String name, String description, String posterPath,
			String posterLink, int status, int rating, int lastEpisode, String review, int rewatched) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
		this.posterLink = posterLink;
		this.status = status;
		this.rating = rating;
		this.lastEpisode = lastEpisode;
		this.review = review;
		this.rewatched = rewatched;
	}

}
