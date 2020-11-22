package edu.wpi.cs.melpomene.feedbackapp.http;

public class AdminRequest {

	public final String snippetID;
	public final String commentID;
	public final String action;
	public final String text;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public AdminRequest () {
		this.snippetID = "";
		this.commentID = "";
		this.action = "";
		this.text = "";
	}
	
	public AdminRequest (String snippetID, String commentID, String action, String text) {
		this.snippetID = snippetID;
		this.commentID = commentID;
		this.action = action;
		this.text = text;
	}
	
	public String toString() {
		return "UpdateCommentRequest(" + commentID + ")";
	}
}
