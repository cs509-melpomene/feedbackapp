import { deleteSnippetHTTPRequest } from './deleteSnippet.js';
import { updateSnippetHTTPRequest } from './updateSnippet.js';
import { createCommentHTTPRequest } from './createComment.js';
import { viewSnippetHTTPRequest } from './viewSnippet.js';
import { setHighlightDivTop } from './viewSnippet.js'; // TODO: move to new file called comments.js?

window.deleteSnippetHTTPRequest = deleteSnippetHTTPRequest
window.updateSnippetHTTPRequest = updateSnippetHTTPRequest
window.createCommentHTTPRequest = createCommentHTTPRequest
viewSnippetHTTPRequest(); // not called by a button, called on page load

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