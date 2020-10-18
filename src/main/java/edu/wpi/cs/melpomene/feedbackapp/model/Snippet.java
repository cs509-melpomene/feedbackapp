package edu.wpi.cs.melpomene.feedbackapp.model;

import java.util.ArrayList;

public class Snippet {

	public final String snippetID;
	public final String text;
	public final String info;
	public final String viewerPassword;
	public final String codingLanguage;
	public final ArrayList<String> commentIDs;
	public final String timestamp;
	
	public Snippet(String snippetID, String text, String info, String viewerPassword, String codingLanguage, ArrayList<String> commentIDs, String timestamp) {
		this.snippetID = snippetID;
		this.text = text;
		this.info = info;
		this.viewerPassword = viewerPassword;
		this.codingLanguage = codingLanguage;
		this.commentIDs = commentIDs;
		this.timestamp = timestamp;	
	}
	
	public void addCommentID(String commentID) {
		commentIDs.add(commentID);
	}
}
