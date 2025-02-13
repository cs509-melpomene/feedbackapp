import { urlParamsSnippetID } from './util.js';
import { viewSnippetHTTPRequest } from './viewSnippet.js';
import { resetCurrentComment } from './viewSnippet.js'; // TODO: move to new file called comments.js?

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

                updateCommentHTTPRequest(obj['commentID'], true);
            } else {
                console.log("status: " + httpRequest.status)
                console.log(httpRequest.responseText)
            }
    
        } else {
            console.log("not done")
        }
    }
}

// isUpdate: if true then update text, otherwise delete comment
export function updateCommentHTTPRequest(commentID, isUpdate){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}/comment/${commentID}`, true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let commentTextInput = document.getElementById("commentTextID");
    console.log("commentTextInput " + commentTextInput.value);
    let body = {
        "action": "delete"
    };

    if (isUpdate) {
        body = {
            "text": commentTextInput.value,
            "action": "update"
        };
    }

    console.log(body);
    httpRequest.send(JSON.stringify(body));
    httpRequest.onreadystatechange = updateCommentHTTPResponse(httpRequest);
}

function updateCommentHTTPResponse(httpRequest) {
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                console.log("success")
                console.log(httpRequest.responseText)
                const obj = JSON.parse(httpRequest.responseText);
            } else {
                console.log("status: " + httpRequest.status)
                console.log(httpRequest.responseText)
            }
            viewSnippetHTTPRequest(); // lambda currently sends 500 either way
            resetCurrentComment()
        } else {
            console.log("not done")
        }
    }
}