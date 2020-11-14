package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.CommentsDAO;
import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateCommentRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateCommentResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Comment;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class UpdateComment implements RequestHandler<UpdateCommentRequest, UpdateCommentResponse> {

	java.sql.Connection conn;
	CommentsDAO dao = new CommentsDAO();
	
	public void updateText(String snippetID, String text) {
		try {
			dao.updateText(snippetID, text, "text");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComment(String snippetID, String commentID) {
		try {
			dao.deleteComment(snippetID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UpdateCommentResponse handleRequest(UpdateCommentRequest input, Context context) {
		UpdateCommentResponse response = null;
		if(input.action == "update") {
			updateText(input.snippetID, input.text);
			try {
				response = new UpdateCommentResponse(dao.getComment(input.snippetID, input.commentID));
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		// if the action is "delete"
		else {
			Comment comment = null;
			try {
				comment = dao.getComment(input.snippetID, input.commentID);
			} catch (Exception e) {
				e.printStackTrace();
			}

			deleteComment(input.snippetID, input.commentID);
			
			try {
				response = new UpdateCommentResponse(comment, "Comment deleted successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}

		return response;
	}
}
