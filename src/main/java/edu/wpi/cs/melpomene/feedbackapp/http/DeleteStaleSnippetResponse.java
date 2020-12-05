package edu.wpi.cs.melpomene.feedbackapp.http;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class DeleteStaleSnippetResponse {

	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public DeleteStaleSnippetResponse () {
		
		this.error = "";
		this.httpCode = 200;
	}
	
	public DeleteStaleSnippetResponse ( String error) {
		
		this.error = error;
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public DeleteStaleSnippetResponse (String error, int code) {
		
		this.error = error;
		this.httpCode = code;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return "Response(" +  httpCode + ")";
	}
	
}
