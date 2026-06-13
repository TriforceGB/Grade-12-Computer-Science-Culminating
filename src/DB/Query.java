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
				"episodeCount" INTEGER,
				"releaseDate" TEXT,
				"posterPath" TEXT,
				"posterLink" TEXT
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
				"rewatched" INTEGER,
				FOREIGN KEY ("userId") REFERENCES "Users"("id") ON DELETE CASCADE
				FOREIGN KEY ("mediaId") REFERENCES "Media"("id") ON DELETE CASCADE
				)
			""";
	// User Queries
	// Creation, Editing, Deletion of Users
	public static final String CREATE_USER = """
			INSERT INTO "User" ("username", "password", "isAdmin", "created", "lastLogin")
			VALUES (?, ?, ?, ?, ?)
			""";
	public static final String EDIT_USER = """
			UPDATE "User"
			SET "username" = ?, "password" = ?, "isAdmin" = ?
			WHERE "id" = ?
			""";
	public static final String DELETE_USER = """
			DELETE FROM "User"
			WHERE "id" = ?
			""";

	// Finds a user Via Username and Password
	public static final String LOGIN_USER = """
			SELECT *
			FROM "User"
			WHERE "username" = ? AND "password" = ?
			""";
	// Update the last login time of a user
	public static final String UPDATE_LOGIN = """
			UPDATE "User"
			SET "lastLogin" = ?
			WHERE "id" = ?
			""";

	// Media Queries
	// Creation, Editing, Deletion of Media
	public static final String CREATE_MEDIA = """
			INSERT INTO "Media" ("type", "externalId", "name", "description", "episodeCount", "posterPath", "posterLink")
			VALUES (?, ?, ?, ?, ?, ?, ?)
			""";
	public static final String EDIT_MEDIA = """
			UPDATE "Media"
			SET "type" = ?, "externalId" = ?, "name" = ?, "description" = ?, "episodeCount" = ?, "posterPath" = ?, "posterLink" = ?
			WHERE "id" = ?
			""";
	public static final String DELETE_MEDIA = """
			DELETE FROM "Media"
			WHERE "id" = ?
			""";

	public static final String FIND_MEDIA = """
				SELECT
					m.*,
					ud.status,
					ud.rating,
					ud.lastEpisode,
					ud.startDate,
					ud.finishDate,
					ud.rewatched,
					count(*) OVER() AS count
				FROM Media AS m
				JOIN UserData AS ud
				WHERE
					m.id = ud.mediaId AND
					ud.userId = ? AND
					ud.type = IN (?, ?, ?) AND
					ud.status = ? AND
					m.name LIKE ? AND
					ud.rating > ? AND
					ud.rating < ?
				SORT BY ud.rating DESC
			""";
	// NOTE Sort might want to change

}
