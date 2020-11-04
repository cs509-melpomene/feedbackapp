package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class UpdateSnippet implements RequestHandler<UpdateSnippetRequest, UpdateSnippetResponse> {

	java.sql.Connection conn;
	SnippetsDAO dao = new SnippetsDAO();
	
	public void updateCode(String snippetID, String code) {
		try {
			dao.updateCode(snippetID, code, "text");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateInfo(String snippetID, String info) {
		try {
			dao.updateInfo(snippetID, info, "info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSnippet(String snippetID) {
		try {
			dao.deleteSnippet(snippetID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UpdateSnippetResponse handleRequest(UpdateSnippetRequest input, Context context) {
		UpdateSnippetResponse response = null;
		if(input.action == "update") {
			if(input.text != null) {
				updateCode(input.snippetID, input.text);
				try {
					response = new UpdateSnippetResponse(dao.getSnippet(input.snippetID));
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			else if(input.info != null) {
				updateInfo(input.snippetID, input.text);
			}
		}
		// if the action is "delete"
		else {
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
