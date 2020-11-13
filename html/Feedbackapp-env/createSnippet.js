function deleteSnippet(){

}

function submitComment(){
	console.log("Submiting Comment")
}

function getRegionValue(child, regionName){
	let region = child.getElementsByClassName(regionName);
	region = region[0].innerHTML;
	console.log(regionName + region);
	return parseInt(region);
}

function getCommentUniqueID(child){
	let commentUniqueIDDiv = child.getElementsByClassName("commentUniqueID");
	commentUniqueID = commentUniqueIDDiv[0].innerHTML;
	console.log("commentUniqueID " + commentUniqueID);
	return commentUniqueID;
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
		</div>
	`
	console.log("new comment: " + commentStr);
	return commentStr;
}

let comments = document.getElementById("comments");
comments.innerHTML += generateCommentString(1, "10:23", 3, 5, "blah");
let longStr = "where "; for(let i = 0; i < 10; i++) {longStr += longStr}
comments.innerHTML += generateCommentString(2, "10:50", 4, 10, longStr);
longStr = "tire"; for(let i = 0; i < 10; i++) {longStr += longStr}
comments.innerHTML += generateCommentString(3, "10:55", 4, 10, longStr);
comments.innerHTML += generateCommentString(4, "10:51", 2, 4, "haha");
comments.innerHTML += generateCommentString(5, "10:51", 30, 32, "bottom");
addOnClickToComments();

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

function reportWindowSize() {
	console.log(window.innerHeight);
	console.log(window.innerWidth);
	let textE = document.getElementById("text")
	let style = getComputedStyle(textE);
    console.log(style.width)
    
    // set up width of text highlight element
	let highlightDiv = document.getElementById("highlight");
    highlightDiv.style.width = style.width;
    
    
    // set up width and height of top highlight mask element
    console.log("offsetTop " + textE.offsetTop);
    let highlightMaskTopE = document.getElementById("highlightMaskTop")
    highlightMaskTopE.style.height = textE.offsetTop;
    highlightMaskTopE.style.width = style.width;
}

window.onresize = reportWindowSize;
reportWindowSize()

let commentEnabled = false
let originalHighlightDivTop = 0;
let currentCommentUniqueID = "";
let currentChild = null;

// set highlight div top relative to scroll bar
function setHighlightDivTop() {
	// get scroll y position
	let textE = document.getElementById("text")
	let scrollTop = textE.scrollTop;
	console.log("scrollTop: " + scrollTop);

	// get highlight div
	let highlightDiv = document.getElementById("highlight");
	let highlightDivTop = parseFloat(highlightDiv.style.top);

	// set highlight div top to correct value
	highlightDiv.style.top = originalHighlightDivTop - scrollTop
}

function changeHighlightComment(enableHighlight, child) {
	if (child == null) {
		return;
	}

	let newRGB = enableHighlight ? "rgb(255, 255, 0)" : "rgb(200,200,200)";
	let style = getComputedStyle(child);
	console.log("style.backgroundColor " + style.backgroundColor);
	child.style.backgroundColor = newRGB;
}

function clickedCommentFunc(child, commentUniqueID, originalRegionStart, originalRegionEnd){
	return function() {

		changeHighlightComment(false, currentChild)
		
		// set enabled to false if highlight is enabled for a different comment
		// already and we want to switch to the new comment instead
		let isSameComment = (currentCommentUniqueID == commentUniqueID)
		if (commentEnabled && isSameComment) {
			regionStart = 0
			regionEnd = 0
			commentEnabled = false;
		}
		else {
			regionStart = originalRegionStart
			regionEnd = originalRegionEnd
			commentEnabled = true;
			changeHighlightComment(true, child);
		}
		
		let lines = regionEnd - regionStart;
		console.log("Clicking Comment: " + regionStart + " " + regionEnd)

		let highlightDiv = document.getElementById("highlight");
		let highlightDivTop = parseFloat(highlightDiv.style.top);
		let highlightDivHeight = 24.3;
		
		highlightDiv.style.height = highlightDivHeight * lines
		originalHighlightDivTop = highlightDivHeight * regionStart
		currentCommentUniqueID = commentUniqueID
		currentChild = child
		setHighlightDivTop();
	}
}

// adjust highlight div top as user scrolls
function codeScrolling() {
	console.log("scrolling");
	setHighlightDivTop();
}

var urlParams = new URLSearchParams(window.location.search);
console.log(urlParams.get('snippetID')); // true
let urlParamsSnippetID = urlParams.get('snippetID');

function codeFunction() {
  httpRequest = new XMLHttpRequest();
  httpRequest.open('POST', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}`, true);
  httpRequest.setRequestHeader('Content-Type', 'application/json');
  let body = document.getElementById("text").value;
  body = body.replace(/\n/g,"\\n").replace(/\r/g,"\\r").replace(/\"/g,'\\"')
  body = '{"action":"update","text":"' + body + '"}';
  console.log(body);
  httpRequest.send(body);
  httpRequest.onreadystatechange = nameOfTheFunction;
}

  httpRequest1 = new XMLHttpRequest();
  httpRequest1.open('GET', `https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/${urlParamsSnippetID}`, true);
  httpRequest1.setRequestHeader('Content-Type', 'application/json');
  httpRequest1.send();
  httpRequest1.onreadystatechange = nameOfTheFunction2;

function nameOfTheFunction() {
  if (httpRequest.readyState === XMLHttpRequest.DONE) {
      if (httpRequest.status === 200) {
		//console.log("responseText: " + httpRequest.responseText)
		//const obj = JSON.parse(httpRequest.responseText);
		console.log("success")
      } else {
        console.log("status: " + httpRequest.status)
      }

  } else {
	  console.log("not done")
  }
}
function nameOfTheFunction2() {
  if (httpRequest1.readyState === XMLHttpRequest.DONE) {
      if (httpRequest1.status === 200) {
        let body1 = '{"action":"update","text":"' + document.getElementById("text").value + '"}'
		console.log("responseText: " + httpRequest1.responseText)
		const obj = JSON.parse(httpRequest1.responseText);
		if (obj['snippet'] === undefined) {
			let commentSidebarDiv = document.getElementById("commentSidebar");
			let snippetInfoSidePanelDiv = document.getElementById("snippetInfoSidePanel");
			commentSidebarDiv.innerHTML = "";
			snippetInfoSidePanelDiv.innerHTML = "";

			let snippetNotFoundHTML = `
				<div class="snippetNotFoundText">
						Snippet ID ${urlParamsSnippetID} not found!
				</div>
			`;
			let snippetTextPanelDiv = document.getElementById("snippetTextPanel");
			snippetTextPanelDiv.innerHTML = snippetNotFoundHTML;
			return;
		}
		
		document.getElementById("text").value = obj['snippet']['text'];
		document.getElementById("timestampDiv").innerHTML = obj['snippet']['timestamp'];
		document.getElementById("Planguage").value = obj['snippet']['codingLanguage'];
		
		console.log("success")
      } else {
        console.log("status: " + httpRequest1.status)
      }

  } else {
	  console.log("bad")
  }
}