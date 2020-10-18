package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateSnippetTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testTestCustomLambda() {
        CreateSnippet handler = new CreateSnippet();
        Context ctx = createContext();

        CreateSnippetResponse response = handler.handleRequest(input, ctx);
        Assert.assertEquals(16, response.getResponse().length());
    }
}
