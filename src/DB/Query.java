package DB;

/**
 * This class has no Method but rather holds all the SQL queries used in the
 * application.
 * Everything is static and final and can only be used inside the DB.
 */
class Query {
	// Meta Queries
	// Stuff that create or alter tables not Data
	// Finds what Tables Exist
	public static final String FIND_TABLE = """
			SELECT name, COUNT(*) OVER() AS count
			FROM sqlite_master
			WHERE type = 'table'
			""";
	// Create Users Table
	public static final String CREATE_USERS_TABLE = """
			CREATE TABLE IF NOT EXISTS "User" (
				"id" INTEGER NOT NULL UNIQUE PRIMARY KEY,
				"username" TEXT NOT NULL UNIQUE,
				"password" TEXT NOT NULL,
				"isAdmin" BOOLEAN NOT NULL,
				"created" TEXT NOT NULL,
				"lastLogin" INTEGER NOT NULL
				)
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
	public static final String CREATE_USERDATA_TABLE = """

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
	// User Queries
	// Create User
	public static final String CREATE_USER = """
			INSERT INTO "User" ("username", "password", "isAdmin", "created", "lastLogin")
			VALUES (?, ?, ?, ?, ?)
			""";
	public static final String LOGIN_USER = """
			SELECT *
			FROM "User"
			WHERE "username" = ? AND "password" = ?
			""";
}
