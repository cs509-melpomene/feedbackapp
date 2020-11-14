package edu.wpi.cs.melpomene.feedbackapp.model;

import java.util.ArrayList;
import java.util.List;

public class Snippet {

	public final String snippetID;
	public final String creatorPassword;
	public final String viewerPassword;
	public final String text;
	public final String info;
	public final String codingLanguage;
	public final ArrayList<String> commentIDs;
	public final String timestamp;
	
	public Snippet(String snippetID, String creatorPassword, String viewerPassword) {
		this.snippetID = snippetID;
		this.creatorPassword = creatorPassword;
		this.viewerPassword = viewerPassword;
		this.text = "";
		this.info = "";
		this.codingLanguage = "";
		this.commentIDs = new ArrayList<String>();
		this.timestamp = "";	
	}
	
	public Snippet(String snippetID, String creatorPassword, String viewerPassword, String text, String info, String codingLanguage, String timestamp, ArrayList<String> commentIDs) {
		this.snippetID = snippetID;
		this.creatorPassword = creatorPassword;
		this.viewerPassword = viewerPassword;
		this.text = text;
		this.info = info;
		this.codingLanguage = codingLanguage;
		this.commentIDs = commentIDs;
		this.timestamp = timestamp;	
	}
	
	public void addCommentID(String commentID) {
		commentIDs.add(commentID);
	}
}
