package edu.wpi.cs.melpomene.feedbackapp.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class DatabaseUtil {

	// DB user names and passwords (as well as the db endpoint) should never be stored directly in code.
	//
	// https://docs.aws.amazon.com/lambda/latest/dg/env_variables.html
	//
	// The above link shows how to ensure Lambda function has access to environment as well as local
	public final static String jdbcTag = "jdbc:mysql://";
	public final static String rdsMySqlDatabasePort = "3306";
	public final static String multiQueries = "?allowMultiQueries=true";
	   
	// Make sure matches Schema created from MySQL WorkBench
	// Make sysEnv variable lambdaTesting so we know we are locally testing
	public final static String lambdaTesting = "lambdaTesting";
	public final static String dbName = "sys";
	public final static String testName = "test";
	
	// pooled across all usages.
	static Connection conn;
 
	/**
	 * Singleton access to DB connection to share resources effectively across multiple accesses.
	 */
	protected static Connection connect() throws Exception {
		if (conn != null) { return conn; }
		
		// this is resistant to any SQL-injection attack.
		String schemaName = dbName;
		String test = System.getenv("lambdaTesting");
		if (test != null) {
			schemaName = testName;
		}
		
		// These three environment variables must be set!
		String dbUsername = System.getenv("dbUsername");
		if (dbUsername == null) {
			System.err.println("Environment variable dbUsername is not set!");
		}
		String dbPassword = System.getenv("dbPassword");
		if (dbPassword == null) {
			System.err.println("Environment variable dbPassword is not set!");
		}
		String rdsMySqlDatabaseUrl = System.getenv("rdsMySqlDatabaseUrl");
		if (rdsMySqlDatabaseUrl == null) {
			System.err.println("Environment variable rdsMySqlDatabaseUrl is not set!");
		}
		
		try {
			//System.out.println("start connecting......");
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + schemaName + multiQueries,
					dbUsername,
					dbPassword);
			return conn;
		} catch (Exception ex) {
			System.err.println("DB-ERROR:" + schemaName + "," + dbUsername + "," + dbPassword + "," + rdsMySqlDatabaseUrl);
			throw new Exception("Failed in database connection");
		}
	}
	
	public static String readFile() {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"C:\\Users\\YahelNachum\\eclipse-workspace\\feedbackapp\\db\\createTable.sql"));
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
			Connection conn = connect();
			
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
			Connection conn = connect();
			ps = conn.prepareStatement("DROP TABLE MyGuests");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new Exception("createTable: " + e.getMessage());
		}
        ps.execute();
	}
	
	
	public static void createSnippet(Snippet snippet) throws Exception {
		PreparedStatement ps;
		try {
			Connection conn = connect();
			ps = conn.prepareStatement(
					"INSERT INTO MyGuests" +
			            "(snippetID, creatorPassword, viewerPassword) VALUES" +
					    "(\"" + snippet.snippetID + "\",\"" +
			                  snippet.creatorPassword + "\",\"" +
					          snippet.viewerPassword + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new Exception("createSnippet: " + e.getMessage());
		}
        ps.execute();
	}
}
