package edu.wpi.cs.melpomene.feedbackapp.http;

import java.util.ArrayList;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class ListAllSnippetsResponse {
	public final ArrayList<String> snippets;
	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public ListAllSnippetsResponse () {
		this.snippets = null;
		this.error = "";
		this.httpCode = 200;
	}
	
	public ListAllSnippetsResponse (ArrayList<String> snippets, String error, int code) {
		this.snippets = snippets;
		this.error = error;
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public ListAllSnippetsResponse (String error, int code) {
		this.snippets = null;
		this.error = error;
		this.httpCode = code;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return "Response(" + httpCode + ")";
	}
}

