package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.db.DatabaseUtil;
import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

public class ViewSnippet implements RequestHandler<ViewSnippetRequest, ViewSnippetResponse> {

    @Override
    public ViewSnippetResponse handleRequest(ViewSnippetRequest input, Context context) {
    	context.getLogger().log(String.format("ViewSnippetRequest: snippetID: %s\n", input.snippetID));
    	ViewSnippetResponse response = null;
    	try {
        	if((input.snippetID.length() != 16)) {
        		throw new Exception("not 16 characters"); 
        	}
        	for(int i=0;i<input.snippetID.length();i++) {
        		char c = input.snippetID.charAt(i);
        		if((c < '0' || c > '9' )&&( c < 'a' || c > 'f' )) {
        			throw new Exception("Not Hexadecimal" + i + "  " + c);
        		}
        		
        	}
    		SnippetsDAO dao = new SnippetsDAO();
    		// do null check on the snippet
    		Snippet snippet = dao.getSnippet(input.snippetID);
    		if(snippet == null) {
    			throw new Exception("Snippet not found in database " + 404);
    		}
    		
            response = new ViewSnippetResponse(snippet);
        } catch (Exception e) {
        	// TODO: failure if database connection does not work (500)
        	// TODO: failure if database does not have snippet (404)
	        context.getLogger().log(String.format("ViewSnippetResponse Failure: %s", e.getMessage()));
	        response = new ViewSnippetResponse(e.getMessage(), 400);
        }

        return response;
    }
}
