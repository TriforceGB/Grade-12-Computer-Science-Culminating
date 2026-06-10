package TableClass;

public class Media {
	// Base Info
	private int id;
	private int type;
	private int externalId;
	private String name;
	private String description;
	private String posterPath;

	// Extra Info
	private int status;
	private int rating;
	private int lastEpisode;
	private String review;
	private int rewatched;

	// Only Media
	public Media(int id, int type, int externalId, String name, String description, String posterPath) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
	}

	// Everything
	public Media(int id, int type, int externalId, String name, String description, String posterPath, int status,
			int rating, int lastEpisode, String review, int rewatched) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
		this.status = status;
		this.rating = rating;
		this.lastEpisode = lastEpisode;
		this.review = review;
		this.rewatched = rewatched;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getExternalId() {
		return externalId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public int getStatus() {
		return status;
	}

	public int getRating() {
		return rating;
	}

	public int getLastEpisode() {
		return lastEpisode;
	}

	public String getReview() {
		return review;
	}

	public int getRewatched() {
		return rewatched;
	}

}
