package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.Connection;
import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.db.CommentsDAO;
import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateCommentRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateCommentResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Comment;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class CreateComment implements RequestHandler<CreateCommentRequest, CreateCommentResponse> {

    static final int ID_LENGTH = 16;
    static final int HEX_MAX_DECIMAL = 16;
    static final int MAX_UNIQUE_ID_TRIES = 10;

    /**
     * Creates a hexadecimal id of length 16 that may or may not be unique.
     * @return
     */
    public String createID() {
       StringBuilder sb = new StringBuilder();
       Random r = new Random();
       for(int i = 0; i < ID_LENGTH; i++) {
	       int randomInt = Math.abs(r.nextInt());
	       int hexDecimal = randomInt % HEX_MAX_DECIMAL;
	       String hexChar = String.format("%x", hexDecimal);
	       sb.append(hexChar);
       }
       return sb.toString();
    }

    /**
     * Creates a unique hexadecimal id of length 16.
     * @return
     * @throws Exception if a unique id could not be constructed within 10 tries
     */
    public String createUniqueID(String snippetID) throws Exception {
    	CommentsDAO dao = new CommentsDAO();
    	for(int i = 0; i < MAX_UNIQUE_ID_TRIES; i++) {
			String newCommentID = createID();
	        if (dao.getComment(newCommentID, snippetID) == null) {
	        	return newCommentID;
	        }
    	}
    	throw new Exception("A unique ID could not be constructed within " + MAX_UNIQUE_ID_TRIES + " tries!");
    }

    @Override
    public CreateCommentResponse handleRequest(CreateCommentRequest input, Context context) {
        CreateCommentResponse response = null;
        try {
           String commentID = createUniqueID(input.snippetID);
           Comment comment = new Comment(commentID, input.snippetID, input.startLine, input.endLine);
           CommentsDAO dao = new CommentsDAO();
           Comment commentFromDB = dao.getComment(commentID, input.snippetID);
           if (commentFromDB == null) {
        	   dao.addComment(comment);
        	   response = new CreateCommentResponse(commentID);
        	   context.getLogger().log(String.format("CreateCommentResponse Success: snippetID: %s, commentID: %s, startLine: %i, endLine: %i\n", response.getSnippetID(), response.getCommentID(), response.getStartLine(), response.getEndLine()));
           }
       } catch (Exception e) {
	       context.getLogger().log(String.format("CreateCommentResponse Failure: %s", e.getMessage()));
	       response = new CreateCommentResponse(e.getMessage());
       }

       return response;
   }
}
