package edu.wpi.cs.melpomene.feedbackapp.http;

import java.util.ArrayList;

public class ListAllSnippetsResponse {
	public final String error;
	public final int httpCode;
	public final ArrayList<String> snippets;
	/**
	 * Create success response.
	 * @param snippet
	 */
	public ListAllSnippetsResponse () {
		this.snippets = null;
		this.error = "";
		this.httpCode = 200;
	}
	
	public ListAllSnippetsResponse ( String error, ArrayList<String> snippets ) {
		this.snippets = snippets;
		this.error = error;
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public ListAllSnippetsResponse(String error, int code) {
		this.snippets = null;
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
