package edu.wpi.cs.melpomene.feedbackapp.http;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class DeleteStaleSnippetsResponse {
	public final String error;
	public final int httpCode;
	public final int numDeleted;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public DeleteStaleSnippetsResponse () {
		this.error = "";
		this.httpCode = 200;
		this.numDeleted = 0;
	}
	
	public DeleteStaleSnippetsResponse (String error, int code, int numDeleted) {
		this.error = error;
		this.httpCode = code;
		this.numDeleted = numDeleted;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public DeleteStaleSnippetsResponse (String error, int code) {
		this.error = error;
		this.httpCode = code;
		this.numDeleted = 0;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return "Response(" + httpCode + ")";
	}
}

