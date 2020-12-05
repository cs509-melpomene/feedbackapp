package edu.wpi.cs.melpomene.feedbackapp.http;

public class ListAllSnippetsRequest {


	public final String action;
	
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public ListAllSnippetsRequest () {
		
		this.action = "";
		
	}
	
	public ListAllSnippetsRequest ( String action) {
		
		this.action = action;
		
	}
	
	public String toString() {
		return "ListAllSnippetsRequest(" + action + ")";
	}
}
