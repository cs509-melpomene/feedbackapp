package edu.wpi.cs.melpomene.feedbackapp.http;

import java.util.ArrayList;

import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class ViewSnippetRequest {
	
	public final String snippetID;
	
	/**
	 * Create success response.
	 * @param snippet
	 */
	public ViewSnippetRequest () {
		this.snippetID = "";
	}
	
	public ViewSnippetRequest (String snippetID) {
		this.snippetID = snippetID;
	}
	
	public String toString() {
		return "ViewSnippetRequest(" + snippetID + ")";
	}
}
