package edu.wpi.cs.melpomene.feedbackapp.http;

public class DeleteStaleSnippetsRequest {

	public final String action;
	public final int nDays;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public DeleteStaleSnippetsRequest () {
		this.action = "";
		this.nDays = -1;
	}
	
	// For listing all snippets
	public DeleteStaleSnippetsRequest(String action) {
		this.action = action;
		this.nDays = -1;
	}
	
	// For deleting stale snippets
	public DeleteStaleSnippetsRequest (String action, int nDays) {
		this.action = action;
		this.nDays = nDays;
	}
	
	public String toString() {
		return "AdminRequest(" + action + ")";
	}
}
