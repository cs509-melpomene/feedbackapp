package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.SQLException;
import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.ListAllSnippetsRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ListAllSnippetsResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class ListAllSnippets implements RequestHandler<ListAllSnippetsRequest,ListAllSnippetsResponse> {

	
	java.sql.Connection conn;
	SnippetsDAO dao = new SnippetsDAO();
	
	public  ArrayList<String> listAllSnippets() {
		ArrayList<String> snippets=null;
		try {
			snippets =dao.listAllSnippets();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return snippets;
	}
	
	 
	@Override
	public ListAllSnippetsResponse handleRequest(ListAllSnippetsRequest input, Context context) {
		ListAllSnippetsResponse response = null;
		ArrayList<String> snippets=null;
		if(input.action.equals("List")) {
			
			snippets = listAllSnippets();
			
			try {
				
				response = new ListAllSnippetsResponse("ListAllSnippetsResponse was successful",snippets);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
	
		return response;
	}
}


