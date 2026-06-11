package DTO.API;

import DTO.LocalDB.Media;

public class AniListRequest {
	// Top Level of the Json
	public Data data;

	public static class Data {
		// Data only Holds Page
		public Page page;
	}

	public static class Page {
		public Media[] media;
	}
}
