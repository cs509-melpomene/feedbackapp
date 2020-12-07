package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.DeleteStaleSnippetsRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.DeleteStaleSnippetsResponse;

public class DeleteStaleSnippets implements RequestHandler<DeleteStaleSnippetsRequest, DeleteStaleSnippetsResponse> {
	
	java.sql.Connection conn;
	SnippetsDAO dao = new SnippetsDAO();
	
	public int deleteStaleSnippets(int nDays) {
		int numDeleted = 0;
		try {
			numDeleted = dao.deleteStaleSnippets(nDays);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numDeleted;
	}

	@Override
	public DeleteStaleSnippetsResponse handleRequest(DeleteStaleSnippetsRequest input, Context context) {
		DeleteStaleSnippetsResponse response = null;
		
		//Admin wants to delete all stale snippets that are n days old
		if(input.action.contentEquals("deleteStale")) {
			int nPlus = input.nDays + 1;
			int numDeleted = deleteStaleSnippets(nPlus);
			try {
				response = new DeleteStaleSnippetsResponse("Snippets deleted successfully", 200, numDeleted);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return response;
	}
}