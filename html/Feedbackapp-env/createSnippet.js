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
  let bodyOrg = document.getElementById("text").value;
  body = bodyOrg.replace(/\n/g,"\\n").replace(/\r/g,"\\r").replace(/\"/g,'\\"')
  body = '{"action":"update","text":"' + body + '"}';
  console.log(body);
  httpRequest.send(body);
  httpRequest.onreadystatechange = nameOfTheFunction;
  updateNumbers(bodyOrg);
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
        
        updateNumbers(obj['snippet']['text']);

        let comments = obj['snippet']['comments']
        let commentsDiv = document.getElementById("comments");
        for(let i = 0; i < comments.length; i++){
            let comment = comments[i];
            commentsDiv.innerHTML += generateCommentString(comment['commentID'], comment['timestamp'], comment['startLine'], comment['endLine'], comment['text']); 
        }
        addOnClickToComments();
		console.log("success")
      } else {
        console.log("status: " + httpRequest1.status)
      }

  } else {
	  console.log("bad")
  }
}

function updateNumbers(text){
    let numOfNumbers = text.replace(/[^\n]/g,"").length;
    let numbersStr = "";
    for (let i = 1; i <= numOfNumbers + 1; i++){ // adding 1 because last line might not have newline char
        numbersStr += i + "\n"
    }
    document.getElementById("numbers").value = numbersStr;
}

var s1 = document.getElementById('numbers');
var s2 = document.getElementById('text');

function select_scroll_1(e) { s2.scrollTop = s1.scrollTop; }
function select_scroll_2(e) { s1.scrollTop = s2.scrollTop; }

s1.addEventListener('scroll', select_scroll_1, false);
s2.addEventListener('scroll', select_scroll_2, false);