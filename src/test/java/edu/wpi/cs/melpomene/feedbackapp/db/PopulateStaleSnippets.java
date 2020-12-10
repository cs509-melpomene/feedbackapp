package edu.wpi.cs.melpomene.feedbackapp.db;

import com.amazonaws.services.lambda.runtime.Context;

import edu.wpi.cs.melpomene.feedbackapp.TestContext;
import edu.wpi.cs.melpomene.feedbackapp.http.CreateSnippetResponse;
import edu.wpi.cs.melpomene.feedbackapp.lambda.CreateSnippet;

public class PopulateStaleSnippets {

	private static Object input = null;

    private static Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			CreateSnippet handler = new CreateSnippet();
	        Context ctx = createContext();	
	        CreateSnippetResponse response = handler.handleRequest(input, ctx);

	        try {
				DatabaseUtilHelper.updateTimestamp(response.snippetID, i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}