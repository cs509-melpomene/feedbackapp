import { urlParamsSnippetID } from './util.js';

export function createCommentHTTPRequest(){
    console.log("Submitting Comment")
    let regionStartInput = document.getElementById("regionStartID");
    let regionEndInput = document.getElementById("regionEndID");
    console.log("region start value " + regionStartInput.value)
    console.log("region end   value " + regionEndInput.value)
    
    let httpRequest3 = new XMLHttpRequest();
    httpRequest3.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}/create-comment`, true);
    httpRequest3.setRequestHeader('Content-Type', 'application/json');
    let body = {
        "startLine": regionStartInput.value,
        "endLine": regionEndInput.value
    };
    console.log(body);
    httpRequest3.send(JSON.stringify(body));
    httpRequest3.onreadystatechange = createCommentHTTPResponse(httpRequest3);
}

function createCommentHTTPResponse(httpRequest) {
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                console.log("success")
                console.log(httpRequest.responseText)
                const obj = JSON.parse(httpRequest.responseText);
                console.log("snippetID " + obj['snippetID'])
                console.log("commentID " + obj['commentID'])

                httpRequest = new XMLHttpRequest();
                httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}/comment/${obj['commentID']}`, true);
                httpRequest.setRequestHeader('Content-Type', 'application/json');
                let commentTextInput = document.getElementById("commentTextID");
                console.log("commentTextInput " + commentTextInput.value);
                let body = {
                    "text": commentTextInput.value,
                    "action": "update"
                };
                console.log(body);
                httpRequest.send(JSON.stringify(body));
                // httpRequest.onreadystatechange = createCommentRequestStateChange; // currently we do not care about the response
            
            } else {
                console.log("status: " + httpRequest.status)
                console.log(httpRequest.responseText)
            }
    
        } else {
            console.log("not done")
        }
    }
}