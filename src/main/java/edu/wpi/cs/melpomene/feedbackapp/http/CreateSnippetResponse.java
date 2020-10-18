package edu.wpi.cs.melpomene.feedbackapp.http;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class CreateSnippetResponse {
	public final String response;
	public final int httpCode;
	
	// 200 means success
	public CreateSnippetResponse (String id) {
		this.response = id;
		this.httpCode = 200;
	}
	
	public CreateSnippetResponse (String errorMessage, int code) {
		this.response = errorMessage;
		this.httpCode = code;
	}
	
	public String getResponse() {
		return response;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
