package DB;

/**
 * This class has no Method but rather holds all the SQL queries used in the
 * application.
 * Everything is static and final and can only be used inside the DB.
 */
class Query {
	// Meta Query
	// Stuff that create or alter tables not Data

	// This Find all Table in DB
	public static final String FIND_TABLE = """
	SELECT name
	FROM sqlite_master
	WHERE type = 'table'
	""";
	public static final String CREATE_TABLE = ""; //
}
