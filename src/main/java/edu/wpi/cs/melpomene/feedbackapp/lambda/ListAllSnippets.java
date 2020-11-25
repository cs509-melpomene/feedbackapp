package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.SQLException;
import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.ListAllSnippetsRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ListAllSnippetsResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class ListAllSnippets implements RequestHandler<ListAllSnippetsRequest, ListAllSnippetsResponse> {
	
	java.sql.Connection conn;
	SnippetsDAO dao = new SnippetsDAO();
	
	public ArrayList<String> listAllSnippets() {
		ArrayList<String> snippets = new ArrayList<>();
		try {
			snippets = dao.listAllSnippets();
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
		ArrayList<String> snippets = new ArrayList<>();
		// Admin wants to view the list of available snippets and their timestamps
		if(input.action.equals("list")) {
			snippets = listAllSnippets();
			try {
				response = new ListAllSnippetsResponse(snippets, "ListAllSnippets was successful", 200);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		
		return response;
	}
}