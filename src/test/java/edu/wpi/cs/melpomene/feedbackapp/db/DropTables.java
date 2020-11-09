package edu.wpi.cs.melpomene.feedbackapp.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;

public class DropTables {

	public static void main(String[] args) {
		try {
			DatabaseUtilHelper.dropTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
