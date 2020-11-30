import {
    urlParamsSnippetID,
    updateNumbers
} from './util.js';
import { snippetNotFound } from './deleteSnippet.js';
import { updateCommentHTTPRequest } from './createComment.js';

export function viewSnippetHTTPRequest(requirePassword=false) {
    let httpRequest1 = new XMLHttpRequest();
    httpRequest1.open('GET', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}`, true);
    httpRequest1.setRequestHeader('Content-Type', 'application/json');
    httpRequest1.send();
    httpRequest1.onreadystatechange = viewSnippetHTTPResponse(httpRequest1, requirePassword);
}

function viewSnippetHTTPResponse(httpRequest, requirePassword) {
    return function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                console.log("responseText: " + httpRequest.responseText)
                const obj = JSON.parse(httpRequest.responseText);
                if (obj['snippet'] === undefined) {
                    snippetNotFound();
                    return;
                }
                
                window.globalSnippet = obj
                if (!requirePassword) {
                    viewSnippetHTTPResponseFinish();
                }
                console.log("success")
            } else {
                console.log("status: " + httpRequest.status)
            }

        } else {
            console.log("bad")
        }
    }
}

export function viewSnippetHTTPResponseFinish(){
    document.getElementById("text").value = window.globalSnippet['snippet']['text'];
    document.getElementById("info").value = window.globalSnippet['snippet']['info'];
    document.getElementById("timestampDiv").innerHTML = window.globalSnippet['snippet']['timestamp'];
    document.getElementById("codeLanguage").value = window.globalSnippet['snippet']['codingLanguage'];
    document.getElementById("viewerPasswordInput").value = window.globalSnippet['snippet']['viewerPassword'];

    updateNumbers(window.globalSnippet['snippet']['text']);

    let comments = window.globalSnippet['snippet']['comments']
    repopulateCommentsDiv(comments)
}

function repopulateCommentsDiv(comments){
    let commentsDiv = document.getElementById("comments");
    commentsDiv.innerHTML = "";
    for(let i = 0; i < comments.length; i++){
        let comment = comments[i];
        commentsDiv.innerHTML += generateCommentString(comment['commentID'], comment['timestamp'], comment['startLine'], comment['endLine'], comment['text']); 
    }
    addOnClickToComments();
}

function getRegionValue(child, regionName){
	let region = child.getElementsByClassName(regionName);
	region = region[0].innerHTML;
	console.log(regionName + region);
	return parseInt(region);
}

function getCommentUniqueID(child){
	let commentUniqueIDDiv = child.getElementsByClassName("commentUniqueID");
	let commentUniqueID = commentUniqueIDDiv[0].innerHTML;
	console.log("commentUniqueID " + commentUniqueID);
	return commentUniqueID;
}

function clickedCommentFunc(child, commentUniqueID, originalRegionStart, originalRegionEnd){
	return function() {

		changeHighlightComment(false, currentChild)
        
        let regionStart = 0;
        let regionEnd = 0;

		// set enabled to false if highlight is enabled for a different comment
		// already and we want to switch to the new comment instead
        let isSameComment = (currentCommentUniqueID == commentUniqueID)
		if (commentEnabled && isSameComment) {
			regionStart = 0
			regionEnd = 0
			commentEnabled = false;
		}
		else {
			regionStart = originalRegionStart - 1 // inclusive of region start
			regionEnd = originalRegionEnd // already inclusive of region end
			commentEnabled = true;
			changeHighlightComment(true, child);
		}
		
		let lines = regionEnd - regionStart;
		console.log("Clicking Comment: " + regionStart + " " + regionEnd)

		let highlightDiv = document.getElementById("highlight");
		let highlightDivTop = parseFloat(highlightDiv.style.top);
		let highlightDivHeight = 24.3;
        
        originalHighlightDivHeight = highlightDivHeight * lines
		highlightDiv.style.height = originalHighlightDivHeight
		originalHighlightDivTop =  highlightDivHeight * regionStart
		currentCommentUniqueID = commentUniqueID
		currentChild = child
		setHighlightDivTop();
	}
}

function addOnClickToComments() {
	let comments = document.getElementById("comments");
	let children = comments.childNodes;
	[].forEach.call(children, function(child) {
		if (child.nodeType == Node.TEXT_NODE)
			return;

		let commentUniqueID = getCommentUniqueID(child);
		let regionStart = getRegionValue(child, "regionStart");
		let regionEnd = getRegionValue(child, "regionEnd");

        child.onclick = clickedCommentFunc(child, commentUniqueID, regionStart,regionEnd)
	});
}

function generateCommentString(uniqueID, timestamp, startRegion, endRegion, text) {
	let commentStr = `
		<div class="singleComment">
			<div class="commentColumn">UniqueID:</div>
			<div class="commentColumn commentUniqueID">${uniqueID}</div>
			<div class="commentColumn">Timestamp:</div>
			<div class="commentColumn">${timestamp}</div>
			<div class="commentFormColumnLabel">Start Region:</div>
			<div class="commentFormColumnInput regionStart">${startRegion}</div>
			<div class="commentFormColumnLabel">End Region:</div>
			<div class="commentFormColumnInput regionEnd">${endRegion}</div>
            <div class="commentText">${text}</div>
            <input type="button" onclick="deleteCommentClick('${uniqueID}')" value="delete"/>
		</div>
	`
	console.log("new comment: " + commentStr);
	return commentStr;
}

export function deleteCommentClick(uniqueID){
    console.log("clicked delete comment button: " + uniqueID)
    updateCommentHTTPRequest(uniqueID, false)
}

export function resetCurrentComment() {
    window.commentEnabled = false
    window.originalHighlightDivTop = 0;
    window.originalHighlightDivHeight = 0;
    window.currentCommentUniqueID = "";
    window.currentChild = null;
    setHighlightDivTop();
}

// set highlight div top relative to scroll bar
export function setHighlightDivTop() {
	// get scroll y position
	let textE = document.getElementById("text")
	let scrollTop = textE.scrollTop;
	console.log("scrollTop: " + scrollTop);

	// get highlight div
	let highlightDiv = document.getElementById("highlight");
	let highlightDivTop = parseFloat(highlightDiv.style.top);

    // set highlight div top and height to correct value
    let style = getComputedStyle(textE);
    let textEHeight = parseFloat(style.height);
    console.log("textEHeight " + textEHeight);

    let newTop = originalHighlightDivTop - scrollTop
    let newHeight = originalHighlightDivHeight
    console.log("highlightDiv.style.top " + highlightDiv.style.top);
    console.log("newTop " + newTop);

    // if highlight is completely over the text area
    if (newTop + newHeight < 0) {
        newTop = 0
        newHeight = 0
    }
    // if highlight is partially over the text area
    else if (newTop < 0) {
        newHeight += newTop; // new height based on where top should be
        newTop = 0;
    }
    // if highlight is completely under text area
    else if (newTop > textEHeight) {
        newTop = textEHeight;
        newHeight = 0
    }
    // if highlight is partially under the text area
    else if (newTop + newHeight > textEHeight) {
        newHeight = textEHeight - newTop // new height based on text area's height and where top is

    }

    highlightDiv.style.top = newTop
    highlightDiv.style.height = newHeight
}
