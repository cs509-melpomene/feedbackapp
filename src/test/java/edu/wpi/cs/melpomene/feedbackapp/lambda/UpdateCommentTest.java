package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateCommentRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateCommentResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateCommentRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateCommentResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Comment;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateCommentTest extends LambdaTest{

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }     

    @Test
    public void testUpdateTextLambdaPositive() {
    	// create snippet
    	CreateSnippet csHandler = new CreateSnippet();
    	Context ctx = createContext();
    	CreateSnippetResponse csResponse = csHandler.handleRequest(null, ctx);
    	
    	CreateCommentRequest ccRequest = new CreateCommentRequest(csResponse.snippetID, 3, 5);
    	CreateComment ccHandler = new CreateComment();
    	CreateCommentResponse ccResponse = ccHandler.handleRequest(ccRequest, ctx);
    	
        UpdateComment handler = new UpdateComment();
        UpdateCommentRequest input = new UpdateCommentRequest(csResponse.snippetID, ccResponse.commentID, "update", "whatever");
        
        UpdateCommentResponse response = handler.handleRequest(input, ctx);
        
        ViewSnippet snippet = new ViewSnippet();
        ViewSnippetRequest request = new ViewSnippetRequest(ccResponse.snippetID);
        ViewSnippetResponse viewResponse = snippet.handleRequest(request, ctx);
        
        System.out.println("====================\n" + response.comment.commentID);
        System.out.println("====================\n" + ccResponse.commentID);   
        System.out.println("====================\n" + viewResponse.snippet.comments);  
        
        boolean isFound = false;
        for(Comment comment : viewResponse.snippet.comments) {
        	if(comment.commentID.equals(response.comment.commentID)) {
        		isFound = true;
        		Assert.assertEquals(comment.text, "whatever");
        		break;
        	}
        }
        
        Assert.assertTrue(isFound);
//        Assert.assertEquals(viewResponse.snippet.text, "whatever");
//        Assert.assertEquals(viewResponse.snippet.snippetID, csResponse.snippetID);
        
    }
    
    @Test
    public void testDeleteCommentLambdaPositive() {
    	//create snippet
    	CreateSnippet csHandler = new CreateSnippet();
    	Context ctx = createContext();
    	CreateSnippetResponse csResponse = csHandler.handleRequest(null, ctx);
    	
    	CreateCommentRequest ccRequest = new CreateCommentRequest(csResponse.snippetID, 3, 5);
    	CreateComment ccHandler = new CreateComment();
    	CreateCommentResponse ccResponse = ccHandler.handleRequest(ccRequest, ctx);

        UpdateComment handler = new UpdateComment();
        UpdateCommentRequest input = new UpdateCommentRequest(ccResponse.snippetID, ccResponse.commentID, "delete", "");
        
        UpdateCommentResponse response = handler.handleRequest(input, ctx);
        
//        System.out.println("====================\n" + response.comment.commentID);
//        System.out.println("====================\n" + ccResponse.commentID);        
        
        Assert.assertNull(response.comment);
        Assert.assertEquals(response.error, "Comment deleted successfully");
        
    }
}
