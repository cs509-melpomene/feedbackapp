package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ListAllSnippetsRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ListAllSnippetsResponse;
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

    private static ListAllSnippetsRequest input;

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }     
    
    @Test
    public void testListAllSnippetsLambdaPositive() {
    	CreateSnippet csHandler = new CreateSnippet();
    	Context ctx = createContext();
    	CreateSnippetResponse csResponse = csHandler.handleRequest(null, ctx);

    	ListAllSnippets handler = new ListAllSnippets();
        input = new ListAllSnippetsRequest("list");
        
        ListAllSnippetsResponse response = handler.handleRequest(input, ctx);
        
        ViewSnippet snippet = new ViewSnippet();
        ViewSnippetRequest request = new ViewSnippetRequest(csResponse.snippetID);
        ViewSnippetResponse viewResponse = snippet.handleRequest(request, ctx);
        
        Assert.assertEquals(viewResponse.snippet.snippetID, csResponse.snippetID);
        Assert.assertEquals(1, response.snippets.size());
        String expectedSnippetStr = viewResponse.snippet.snippetID + " " + viewResponse.snippet.timestamp;
        Assert.assertEquals(expectedSnippetStr, response.snippets.get(0));
    }
    
}
