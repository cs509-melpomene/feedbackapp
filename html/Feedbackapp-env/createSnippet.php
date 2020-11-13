<html>

<head>
	<title>Feedback Application</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
	<link rel="stylesheet" href="mainGrid.css"/>
	<link rel="icon" type="image/png" href="favicon.png"/>
	<link rel="stylesheet" href="main.css" />
	<title>Feedback Application</title>
</head>

<body>
    <form>
	<div class="container-fluid">
		<div class="row" >
			<div class="col-md-3" id="snippetInfoSidePanel" style="border-style: solid;">
				<br>
					Unique ID: <?php echo($_GET["snippetID"])?><br>
					<a href="http://feedbackapp-env.eba-9ipq52ps.us-east-2.elasticbeanstalk.com/createSnippet.php?snippetID=<?php echo($_GET["snippetID"])?>" target="_blank">Click to open Viewer Screen</a>
					                <br>
					Time Stamp: <div id='timestampDiv'></div>
					<br>Programming Language: <br><input type="text" id="Planguage"
					<?php
                    	if (strcmp($_POST["isCreator"] , "true" ) != 0){
                                echo("readonly");
                    		}
                    ?>><br>
					<div>
                    </div>
					<label>Information:</label><br><textarea rows="15" cols="30" id="info"
					    <?php
                            if (strcmp($_POST["isCreator"] , "true" ) != 0){
                              echo("readonly");
                           }
                        ?>></textarea><br>
					<div>
                    </div>
                    <input type="button" onclick="deleteSnippet()" value="Delete Snippet" style = "font-size:20px">

			</div>
			<div class="col-md-1" style="border-style: solid none solid none; text-align: right;">
			</div>
			<div class="col-md-5" id="snippetTextPanel" style="border-style: solid none solid none;">
				<br>
				<label>Code: </label><br>
				<div class="highlightWrapper" id="highlightWrapper">
					<div class="highlight" id="highlight"></div>
				</div>
				<textarea class="snippetText" id="text" oninput="codeFunction()" onscroll="codeScrolling()"></textarea>
				<br>
			</div>
			<div class="commentSidebar" id="commentSidebar" style="border-style: solid;">
				<br>
				<label>Comments: </label><br>
				<div class="singleComment">
				<form action="">
					<label class="commentFormColumnLabel" for="regionStart">Start Region:</label>
					<input class="commentFormColumnInput" type="text" id="regionStartID" name="regionStart">
					<label class="commentFormColumnLabel" for="regionEnd">End Region:</label>
					<input class="commentFormColumnInput" type="text" id="regionEndID" name="regionEnd">
					<textarea class="columnFormText" id="comment"></textarea><br>
					<input class="submitColumnButton" type="submit" formmethod="post" onclick="submitComment()"/><br>
				</form>
				</div>
				<div id="comments">
				</div>
			</div>
		</div>
	</div>
	</form>
</body>
<script>
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

function generateCommentString(uniqueID, timestamp, startRegion, endRegion, text) {
	let commentStr = `
		<div class="singleComment">
			<div class="commentColumn">UniqueID:</div>
			<div class="commentColumn">${uniqueID}</div>
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
addOnClickToComments();

function addOnClickToComments() {
	let comments = document.getElementById("comments");
	let children = comments.childNodes;
	[].forEach.call(children, function(child) {
		if (child.nodeType == Node.TEXT_NODE)
			return;

		let regionStart = getRegionValue(child, "regionStart");
		let regionEnd = getRegionValue(child, "regionEnd");

		child.onclick = clickedCommentFunc(regionStart,regionEnd)
	});
}

let commentEnabled = false
let originalHighlightDivTop = 0;

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

function clickedCommentFunc(originalRegionStart, originalRegionEnd){
	return function() {
		// get expected highlight div top to compare with
		let highlightDivHeight = 24.3;
		let comparison = highlightDivHeight * originalRegionStart
		
		// set enabled to false if highlight is enabled for a different comment
		// already and we want to switch to the new comment instead
		let isSameComment = Math.abs(originalHighlightDivTop - comparison) < 0.1;
		if (commentEnabled && isSameComment) {
			regionStart = 0
			regionEnd = 0
			enabled = false;
		}
		else {
			regionStart = originalRegionStart
			regionEnd = originalRegionEnd
			commentEnabled = true;
		}
		let lines = regionEnd - regionStart;
		console.log("Clicking Comment: " + regionStart + " " + regionEnd)

		let highlightDiv = document.getElementById("highlight");
		let highlightDivTop = parseFloat(highlightDiv.style.top);
		highlightDiv.style.height = highlightDivHeight * lines
		originalHighlightDivTop = highlightDivHeight * regionStart
		setHighlightDivTop();
	}
}

// adjust highlight div top as user scrolls
function codeScrolling() {
	console.log("scrolling");
	setHighlightDivTop();
}

function codeFunction() {
  httpRequest = new XMLHttpRequest();
  httpRequest.open('POST', 'https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/<?php echo($_GET["snippetID"])?>', true);
  httpRequest.setRequestHeader('Content-Type', 'application/json');
  let body = document.getElementById("text").value;
  body = body.replace(/\n/g,"\\n").replace(/\r/g,"\\r").replace(/\"/g,'\\"')
  body = '{"action":"update","text":"' + body + '"}';
  console.log(body);
  httpRequest.send(body);
  httpRequest.onreadystatechange = nameOfTheFunction;
}

  httpRequest1 = new XMLHttpRequest();
  httpRequest1.open('GET', 'https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/<?php echo($_GET["snippetID"])?>', true);
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
						Snippet ID <?php echo($_GET["snippetID"])?> not found!
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
</script>
</html>
