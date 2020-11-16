import { snippetNotFound } from './deleteSnippet.js';
import { deleteSnippetHTTPRequest } from './deleteSnippet.js';
import { updateSnippetHTTPRequest } from './updateSnippet.js';
import { viewSnippetHTTPRequest } from './viewSnippet.js';
import { urlParamsSnippetID } from './util.js';
import { setHighlightDivTop } from './viewSnippet.js'; // TODO: move to new file called comments.js?

window.deleteSnippetHTTPRequest = deleteSnippetHTTPRequest
window.updateSnippetHTTPRequest = updateSnippetHTTPRequest
viewSnippetHTTPRequest(); // not called by a button, called on page load

window.createCommentHTTPRequest = function createCommentHTTPRequest(){
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

// resets highlight width to width of text element
function adjustHighlightWidth() {
	console.log(window.innerHeight);
	console.log(window.innerWidth);
	let textE = document.getElementById("text")
	let style = getComputedStyle(textE);
    console.log(style.width)
    
    // set up width of text highlight element
	let highlightDiv = document.getElementById("highlight");
    highlightDiv.style.width = style.width;
}

// on window resize, recomput highlight width
window.onresize = adjustHighlightWidth;
adjustHighlightWidth() // initial computation

window.commentEnabled = false
window.originalHighlightDivTop = 0;
window.originalHighlightDivHeight = 0;
window.currentCommentUniqueID = "";
window.currentChild = null;

window.changeHighlightComment = function changeHighlightComment(enableHighlight, child) {
	if (child == null) {
		return;
	}

	let newRGB = enableHighlight ? "rgb(255, 255, 0)" : "rgb(200,200,200)";
	let style = getComputedStyle(child);
	console.log("style.backgroundColor " + style.backgroundColor);
	child.style.backgroundColor = newRGB;
}

// adjust highlight div top as user scrolls
window.codeScrolling = function codeScrolling() {
	console.log("scrolling");
	setHighlightDivTop();
}

var s1 = document.getElementById('numbers');
var s2 = document.getElementById('text');

function select_scroll_1(e) { s2.scrollTop = s1.scrollTop; }
function select_scroll_2(e) { s1.scrollTop = s2.scrollTop; }

s1.addEventListener('scroll', select_scroll_1, false);
s2.addEventListener('scroll', select_scroll_2, false);