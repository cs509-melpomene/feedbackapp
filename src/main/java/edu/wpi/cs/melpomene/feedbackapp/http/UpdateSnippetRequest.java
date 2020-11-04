package edu.wpi.cs.melpomene.feedbackapp.http;

public class UpdateSnippetRequest {
	public final String snippetID;
	public final String action;
	public final String text;
	public final String info;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public UpdateSnippetRequest () {
		this.snippetID = "";
		this.action = "";
		this.text = "";
		this.info = "";
	}
	
	public UpdateSnippetRequest (String snippetID, String action, String text, String info) {
		this.snippetID = snippetID;
		this.action = action;
		this.text = text;
		this.info = info;
	}
	
	public String toString() {
		return "UpdateSnippetRequest(" + snippetID + ")";
	}
}
