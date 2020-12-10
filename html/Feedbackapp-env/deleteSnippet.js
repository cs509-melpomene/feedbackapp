import { urlParamsSnippetID } from './util.js';
import { viewAllSnippetsHTTPRequest } from './viewAllSnippets.js';

export function deleteSnippetHTTPRequest(snippetID=null){
    let httpRequest = new XMLHttpRequest();
    if (snippetID == null){
        httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}`, true);
    }
    else{
        httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${snippetID}`, true);
    }
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let body = '{"action":"delete"}';
    console.log(body);
    httpRequest.send(body);
    httpRequest.onreadystatechange = deleteSnippetHTTPResponse(httpRequest, snippetID);
}

export function snippetNotFound(){
    let commentSidebarDiv = document.getElementById("commentSidebar");
    let snippetInfoSidePanelDiv = document.getElementById("snippetInfoSidePanel");
    commentSidebarDiv.innerHTML = "";
    snippetInfoSidePanelDiv.innerHTML = "";
    document.getElementById("codeNumbersSidePanel").innerHTML = "";
    let snippetNotFoundHTML = `
        <div class="snippetNotFoundText">
                Snippet ID ${urlParamsSnippetID} not found!
        </div>
    `;
    let snippetTextPanelDiv = document.getElementById("snippetTextPanel");
    snippetTextPanelDiv.innerHTML = snippetNotFoundHTML;
}

function deleteSnippetHTTPResponse(httpRequest, snippetID=null){
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                console.log("responseText: " + httpRequest.responseText)
                if (snippetID = null){
                    snippetNotFound();
                }
                else{
                    location.reload();
                }
                return;
            }
        } else {
            console.log("bad")
        }
    }  
}