package edu.wpi.cs.melpomene.feedbackapp.http;

import edu.wpi.cs.melpomene.feedbackapp.model.Comment;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class UpdateCommentResponse {
	public final Comment comment;
	
	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public UpdateCommentResponse (Comment comment) {
		this.comment = comment;
		this.error = "";
		this.httpCode = 200;
	}
	
	public UpdateCommentResponse (Comment comment, String error) {
		this.comment = comment;
		this.error = error;
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public UpdateCommentResponse (String error, int code) {
		this.comment = null;
		this.error = error;
		this.httpCode = code;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return "Response(" + comment + ")";
	}
}
