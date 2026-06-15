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
				"posterPath" TEXT,
				"posterLink" TEXT,
				UNIQUE(externalId,type)
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
				UNIQUE(userId,mediaId),
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
			SELECT *, count(*) OVER() AS count
			FROM "User"
			WHERE "username" = ? AND "password" = ?
			""";
	// Update the last login time of a user
	public static final String UPDATE_LOGIN = """
			UPDATE "User"
			SET "lastLogin" = ?
			WHERE "id" = ?
			""";
	// Finds all Users
	public static final String ALL_USERS = """
			SELECT *
			FROM "User"
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
					ud.startDate,
					ud.finishDate,
					ud.rating,
					ud.lastEpisode,
					ud.review,
					ud.rewatched,
					count(*) OVER() AS count
				FROM Media AS m
				LEFT JOIN UserData AS ud ON m.id = ud.mediaId
				WHERE
					m.name LIKE ? AND
					m.type IN (?, ?, ?) AND
					(ud.userId = ? OR ud.userId IS NULL) AND
					COALESCE(ud.status, 0) IN (?, ?, ?, ?, ?) AND
					COALESCE(ud.rating, 0) BETWEEN ? AND ?
			""";
	public static final String ALL_MEDIA = """
				SELECT
					m.*,
					count(*) OVER() AS count
				FROM Media AS m
			""";
	// User Data Query
	// Create Edit Delete
	public static final String CREATE_USERDATA = """
			INSERT INTO "UserData" ("userId", "mediaId", "status", "startDate", "finishDate", "rating", "lastEpisode", "review", "rewatched")
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";
	public static final String EDIT_USERDATA = """
			UPDATE "UserData"
			SET "status" = ?, "startDate" = ?, "finishDate" = ?, "rating" = ?, "lastEpisode" = ?, "review" = ?, "rewatched" = ?
			WHERE "userId" = ? AND "mediaID" = ?
			""";
	public static final String DELETE_USERDATA = """
			DELETE FROM "UserData"
			WHERE "userId" = ? AND "mediaID" = ?
			""";

	// Export all Media that has a relationship with the UserData from Current User
	public static final String EXPORT_USER_RELATION = """
				SELECT
					m.*,
					ud.status,
					ud.startDate,
					ud.finishDate,
					ud.rating,
					ud.lastEpisode,
					ud.review,
					ud.rewatched,
					COUNT(*) OVER() AS count
				FROM Media AS m
				JOIN UserData AS ud ON m.id = ud.mediaId
				WHERE ud.userId = ?
			""";
}
