package edu.wpi.cs.melpomene.feedbackapp.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;

public class DatabaseUtilHelper {

	public static String readFile() {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader;
		try {
			String currentDir = System.getProperty("user.dir");
			String filePath = currentDir + "\\db\\snippetTables.sql";
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void createTable() throws Exception {
		PreparedStatement ps;
		try {
			Connection conn = DatabaseUtil.connect();
			
			ps = conn.prepareStatement(readFile());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new Exception("createTable: " + e.getMessage());
		}
        ps.execute();
	}
	
	public static void dropTable() throws Exception {
		PreparedStatement ps;
		try {
			Connection conn = DatabaseUtil.connect();
			ps = conn.prepareStatement("DROP TABLE Snippet");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new Exception("createTable: " + e.getMessage());
		}
        ps.execute();
        
//        try {
//			Connection conn = DatabaseUtil.connect();
//			ps = conn.prepareStatement("DROP TABLE Snippet");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			throw new Exception("createTable: " + e.getMessage());
//		}
//        ps.execute();
	}

}
