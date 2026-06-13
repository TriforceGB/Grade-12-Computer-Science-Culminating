package DTO.LocalDB;

import DTO.API.Response.AniListSearchResponse;
import DTO.API.Response.TheTVDBSearchResponse;

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

	public Media(AniListSearchResponse.Data.Page.Anime response) {
		this.id = 0; // Unknown (DB will give it a real one later)
		this.type = response.getType();
		this.externalId = response.getId();
		this.name = response.getName();
		this.description = response.getDescription();
		this.posterPath = "assets/Images/Anime/" + response.getId() + ".png";
		this.posterLink = response.getImageUrl();
	}

	public Media(TheTVDBSearchResponse.Data response) {
		this.id = 0; // Unknown (DB will give it a real one later)
		this.type = response.getType();
		this.externalId = response.getId();
		this.name = response.getName();
		this.description = response.getDescription();
		if (response.getType() == 1) {
			this.posterPath = "assets/Images/Movies/" + response.getId() + ".jpg";
		} else {
			this.posterPath = "assets/Images/Shows/" + response.getId() + ".jpg";
		}
		this.posterLink = response.getImageUrl();
	}

	// Getter
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

	public int getEpisodeCount() {
		return episodeCount;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getPosterLink() {
		return posterLink;
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
