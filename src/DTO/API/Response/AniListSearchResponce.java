package DTO.API.Response;

import com.google.gson.annotations.SerializedName;

/**
 * The Object that the AniList API returns.
 */
public class AniListSearchResponce {
	// Top Level of the Json
	private Data data; // Private so no info is exposed

	/**
	 * First Layer of the Json, exist just for formatting
	 */
	public class Data {
		// Data only Holds Page
		@SerializedName("Page")
		private Page page;

		/**
		 * The Page of the Json, holds the media
		 */
		public class Page {
			private Anime[] media;

			/**
			 * The Class which holds info on a Anime. like Media Class but different so Gson
			 * works
			 */
			public class Anime {
				private int id;
				private Title title;
				private int episodes;
				private String description;
				private CoverImage coverImage;

				// Classes To Hold Data and Help with Gson
				public class Title {
					private String english;
					private String romaji;
				}

				public class CoverImage {
					private String large;
				}

				// Getters
				public int getId() {
					return id;
				}

				public String getTitle() {
					// If no English title was found, return the romaji title
					if (title.english == null) {
						return title.romaji;
					}
					return title.english;
				}

				public int getEpisodes() {
					return episodes;
				}

				public String getDescription() {
					return description;
				}

				public String getCoverImage() {
					// Return the String rather then the Object
					return coverImage.large;
				}

			}
		}

	}

	// Getter
	public AniListSearchResponce.Data.Page.Anime[] getAnime() {
		return this.data.page.media;
	}

}
