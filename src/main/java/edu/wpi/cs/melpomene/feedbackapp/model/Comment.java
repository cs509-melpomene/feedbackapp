package edu.wpi.cs.melpomene.feedbackapp.model;

import java.util.ArrayList;
import java.util.List;

public class Comment {

	public final String commentID;
	public final String timestamp;
	public final String text;
	public final int startLine;
	public final int endLine;
	public final String snippetID;
	
	public Comment(String commentID, String snippetID, int startLine, int endLine, String timestamp) {
		this.commentID = commentID;
		this.snippetID = snippetID;
		this.text = "";
		this.timestamp = timestamp;	
		this.startLine = startLine;
		this.endLine = endLine;
	}
	
	public Comment(String snippetID, String commentID, int startLine, int endLine, String text, String timestamp) {
		this.commentID = commentID;
		this.snippetID = snippetID;
		this.text = text;
		this.timestamp = timestamp;	
		this.startLine = startLine;
		this.endLine = endLine;
	}
	
}
