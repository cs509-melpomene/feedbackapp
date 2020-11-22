package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
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

    private static UpdateCommentRequest input;

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }     

    @Test
    public void testUpdateTextLambdaPositive() {
    	CreateComment csHandler = new CreateComment();
    	Context ctx = createContext();
    	CreateCommentResponse csResponse = csHandler.handleRequest(null, ctx);

        UpdateComment handler = new UpdateComment();
        input = new UpdateCommentRequest(csResponse.snippetID, csResponse.commentID, "update", "whatever");
        
        UpdateCommentResponse response = handler.handleRequest(input, ctx);
        
        ViewSnippet snippet = new ViewSnippet();
        ViewSnippetRequest request = new ViewSnippetRequest(csResponse.snippetID);
        ViewSnippetResponse viewResponse = snippet.handleRequest(request, ctx);
        
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
    	CreateComment csHandler = new CreateComment();
    	Context ctx = createContext();
    	CreateCommentResponse csResponse = csHandler.handleRequest(null, ctx);

        UpdateComment handler = new UpdateComment();
        input = new UpdateCommentRequest(csResponse.snippetID, csResponse.commentID, "delete", "");
        
        UpdateCommentResponse response = handler.handleRequest(input, ctx);
        
        Assert.assertEquals(response.comment.commentID, csResponse.commentID);
        Assert.assertEquals(response.error, "Comment deleted successfully");
        
    }
}
