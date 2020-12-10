package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateSnippetTest extends LambdaTest{

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
    public void testCreateSnippetLambda() {
        CreateSnippet handler = new CreateSnippet();
        Context ctx = createContext();

        CreateSnippetResponse response = handler.handleRequest(input, ctx);
        
        Assert.assertEquals(16, response.snippetID.length());
        Assert.assertEquals(16, response.viewerPassword.length());
        
        SnippetsDAO dao = new SnippetsDAO();
        try {
			Snippet snippetFromDB = dao.getSnippet(response.snippetID);
			Assert.assertEquals(response.getSnippetID(), snippetFromDB.snippetID);
			Assert.assertEquals(response.getViewerPassword(), snippetFromDB.viewerPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
        
    }
}
