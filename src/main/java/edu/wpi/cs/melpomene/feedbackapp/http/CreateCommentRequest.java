package edu.wpi.cs.melpomene.feedbackapp.http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class CreateCommentRequest {
	
	public final String snippetID;
	public final int startLine;
	public final int endLine;
	
	/**
	 * Create success response.
	 * @param snippetID
	 */

	public CreateCommentRequest() {
		this.snippetID = "";
		this.startLine = 0;
		this.endLine = 0;
	}
	
	public CreateCommentRequest(String snippetID, int startLine, int endLine) {
		this.snippetID = snippetID;
		this.startLine = startLine;
		this.endLine = endLine;
	}
	
	public String toString() {
		return "CreateCommentRequest(" + snippetID + ")";
	}
}

