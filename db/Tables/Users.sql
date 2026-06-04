-- DROP TABLE IF EXISTS "Users"; --Delete the Users table If it Exists

CREATE TABLE IF NOT EXISTS "Users" (
	"id" INTEGER NOT NULL UNIQUE PRIMARY KEY,
	"username" TEXT NOT NULL UNIQUE,
	"password" TEXT NOT NULL,
	"isAdmin" BOOLEAN NOT NULL,
	"created" TEXT NOT NULL,
	"lastLogin" INTEGER NOT NULL
);
