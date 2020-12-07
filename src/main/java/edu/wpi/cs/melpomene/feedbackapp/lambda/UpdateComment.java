package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

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
	
	public void updateText(String snippetID, String commentID, String text) {
		try {
			dao.updateText(snippetID, commentID, text);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Comment> deleteComment(String snippetID, String commentID) {
		try {
			return dao.deleteComment(snippetID, commentID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public UpdateCommentResponse handleRequest(UpdateCommentRequest input, Context context) {
		UpdateCommentResponse response = null;
		if(input.action.equals("update")) {
			updateText(input.snippetID, input.commentID, input.text);
			try {
				response = new UpdateCommentResponse(dao.getComment(input.commentID, input.snippetID));
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		
		if(input.action.equals("delete")) {
			Comment comment = null;
			try {
				comment = dao.getComment(input.commentID, input.snippetID);
			} catch (Exception e) {
				e.printStackTrace();
				return new UpdateCommentResponse("Comment not found", 400); 
			}

			ArrayList<Comment> remainingComments = deleteComment(input.snippetID, input.commentID);
			
			try {
				response = new UpdateCommentResponse("Comment deleted successfully", remainingComments);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}

		return response;
	}
}
