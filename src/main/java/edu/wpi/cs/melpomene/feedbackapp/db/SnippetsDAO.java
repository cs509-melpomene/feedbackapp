package edu.wpi.cs.melpomene.feedbackapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
	
	final String tblName = "MyGuests";   // Exact capitalization

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
            
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE snippetID=?;");
            ps.setString(1,  snippetID);
            ResultSet resultSet = ps.executeQuery();
            
            Snippet snippet = null;
            while (resultSet.next()) {
                snippet = generateSnippet(resultSet);
            }
            resultSet.close();
            ps.close();
            return snippet;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting snippet: " + e.getMessage());
        }
    }
    
    public boolean updateConstant() throws Exception {
    	// TODO: need to change from constant to snippet
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE name=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deleteSnippet(String snippetID) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE snippetID = ?;");
            ps.setString(1,  snippetID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete constant: " + e.getMessage());
        }
    }


    public boolean addSnippet(Snippet snippet) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE snippetID = ?;");
            ps.setString(1,  snippet.snippetID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                generateSnippet(resultSet);
                resultSet.close();
                return false;
            }

            // TODO: change this to include other columns when db has fuller script (e.g. info, text, etc)
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (snippetID, creatorPassword, viewerPassword) values(?, ?, ?);");
            ps.setString(1,  snippet.snippetID);
            ps.setString(2,  snippet.creatorPassword);
            ps.setString(3,  snippet.viewerPassword);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add snippet: " + e.getMessage());
        }
    }

    public ArrayList<Snippet> getAllSnippets() throws Exception {
        
        try {
        	Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
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
            throw new Exception("Failed in getting constants: " + e.getMessage());
        }
    }
    
    private Snippet generateSnippet(ResultSet resultSet) throws Exception {
    	// TODO: return all snippet information
    	String snippetID  = resultSet.getString("snippetID");
    	String creatorPassword  = resultSet.getString("creatorPassword");
    	String viewerPassword  = resultSet.getString("viewerPassword");
        Snippet snippet = new Snippet(snippetID, creatorPassword, viewerPassword);
		return snippet;
    }

}