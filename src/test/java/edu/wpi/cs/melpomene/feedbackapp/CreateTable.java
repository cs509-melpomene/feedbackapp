package edu.wpi.cs.melpomene.feedbackapp;

import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;

public class CreateTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DatabaseUtil.createTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
