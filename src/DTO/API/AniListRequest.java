package DTO.API;

import com.google.gson.annotations.SerializedName;

/**
 * The Object that the AniList API returns.
 */
public class AniListRequest {
	// Top Level of the Json
	private Data data; // Private so no info is exposed

	// Getter
	public Anime[] getAnime() {
		return data.page.media;
	}
}

/**
 * First Layer of the Json, exist just for formatting
 */
class Data {
	// Data only Holds Page
	@SerializedName("Page")
	public Page page;
}

class Page {
	public Anime[] media;
}
