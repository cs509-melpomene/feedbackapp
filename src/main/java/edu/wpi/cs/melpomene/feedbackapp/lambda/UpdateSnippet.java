package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
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
			dao.updateSnippet(snippetID, code, "text");
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void updateInfo(String snippetID, String code) {
		try {
			SnippetsDAO dao = new SnippetsDAO();
			dao.updateSnippet(snippetID, code, "info");
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public UpdateSnippetResponse handleRequest(UpdateSnippetRequest input, Context context) {
		updateCode(input.snippetID, input.text);
		return null;
	}
}
