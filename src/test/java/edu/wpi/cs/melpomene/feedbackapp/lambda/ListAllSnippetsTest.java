package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListAllSnippetsTest extends LambdaTest{

    private static UpdateSnippetRequest input;

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }     
    
    
    
    @Test
    public void testDeleteSnippetLambdaPositive() {
    	CreateSnippet csHandler = new CreateSnippet();
    	Context ctx = createContext();
    	CreateSnippetResponse csResponse = csHandler.handleRequest(null, ctx);

        UpdateSnippet handler = new UpdateSnippet();
        input = new UpdateSnippetRequest(csResponse.snippetID, "delete", "", "");
        
        UpdateSnippetResponse response = handler.handleRequest(input, ctx);
     
        Assert.assertEquals(response.snippet.snippetID, csResponse.snippetID);
        Assert.assertEquals(response.error, "Snippet deleted successfully");
        
    }
}
