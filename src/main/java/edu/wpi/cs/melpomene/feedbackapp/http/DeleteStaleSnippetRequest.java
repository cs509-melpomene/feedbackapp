package edu.wpi.cs.melpomene.feedbackapp.http;

public class DeleteStaleSnippetRequest {

	public final long nDays;
	public final String action;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public DeleteStaleSnippetRequest () {
		
		this.nDays=0;
		this.action = "";

	}
	
	public DeleteStaleSnippetRequest (long nDays, String action) {
		this.nDays=nDays;
		this.action = action;
	}
	
	public String toString() {
		return "DeleteStaleSnippetRequest(" + action + ")"; //HALP
	}
}
