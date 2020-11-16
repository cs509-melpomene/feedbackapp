# cs509-melpomene-feedbackapp

### FeedBack System Base URL
1. Go to the url http://feedbackapp-env.eba-9ipq52ps.us-east-2.elasticbeanstalk.com/

### HTML:
#### Warning:
Formatting for entering comments is not fully accurate.
For example, adding an enter to space out comment text will not show up when page is refreshed.

#### Things that are working:
##### Landing Page:
1. "Create snippet" button goes to creater view
2. Admin Password and submit button goes to admin view

##### Only Creator Page:
1. Can enter info into the information column
2. Can click the "Click to open Viewer Screen" link
3. Can delete snippet.

##### Creator & Viewer Page:
1. Can enter text into the code column
2. Can select text and have comment start and end region form auto fill in
3. Can enter text for comment
4. Can submit comment to create a new comment
5. Can refresh page to see new (text, info, comments, deletion of snippet)
6. Can click on comment to see highlighted region.
7. Can scroll to see more code text and, if applicable, find hidden highlighted region

##### Only Viewer Page:
N/A, viewer is a subset of a creator

#### How to use Html: 
##### Create a Snippet:
1. Go to the url http://feedbackapp-env.eba-9ipq52ps.us-east-2.elasticbeanstalk.com/
2. Click "Create snippet" button
3. enter code into the middle column (the one labeled code)

##### See the viewer view:
###### Option 1:
1. From the Creater view, click the "Click to open Viewer Screen" link
2. Can edit code

###### Option 2:
1. Copy url from the Creater view
2. Paste into a new tab/window
3. Can only edit code 
