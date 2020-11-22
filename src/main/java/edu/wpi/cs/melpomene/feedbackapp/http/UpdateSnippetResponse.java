package edu.wpi.cs.melpomene.feedbackapp.http;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class UpdateSnippetResponse {
	public final Snippet snippet;
	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public UpdateSnippetResponse (Snippet snippet) {
		this.snippet = snippet;
		this.error = "";
		this.httpCode = 200;
	}
	
	public UpdateSnippetResponse (Snippet snippet, String error) {
		this.snippet = snippet;
		this.error = error;
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public UpdateSnippetResponse (String error, int code) {
		this.snippet = null;
		this.error = error;
		this.httpCode = code;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return "Response(" + snippet + ")";
	}
}
