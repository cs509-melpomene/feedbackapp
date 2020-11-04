package edu.wpi.cs.melpomene.feedbackapp.http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class CreateCommentRequest {
	
	public final String snippetID;
	
	/**
	 * Create success response.
	 * @param snippetID
	 */

	public CreateCommentRequest() {
		this.snippetID = "";
	}
	
	public CreateCommentRequest(String snippetID) {
		this.snippetID = snippetID;
	}
	
	public String toString() {
		return "CreateCommentRequest(" + snippetID + ")";
	}
}

