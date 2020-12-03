import { deleteSnippetHTTPRequest } from './deleteSnippet.js';
import { updateSnippetHTTPRequest } from './updateSnippet.js';
import { createCommentHTTPRequest } from './createComment.js';
import { viewSnippetHTTPRequest } from './viewSnippet.js';
import { setHighlightDivTop } from './viewSnippet.js'; // TODO: move to new file called comments.js?
import { deleteCommentClick } from './viewSnippet.js'; // TODO: move to new file called comments.js?
import { resetCurrentComment } from './viewSnippet.js'; // TODO: move to new file called comments.js?

window.deleteSnippetHTTPRequest = deleteSnippetHTTPRequest
window.updateSnippetHTTPRequest = updateSnippetHTTPRequest
window.createCommentHTTPRequest = createCommentHTTPRequest
viewSnippetHTTPRequest(true); // not called by a button, called on page load

window.deleteCommentClick = deleteCommentClick

function selectionChange(){
    console.log('new selection');
    let selection = document.getSelection()
    let selectionStr = document.getSelection().toString()
    console.log('selection ', selection);
    console.log('selction string ' + selectionStr);
    let currentNode = selection.focusNode.parentElement;
    while (currentNode.id == '') {
        currentNode = currentNode.parentElement
    }
    console.log('selction currentNode.id ' + currentNode.id);
    if (currentNode.id == 'text') {
        console.log('selection from snippet text / code')
        let textE = document.getElementById('text');
        let textStr = textE.innerText;
        "".replace(/\r/, "")

        let textSelStart = textStr.indexOf(selectionStr)
        let textSelEnd = textSelStart + selectionStr.length
        /*let textSelStart = textE.selectionStart;
        le=t textSelEnd = textE.selectionEnd;*/

        /*console.log("selection.getRangeAt(0)" + selection.getRangeAt(0))
        let textSelStart = selection.getRangeAt(0).startOffset;
        let textSelEnd = selection.getRangeAt(0).endOffset;*/
        
        let textSub0 = textStr.substring(0, textSelStart);
        let textSub1 = textStr.substring(textSelStart, textSelEnd);
        console.log('substr ' + textSub1);

        let linesStart = textSub0.split("\n").length
        let linesNum = textSub1.split("\n").length - 1
        document.getElementById('regionStartID').value = linesStart
        document.getElementById('regionEndID').value = linesStart + linesNum
    }
}
document.onselectionchange = selectionChange

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

function adjustBlurred(){
    let blurredDiv = document.getElementById("blurredDiv")
    blurredDiv.style.height = window.innerHeight 
    blurredDiv.style.width = window.innerWidth 
}

function windowResize(){
    adjustHighlightWidth()
    adjustBlurred()
}

window.onload = function() {
    // on window resize, recompute highlight width
    window.onresize = windowResize;
    windowResize() // initial computation
}


resetCurrentComment();

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

window.togglePasswordText = function togglePasswordText(){
    var viewPasswordInput = document.getElementById("viewerPasswordInput");
    if (viewPasswordInput.type === "password") {
        viewPasswordInput.type = "text";
    } else {
        viewPasswordInput.type = "password";
    }
}

window.copyViewerPassword = function copyViewerPassword(){
    var neededTypeSwitch = false;
    var viewPasswordInput = document.getElementById("viewerPasswordInput");
    if (viewPasswordInput.type === "password") {
        viewPasswordInput.type = "text";
        neededTypeSwitch = true;
    }
    viewPasswordInput.select();
    viewPasswordInput.setSelectionRange(0, 99999);
    document.execCommand("copy");
    if (neededTypeSwitch) {
        viewPasswordInput.type = "password";
    }
}

import { viewSnippetHTTPResponseFinish } from './viewSnippet.js';
window.unlockViewerPassword = function unlockViewerPassword(){
    var unlockViewerPasswordText = document.getElementById("unlockViewerPasswordText");
    if (unlockViewerPasswordText.value == window.globalSnippet['snippet']['viewerPassword'] ) {
        var blurredDiv = document.getElementById("blurredDiv");
        blurredDiv.hidden = true
        viewSnippetHTTPResponseFinish();
    }
}