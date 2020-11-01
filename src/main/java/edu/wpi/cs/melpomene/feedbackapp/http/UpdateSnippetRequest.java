package edu.wpi.cs.melpomene.feedbackapp.http;

public class UpdateSnippetRequest {
	public final String snippetID;
	public final String action;
	public final String text;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public UpdateSnippetRequest () {
		this.snippetID = "";
		this.action = "";
		this.text = "";
	}
	
	public UpdateSnippetRequest (String snippetID, String action, String text) {
		this.snippetID = snippetID;
		this.action = action;
		this.text = text;
	}
	
	public String toString() {
		return "UpdateSnippetRequest(" + snippetID + ")";
	}
}
