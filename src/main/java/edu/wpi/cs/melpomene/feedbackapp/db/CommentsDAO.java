package edu.wpi.cs.melpomene.feedbackapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.melpomene.feedbackapp.model.Comment;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class CommentsDAO { 

	java.sql.Connection conn;

	final String commentTable = "SnippetComment";	// Exact capitalization

    public CommentsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("error in connecting: " + e.getMessage());
    	}
    }

    public Comment getComment(String commentID, String snippetID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + commentTable + " WHERE snippetID = ? AND commentID = ?;");
            ps.setString(1,  snippetID);
            ps.setString(2, commentID);
            ResultSet resultSet = ps.executeQuery();
            
            Comment comment = null;
            while (resultSet.next()) {
                comment = generateComment(resultSet);
            }
            resultSet.close();
            ps.close();
            return comment;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting comment: " + e.getMessage());
        }
    }
    
    public boolean updateText(String snippetID, String commentID, String text) throws Exception {
        try {
        	PreparedStatement ps = conn.prepareStatement("UPDATE " + commentTable + " SET text = ? WHERE snippetID = ? AND commentID = ?;");
        	ps.setString(1,  text);
        	ps.setString(2,  snippetID);
        	ps.setString(3,  commentID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update text: " + e.getMessage());
        }
    }
        
    public boolean deleteComment(String snippetID, String commentID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + commentTable + " WHERE snippetID = ? AND commentID = ?;");
            ps.setString(1,  snippetID);
            ps.setString(2,  commentID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete comment: " + e.getMessage());
        }
    }
    
    public boolean addComment(Comment comment) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + commentTable + " WHERE commentID = ?;");
            ps.setString(1,  comment.commentID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                generateComment(resultSet);
                resultSet.close();
                return false;
            }
        	
            ps = conn.prepareStatement("INSERT INTO " + commentTable + " (commentID, timestamp, text, startLine, endLine, snippetID) values(?, '1990-09-01', '', ?, ?, ?);");
            ps.setString(1, comment.commentID);
            ps.setInt(2, comment.startLine);
            ps.setInt(3, comment.endLine);
            ps.setString(4, comment.snippetID);
            ps.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to add comment: " + e.getMessage());
        }
    }

    // not sure we'll need this
    // maybe keep
//    public ArrayList<Snippet> getAllComments() throws Exception {
//        try {
//        	Statement statement = conn.createStatement();
//            String query = "SELECT * FROM " + snippetTable + ";";
//            ResultSet resultSet = statement.executeQuery(query);
//            
//            ArrayList<Snippet> snippets = new ArrayList<Snippet>();
//            while (resultSet.next()) {
//            	Snippet snippet = generateSnippet(resultSet);
//            	snippets.add(snippet);
//            }
//            resultSet.close();
//            statement.close();
//            return snippets;
//
//        } catch (Exception e) {
//            throw new Exception("Failed in getting snippet: " + e.getMessage());
//        }
//    }
    
    private Comment generateComment(ResultSet resultSet) throws Exception {
		String commentID = resultSet.getString("commentID");
    	String snippetID  = resultSet.getString("snippetID");
    	String text = resultSet.getString("text");
    	String commentTimestamp = resultSet.getString("timestamp");
    	int startLine = resultSet.getInt("startLine");
    	int endLine = resultSet.getInt("endLine");
        Comment comment = new Comment(snippetID, commentID, startLine, endLine, text, commentTimestamp);
		return comment;
    }
}