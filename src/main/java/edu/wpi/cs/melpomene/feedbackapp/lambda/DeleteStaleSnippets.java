package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.DeleteStaleSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.DeleteStaleSnippetResponse;


public class DeleteStaleSnippets implements RequestHandler<DeleteStaleSnippetRequest,DeleteStaleSnippetResponse>{

	
	java.sql.Connection conn;
	SnippetsDAO dao = new SnippetsDAO();
	
 
	
	public void deleteStaleSnippets(long nDays) {
		try {
			dao.deleteStaleSnippets(nDays);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DeleteStaleSnippetResponse handleRequest(DeleteStaleSnippetRequest input, Context context) {
		DeleteStaleSnippetResponse response = null;
		//DeleteStaleSnippets 
		
		if(input.action.equals("deleteStale")) {
			
			

			deleteStaleSnippets(input.nDays);
			
			try {
				response = new DeleteStaleSnippetResponse("Snippet deleted successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return response;
	}
}


