package edu.wpi.cs.melpomene.feedbackapp.http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class CreateSnippetResponse {
	
	public final String snippetID;
	public final String viewerPassword;
	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippetID
	 * @param creatorPassword
	 * @param viewerPassword
	 */
	public CreateSnippetResponse (String snippetID, String viewerPassword) {
		this.snippetID = snippetID;
		this.viewerPassword = viewerPassword;
		this.error = "";
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public CreateSnippetResponse (String error) {
		this.snippetID = "";
		this.viewerPassword = "";
		this.error = error;
		this.httpCode = 500;
	}
	
	public String getSnippetID() {
		return snippetID;
	}
	
	public String getViewerPassword() {
		return viewerPassword;
	}
	
	public String toString() {
		return "Response(" + snippetID + ")";
	}
}
