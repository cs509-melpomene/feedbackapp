package edu.wpi.cs.melpomene.feedbackapp.lambda;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;
import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtilHelper;

public abstract class LambdaTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DatabaseUtilHelper.createTable();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DatabaseUtilHelper.dropTable();
	}
}
