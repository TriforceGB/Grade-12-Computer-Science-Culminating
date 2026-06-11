package DTO.LocalDB;

import com.google.gson.annotations.SerializedName;

public class Media {
	// Base Info
	@SerializedName("internalId")
	private int id; // The ID in the database
	private int type; // The type of media (e.g. TV, Movie)
	@SerializedName("id")
	private int externalId; // The external ID from the API
	@SerializedName("value = english, alternate = {romaji}")
	private String name; // Name of Media
	private String description; // Description of Media
	@SerializedName("episodes")
	private int episodeCount;
	private String posterPath;
	@SuppressWarnings("large")
	private String posterLink;

	// Extra Info
	private int status;
	private int rating;
	private int lastEpisode;
	private String review;
	private int rewatched;

	// Only Media
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
			String posterLink, int status,
			int rating, int lastEpisode, String review, int rewatched) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
		this.posterLink = null;
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
