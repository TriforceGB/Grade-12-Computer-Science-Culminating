package DB;

/**
 * This class has no Method but rather holds all the SQL queries used in the
 * application.
 * Everything is static and final and can only be used inside the DB.
 */
class Query {
	// Meta Query
	// Stuff that create or alter tables not Data

	// Finds what Tables Exist
	public static final String FIND_TABLE = """
			SELECT name, count(*) as count
			FROM sqlite_master
			WHERE type = 'table'
			""";

	// Create Media Table
	public static final String CREATE_MEDIA_TABLE = """
			CREATE TABLE "Media" (
				"id" INTEGER NOT NULL UNIQUE PRIMARY KEY,
				"type" INTEGER NOT NULL,
				"externalId" INTEGER NOT NULL,
				"name" TEXT NOT NULL,
				"description" TEXT,
				"releaseDate" TEXT,
				"posterPath" TEXT
				)
			""";
	// Create UserData Table
	private static final String CREATE_USERDATA_TABLE = """
			CREATE TABLE "UserData" (
				"id" INTEGER NOT NULL UNIQUE PRIMARY KEY,
				"userId" INTEGER NOT NULL,
				"mediaId" INTEGER NOT NULL,
				"status" INTEGER,
				"startDate" TEXT,
				"finishDate" TEXT,
				"rating" INTEGER,
				"lastEpisode" INTEGER,
				"review" TEXT,
				"Rewatched" INTEGER,
				FOREIGN KEY ("userId") REFERENCES "Users"("id") ON DELETE CASCADE
				FOREIGN KEY ("mediaId") REFERENCES "Media"("id") ON DELETE CASCADE
				)
			""";
	// Create Users Table
	private static final String CREATE_USERS_TABLE = """
			CREATE TABLE IF NOT EXISTS "Users" (
				"id" INTEGER NOT NULL UNIQUE PRIMARY KEY,
				"username" TEXT NOT NULL UNIQUE,
				"password" TEXT NOT NULL,
				"isAdmin" BOOLEAN NOT NULL,
				"created" TEXT NOT NULL,
				"lastLogin" INTEGER NOT NULL
				)
			""";
}
