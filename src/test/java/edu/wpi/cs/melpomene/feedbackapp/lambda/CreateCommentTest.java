package edu.wpi.cs.melpomene.feedbackapp.lambda;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.db.CommentsDAO;
import edu.wpi.cs.melpomene.feedbackapp.db.SnippetsDAO;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateCommentRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateCommentResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateCommentRequest;
import edu.wpi.cs.melpomene.feedbackapp.http.UpdateCommentResponse;
import edu.wpi.cs.melpomene.feedbackapp.model.Comment;
import edu.wpi.cs.melpomene.feedbackapp.model.Snippet;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateCommentTest extends LambdaTest{

    private static CreateCommentRequest input;

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
    public void testCreateCommentLambda() {
    	CreateSnippet csHandler = new CreateSnippet();
    	Context ctx = createContext();
    	CreateSnippetResponse csResponse = csHandler.handleRequest(null, ctx);

    	CreateCommentRequest ccRequest = new CreateCommentRequest(csResponse.snippetID, 3, 5);
    	CreateComment ccHandler = new CreateComment();
    	CreateCommentResponse ccResponse = ccHandler.handleRequest(ccRequest, ctx);
        
        Assert.assertEquals(16, ccResponse.commentID.length());
        
        CommentsDAO dao = new CommentsDAO();
        try {
			Comment commentFromDB = dao.getComment(ccResponse.commentID, ccResponse.snippetID);
			Assert.assertEquals(ccResponse.getSnippetID(), commentFromDB.snippetID);
			Assert.assertEquals(ccResponse.getCommentID(), commentFromDB.commentID);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
}
