package edu.wpi.cs.melpomene.feedbackapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 * 
 * @author heineman
 *
 */
public class ConstantsDAO { 

	java.sql.Connection conn;
	
	final String tblName = "Constants";   // Exact capitalization

    public ConstantsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("error in connecting: " + e.getMessage());
    	}
    }

    public void getConstant(String name) throws Exception {
        
        try {
            
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                generateConstant(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting constant: " + e.getMessage());
        }
    }
    
    public boolean updateConstant() throws Exception {
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
    
    public boolean deleteConstant() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name = ?;");
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }


    public boolean addConstant() throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                generateConstant(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }

    public void getAllConstants() throws Exception {
        
        try {
        	System.out.println("here6");
            Statement statement = conn.createStatement();
            System.out.println("here5");
            String query = "SELECT * FROM " + tblName + ";";
            System.out.println("here2");
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("here1");
            while (resultSet.next()) {
                generateConstant(resultSet);
            }
            System.out.println("here4");
            resultSet.close();
            statement.close();

        } catch (Exception e) {
            throw new Exception("Failed in getting constants: " + e.getMessage());
        }
    }
    
    private void generateConstant(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("name");
        Double value = resultSet.getDouble("value");
        System.out.println("name: " + name + ", value: " + value);
    }

}