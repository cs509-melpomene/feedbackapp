package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class Admin {
	
	java.sql.Connection conn;
	SnippetsDAO dao = new SnippetsDAO();
	
	public void showAllSnippets() {
		try {
			dao.getAllSnippets();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSnippet(String snippetID) {
		try {
			dao.deleteSnippet(snippetID);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteNSnippets(int n) {
		try {
			dao.deleteSnippet(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public AdminResponse handleRequest(AdminRequest input, Context context) {
		UpdateSnippetResponse response = null;
		if(input.action.equals("update")) {
			if(input.text != null) {
				updateCode(input.snippetID, input.text);
				try {
					response = new UpdateSnippetResponse(dao.getSnippet(input.snippetID));
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			else if(input.info != null) {
				updateInfo(input.snippetID, input.info);
			}
		}
		
		if(input.action.equals("delete")) {
			Snippet snippet = null;
			try {
				snippet = dao.getSnippet(input.snippetID);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			deleteSnippet(input.snippetID);
			
			try {
				response = new UpdateSnippetResponse(snippet, "Snippet deleted successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return response;
	}
}

}
