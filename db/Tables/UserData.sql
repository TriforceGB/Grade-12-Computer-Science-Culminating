-- DROP TABLE IF EXISTS "UserData"; --Delete the UserData table If it Exists

CREATE TABLE IF NOT EXISTS "UserData" (
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
);
