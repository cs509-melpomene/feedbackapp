
CREATE TABLE Snippet(
	snippetID VARCHAR(30) PRIMARY KEY,
	text VARCHAR(4000),
    info VARCHAR(1000),
    codeLanguage VARCHAR(10),
    viewerPassword VARCHAR(30),
    creatorPassword VARCHAR(30),
    snippetTimestamp DATE,
    URL VARCHAR(30)
);

