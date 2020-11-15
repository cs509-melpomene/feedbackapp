package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class CreateSnippet implements RequestHandler<Object, CreateSnippetResponse> {

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
    public String createUniqueID() throws Exception {
    	SnippetsDAO dao = new SnippetsDAO();
    	for(int i = 0; i < MAX_UNIQUE_ID_TRIES; i++) {
			String newSnippetID = createID();
	        if (dao.getSnippet(newSnippetID) == null) {
	        	return newSnippetID;
	        }
    	}
    	throw new Exception("A unique ID could not be constructed within " + MAX_UNIQUE_ID_TRIES + " tries!");
    }

    @Override
    public CreateSnippetResponse handleRequest(Object input, Context context) {
        CreateSnippetResponse response = null;
        try {
           String snippetID = createUniqueID();
           String creatorPassword = createID();
           String viewerPassword = createID();
           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           Snippet snippet = new Snippet(snippetID, creatorPassword, viewerPassword, timestamp.toString());
           SnippetsDAO dao = new SnippetsDAO();
           Snippet snippetFromDB = dao.getSnippet(snippetID);
           if (snippetFromDB == null) {
        	   dao.addSnippet(snippet);
        	   response = new CreateSnippetResponse(snippetID, creatorPassword, viewerPassword);
        	   context.getLogger().log(String.format("CreateSnippetResponse Success: snippetID: %s, creatorPassword: %s, viewerPassword: %s\n", response.getSnippetID(), response.getCreatorPassword(), response.getViewerPassword()));
           }
       } catch (Exception e) {
	       context.getLogger().log(String.format("CreateSnippetResponse Failure: %s", e.getMessage()));
	       response = new CreateSnippetResponse(e.getMessage());
       }

       return response;
   }
}
