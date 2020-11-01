package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class UpdateComments {
//	public void addComment() {
//		// How to tell which snippet I'm referencing?
//		Snippet snippet = dao.getSnippet(snippetID);
//        PreparedStatement ps = conn.prepareStatement("SELECT * FROM SnippetComment" + " WHERE snippetID = ?;");
//        ps.setString(1,  snippet.snippetID);
//        ResultSet resultSet = ps.executeQuery();
//        
//        // Hopefully I imported the correct "Timestamp" (imported the java.sql.Timestamp)
//        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//        
////    	commentID VARCHAR(30) PRIMARY KEY,
////    	timeStamp DATE,
////    	text VARCHAR(1000),
////    	startLine INT,
////    	endLine INT,
////        FOREIGN KEY (snippetID) REFERENCES Snippet(snippetID) ON DELETE CASCADE
//        // TODO: change this to include other columns when db has fuller script (e.g. info, text, etc)
//        ps = conn.prepareStatement("INSERT INTO SnippetComment " + " (commentID, timestamp, text, startLine, endLine, snippetID) values(?, ?, ?, ?, ?, ?);");
//        ps.setString(1,  "blah"); // this is where we need to insert a generated commentID
//        ps.setString(2,  timeStamp);
//        ps.setString(3,  );
//        ps.execute();
//        
//		try {
//			SnippetsDAO dao = new SnippetsDAO();
//			dao.updateSnippet("SnippetComment", );
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//	}
}
