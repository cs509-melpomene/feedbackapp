package edu.wpi.cs.melpomene.feedbackapp.http;

import java.util.ArrayList;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class ViewSnippetResponse {
	
	public final Snippet snippet;
	
	public final String error;
	public final int httpCode;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public ViewSnippetResponse (Snippet snippet) {
		this.snippet = snippet;
		this.error = "";
		this.httpCode = 200;
	}
	
	/**
	 * Create failure response.
	 * @param error
	 */
	public ViewSnippetResponse (String error, int code) {
		this.snippet = null;
		this.error = error;
		this.httpCode = code;
	}
	
	public Snippet getSnippet() {
		return snippet;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return "Response(" + snippet + ")";
	}
}
