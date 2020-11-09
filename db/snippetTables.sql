
CREATE TABLE Snippet(
	snippetID VARCHAR(30) PRIMARY KEY,
	text TEXT,
    info VARCHAR(1000),
    codeLanguage VARCHAR(10),
    viewerPassword VARCHAR(30),
    creatorPassword VARCHAR(30),
    snippetTimestamp DATETIME
);

CREATE TABLE SnippetComment(
	commentID VARCHAR(30) PRIMARY KEY,
	timeStamp DATETIME,
	text VARCHAR(1000),
	startLine INT,
	endLine INT,
	snippetID VARCHAR(30),
    CONSTRAINT fk_snippetID FOREIGN KEY (snippetID) REFERENCES SnippetDB.Snippet(snippetID) ON DELETE CASCADE
);
