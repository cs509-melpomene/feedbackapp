package edu.wpi.cs.melpomene.feedbackapp.http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class CreateCommentResponse {
	
	public final String snippetID;
	public final String commentID;
	public final int startLine;
	public final int endLine;
	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippetID
	 * @param commentID
	 * @param startLine
	 * @param endLine
	 */
	public CreateCommentResponse (String snippetID,  String commentID, int startLine, int endLine) {
		this.snippetID = snippetID;
		this.commentID = commentID;
		this.startLine = startLine;
		this.endLine = endLine;
		this.error = "";
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public CreateCommentResponse (String error) {
		this.snippetID = "";
		this.commentID = "";
		this.startLine = -1;
		this.endLine = -1;
		this.error = error;
		this.httpCode = 500;
	}
	
	public String getSnippetID() {
		return snippetID;
	}
	
	public String getCommentID() {
		return commentID;
	}
	
	public int getStartLine() {
		return startLine;
	}
	
	public int getEndLine() {
		return endLine;
	}
	
	public String toString() {
		return "Response(" + commentID + ")";
	}
}
