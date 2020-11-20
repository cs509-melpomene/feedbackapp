import {
    urlParamsSnippetID,
    updateNumbers
} from './util.js';

// update snippet
// divID: either 'text' or 'info' based on which part of the snippet you want to update
export function updateSnippetHTTPRequest(divID) {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}`, true);
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let bodyOrg = document.getElementById(divID).value;
    let body = { "action": "update" };
    body[divID] = bodyOrg;

    // set text to null if we are updating the snippet info
    if (divID != "text") {
        body["text"] = null;
    }

    console.log(body);
    httpRequest.send(JSON.stringify(body));
    httpRequest.onreadystatechange = updateSnippetHTTPResponse(httpRequest);

    // update line numbers only if we updated the code text
    if (divID == "text") {
        updateNumbers(bodyOrg);
    }
}

function updateSnippetHTTPResponse(httpRequest) {
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                    console.log("success")
            } else {
                    console.log("status: " + httpRequest.status)
            }
        } else {
            console.log("not done")
        }
    }
}
