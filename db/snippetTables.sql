
CREATE TABLE Snippet(
	snippetID VARCHAR(30) PRIMARY KEY,
    creatorID VARCHAR(30),
	text NVARCHAR2(4000),
    info VARCHAR(1000),
    codeLanguage VARCHAR(10),
    viewerPassword VARCHAR(30),
    creatorPassword VARCHAR(30),
    snippetTimestamp DATE,
    URL VARCHAR(30)
);

CREATE TABLE SnippetComment(
	commentID VARCHAR(30) PRIMARY KEY,
	timeStamp DATE,
	text VARCHAR(1000),
	startLine INT,
	endLine INT,
    FOREIGN KEY (snippetID) REFERENCES Snippet(snippetID)
);

