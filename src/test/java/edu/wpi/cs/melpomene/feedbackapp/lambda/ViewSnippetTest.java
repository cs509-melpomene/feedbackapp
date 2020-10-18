package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.ViewSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ViewSnippetTest {

    private static ViewSnippetRequest input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = new ViewSnippetRequest("abcdef");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testViewSnippetLambda() {
        ViewSnippet handler = new ViewSnippet();
        Context ctx = createContext();

        ViewSnippetResponse response = handler.handleRequest(input, ctx);
        Snippet snippet = response.getSnippet();
        Assert.assertEquals("abcdef", snippet.snippetID);
    }
}
