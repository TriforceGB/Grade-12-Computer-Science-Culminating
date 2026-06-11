-- DROP TABLE IF EXISTS "Media"; --Delete the Media table If it Exists

CREATE TABLE IF NOT EXISTS "Media" (
	"id" INTEGER NOT NULL UNIQUE PRIMARY KEY,
	"type" INTEGER NOT NULL,
	"externalId" INTEGER NOT NULL, -- Might not be an Int
	"name" TEXT NOT NULL,
	"description" TEXT,
	"episodeCount" INTEGER,
	"posterPath" TEXT,
	"posterLink" TEXT
);
