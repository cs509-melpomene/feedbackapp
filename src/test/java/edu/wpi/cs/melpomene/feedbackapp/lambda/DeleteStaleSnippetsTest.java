package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.DeleteStaleSnippetsRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.DeleteStaleSnippetsResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteStaleSnippetsTest extends LambdaTest{

    private static DeleteStaleSnippetsRequest input;

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }     
   
    // How to create a test for n days old when you're creating a test in the moment?
    // should I set nDays = 0?
    @Test
    public void testDeleteStaleSnippetsLambdaPositive() {
    	CreateSnippet csHandler = new CreateSnippet();
    	Context ctx = createContext();
    	CreateSnippetResponse csResponse = csHandler.handleRequest(null, ctx);

    	DeleteStaleSnippets handler = new DeleteStaleSnippets();
        input = new DeleteStaleSnippetsRequest("deleteStale", 1);
        
        DeleteStaleSnippetsResponse response = handler.handleRequest(input, ctx);
     
        Assert.assertEquals(1, response.numDeleted);
        Assert.assertEquals(response.error, "Snippets deleted successfully");
        
    }
}
