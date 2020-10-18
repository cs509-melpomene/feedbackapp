package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;

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
       for(int i = 0; i < MAX_UNIQUE_ID_TRIES; i++) {
           String newID = createID();
           // TODO: check for uniqueness
           return newID;
       }
       throw new Exception("A unique ID could not be constructed within " + MAX_UNIQUE_ID_TRIES + " tries!");
    }

    @Override
    public CreateSnippetResponse handleRequest(Object input, Context context) {
       /*

        *   ID:
	           description: unique hexadecimal id
	           type: string
	           pattern: "([a-fA-F0-9]){16,16}"
	           example: 123456789abcdef0

        *   CreateSnippetResponse:
	           type: object
	           required:
	           - creatorPassword
	           - viewerPassword
	           - snippetID
	           properties:
	             creatorPassword:
	               type: string
	               example: randomCreatorPassword
	             viewerPassword:
	               type: string
	               example: randomViewerPassword
	             snippetID:
	               $ref: '#/definitions/ID'

        */
        context.getLogger().log("Input Yahel: " + input);

        CreateSnippetResponse response = null;
        try {
           String snippetID = createUniqueID();
           String creatorPassword = createUniqueID();
           String viewerPassword = createUniqueID();
           response = new CreateSnippetResponse(
        		   snippetID,
        		   creatorPassword,
        		   viewerPassword);
       } catch (Exception e) {
	       context.getLogger().log(e.getMessage());
	       response = new CreateSnippetResponse(e.getMessage());
       }

       return response;
   }
}
