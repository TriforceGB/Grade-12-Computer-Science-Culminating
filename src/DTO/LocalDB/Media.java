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
	private int episodeCount; // The Total Number of Episodes
	private String posterPath; // The path to the poster image
	private String posterLink; // The URL of the poster image

	// UserData
	private UserData userData;

	/**
	 * Stores Just media Information no User Info
	 *
	 * @param id          The ID of in the database (int)
	 * @param type        The type of media (int)
	 * @param externalId  The external ID from the API (int)
	 * @param name        The name of the media (String)
	 * @param description The description of the media (String)
	 * @param posterPath  The path to the poster image (String)
	 * @param posterLink  The URL of the poster image (String)
	 */
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

	/**
	 * Stores Everything about media related to the user
	 *
	 * @param id          The ID of in the database (int)
	 * @param type        The type of media (int)
	 * @param externalId  The external ID from the API (int)
	 * @param name        The name of the media (String)
	 * @param description The description of the media (String)
	 * @param posterPath  The path to the poster image (String)
	 * @param posterLink  The URL of the poster image (String)
	 * @param status      The status of the media (int)
	 * @param startDate   When the User Started Watching (String)
	 * @param finishDate  When the User Finish Watching (String)
	 * @param rating      User Rating (int)
	 * @param lastEpisode The last episode watched (int)
	 * @param review      User Review (String)
	 * @param rewatched   The number of times the media has been rewatched (int)
	 */
	public Media(int id, int type, int externalId, String name, String description, String posterPath,
			String posterLink, int status, String startDate, String finishDate, int rating, int lastEpisode,
			String review, int rewatched) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
		this.posterLink = posterLink;
		this.userData = new UserData(status, startDate, finishDate, rating, lastEpisode, review, rewatched);
	}

	/**
	 * Stores Everything about media related to the user (but with a UserData
	 * Object Input)
	 *
	 * @param id          The ID of in the database (int)
	 * @param type        The type of media (int)
	 * @param externalId  The external ID from the API (int)
	 * @param name        The name of the media (String)
	 * @param description The description of the media (String)
	 * @param posterPath  The path to the poster image (String)
	 * @param posterLink  The URL of the poster image (String)
	 * @param userData    an User Data Object that holds UserData (UserData)
	 */
	public Media(int id, int type, int externalId, String name, String description, String posterPath,
			String posterLink, UserData userData) {
		this.id = id;
		this.type = type;
		this.externalId = externalId;
		this.name = name;
		this.description = description;
		this.posterPath = posterPath;
		this.posterLink = posterLink;
		this.userData = userData;
	}

	/**
	 * Imports a Anime from Anilist into the Media format
	 *
	 * @param response The response from the Anilist API
	 *                 (AniListSearchResponse.Data.Page.Anime)
	 */
	public Media(AniListSearchResponse.Data.Page.Anime response) {
		this.name = response.getName();
		this.description = response.getDescription();
		this.type = response.getType();
		this.externalId = response.getId();
		this.posterPath = "assets/Images/Anime/" + response.getId() + ".png";
		this.posterLink = response.getImageUrl();
	}

	/**
	 * Imports a Movie/Show from TheTVDB into the Media format
	 *
	 * @param response The response from the TheTVDB API
	 *                 (TheTVDBSearchResponse.Data)
	 */
	public Media(TheTVDBSearchResponse.Data response) {
		this.name = response.getName();
		this.description = response.getDescription();
		this.type = response.getType();
		this.externalId = response.getId();
		if (response.getType() == 1) {
			this.posterPath = "assets/Images/Movies/" + response.getId() + ".jpg";
		} else {
			this.posterPath = "assets/Images/Shows/" + response.getId() + ".jpg";
		}
		this.posterLink = response.getImageUrl();

	}

	/**
	 * This class holds user information about a piece of media
	 */
	public static class UserData {
		private int status; // The status of the media (0 = undefined, 1 = dropped, 2 = backlogged, 3 =
							// watching, 4 = completed)
		private String startDate; // When the User Started Watching the Media
		private String finishDate; // When the User Finish Watching the Media
		private int rating; // User Rating
		private int lastEpisode; // The last episode watched
		private String review; // User Review
		private int rewatched; // The number of times the media has been rewatched

		/**
		 * Create an Object with info about media related to a show
		 *
		 * @param status      What Status is the Media
		 *                    (0 = undefined, 1 = dropped, 2 = backlogged, 3 = watching,
		 *                    4 = completed)
		 * @param startDate   When did you start The Media
		 * @param finishDate  When did you Finish the Media
		 * @param rating      What the user rating is on the media
		 * @param lastEpisode What is the last Episode the User Watched
		 * @param review      The User Review
		 * @param rewatched   How many time have they rewatched it
		 */
		public UserData(int status, String startDate, String finishDate, int rating, int lastEpisode, String review,
				int rewatched) {
			this.status = status;
			this.startDate = startDate;
			this.finishDate = finishDate;
			this.rating = rating;
			this.lastEpisode = lastEpisode;
			this.review = review;
			this.rewatched = rewatched;
		}

		// Getters
		public int getStatus() {
			return status;
		}

		public String getStartDate() {
			return startDate;
		}

		public String getFinishDate() {
			return finishDate;
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
		return userData.status;
	}

	public String getStartDate() {
		return userData.startDate;
	}

	public String getFinishDate() {
		return userData.finishDate;
	}

	public int getRating() {
		return userData.rating;
	}

	public int getLastEpisode() {
		return userData.lastEpisode;
	}

	public String getReview() {
		return userData.review;
	}

	public int getRewatched() {
		return userData.rewatched;
	}

}
