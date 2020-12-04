package edu.wpi.cs.melpomene.feedbackapp.db;

import java.sql.*;
import java.time.LocalDate;
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
public class SnippetsDAO { 

	java.sql.Connection conn;
	
	final String snippetTable = "Snippet";   // Exact capitalization

    public SnippetsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("error in connecting: " + e.getMessage());
    	}
    }

    public Snippet getSnippet(String snippetID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + snippetTable + " WHERE snippetID=?;");
            ps.setString(1,  snippetID);
            ResultSet resultSet = ps.executeQuery();
            
            Snippet snippet = null;
            while (resultSet.next()) {
                snippet = generateSnippet(resultSet);
            }
            resultSet.close();
            ps.close();
            
            if (snippet == null) {
            	return snippet;
            }
            
            CommentsDAO daoComment = new CommentsDAO();
            ArrayList<Comment> comments = daoComment.getComments(snippetID);
            snippet.comments = comments;
            
            return snippet;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting snippet: " + e.getMessage());
        }
    }
    
    public boolean updateCode(String snippetID, String code, String field) throws Exception {
        try {
        	PreparedStatement ps = conn.prepareStatement("UPDATE " + snippetTable + " SET " + field + " = ? WHERE snippetID = ?;");
        	ps.setString(1,  code);
        	ps.setString(2,  snippetID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update code: " + e.getMessage());
        }
    }
    
    public boolean updateInfo(String snippetID, String info, String field) throws Exception {
        try {
        	PreparedStatement ps = conn.prepareStatement("UPDATE " + snippetTable + " SET " + field + " = ? WHERE snippetID = ?;");
        	ps.setString(1,  info);
        	ps.setString(2,  snippetID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update info: " + e.getMessage());
        }
    }
    
    public boolean updateLanguage(String snippetID, String language, String field) throws Exception {
        try {
        	PreparedStatement ps = conn.prepareStatement("UPDATE " + snippetTable + " SET " + field + " = ? WHERE snippetID = ?;");
        	ps.setString(1,  language);
        	ps.setString(2,  snippetID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update info: " + e.getMessage());
        }
    }
    
    
    public boolean deleteSnippet(String snippetID) throws Exception {
        try {
        	
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + snippetTable + " WHERE snippetID = ?;");
            ps.setString(1,  snippetID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete snippet: " + e.getMessage());
        }
    }
    
    public int deleteStaleSnippets(int nDays) throws Exception {
        try {
        	LocalDate today = LocalDate.now();
        	LocalDate compareDate = today.minusDays(nDays);
        	String staleDate = compareDate.toString();
        	// test this
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + snippetTable + " WHERE snippetTimestamp <= ?;");
            ps.setString(1, staleDate);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return numAffected;

        } catch (Exception e) {
            throw new Exception("Failed to delete snippets: " + e.getMessage());
        }
    }

    public boolean addSnippet(Snippet snippet) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + snippetTable + " WHERE snippetID = ?;");
            ps.setString(1,  snippet.snippetID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                generateSnippet(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + snippetTable + " (snippetID, text, info, codeLanguage, viewerPassword, snippetTimestamp) values(?, '', '', '', ?, ?);");
            ps.setString(1,  snippet.snippetID);
            ps.setString(2,  snippet.viewerPassword);
            ps.setString(3,  snippet.timestamp);
            ps.execute();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to add snippet: " + e.getMessage());
        }
    }

    public ArrayList<Snippet> getAllSnippets() throws Exception {
        try {
        	Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + snippetTable + ";";
            ResultSet resultSet = statement.executeQuery(query);
            
            ArrayList<Snippet> snippets = new ArrayList<Snippet>();
            while (resultSet.next()) {
            	Snippet snippet = generateSnippet(resultSet);
            	snippets.add(snippet);
            }
            resultSet.close();
            statement.close();
            return snippets;

        } catch (Exception e) {
            throw new Exception("Failed in getting snippet: " + e.getMessage());
        }
    }
    
    public ArrayList<String> listAllSnippets() throws Exception {
        try {
        	Statement statement = conn.createStatement();
            String query = "SELECT snippetID, snippetTimestamp FROM " + snippetTable + ";";
            ResultSet resultSet = statement.executeQuery(query);
            
            ArrayList<String> snippets = new ArrayList<String>();
            while (resultSet.next()) {
            	String temp = resultSet.getNString(1) + " " + resultSet.getString(2);
            	snippets.add(temp);
            }
            resultSet.close();
            statement.close();
            return snippets;

        } catch (Exception e) {
            throw new Exception("Failed in listing snippets: " + e.getMessage());
        }
    }
    
    private Snippet generateSnippet(ResultSet resultSet) throws Exception {
    	String snippetID  = resultSet.getString("snippetID");
    	String viewerPassword  = resultSet.getString("viewerPassword");
    	String text = resultSet.getString("text");
    	String info = resultSet.getString("info");
    	String codeLanguage = resultSet.getString("codeLanguage");
    	String snippetTimestamp = resultSet.getString("snippetTimestamp");
    	ArrayList<Comment> comments = new ArrayList<>();
        Snippet snippet = new Snippet(snippetID, viewerPassword, text, info, codeLanguage, snippetTimestamp, comments);
		return snippet;
    }
}