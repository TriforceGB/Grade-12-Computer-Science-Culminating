package DTO.API;

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
	private class Title {
		public String english;
		public String romaji;
	}

	private class CoverImage {
		public String large;
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
