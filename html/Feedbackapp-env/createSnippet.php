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
			<div class="col-md-3" style="border-style: solid;">
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
			<div class="col-md-5" style="border-style: solid none solid none;">
				<br>
				<label>Code: </label><br><textarea id="text" oninput="codeFunction()" rows="25" cols="68"></textarea><br>
			</div>
			<div class="commentSidebar" style="border-style: solid;">
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
					<div class="singleComment" onclick="clickedComment(123)">
						<div class="commentColumn">UniqueID:</div>
						<div class="commentColumn">sdljf489</div>
						<div class="commentColumn">Timestamp:</div>
						<div class="commentColumn">s;odfj8943</div>
						<div class="commentColumn">Start Region:</div>
						<div class="commentColumn">3</div>
						<div class="commentColumn">End Region:</div>
						<div class="commentColumn">11</div>
						<div class="commentText">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br><br><br> quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
					</div>
					<div class="singleComment" onclick="clickedComment(456)">
						<div class="commentColumn">UniqueID:</div>
						<div class="commentColumn">sdljf489</div>
						<div class="commentColumn">Timestamp:</div>
						<div class="commentColumn">s;odfj8943</div>
						<div class="commentColumn">Start Region:</div>
						<div class="commentColumn">3</div>
						<div class="commentColumn">End Region:</div>
						<div class="commentColumn">11</div>
						<div class="commentText">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br><br><br> quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
					</div>
					<div class="singleComment" onclick="clickedComment(789)">
						<div class="commentColumn">UniqueID:</div>
						<div class="commentColumn">sdljf489</div>
						<div class="commentColumn">Timestamp:</div>
						<div class="commentColumn">s;odfj8943</div>
						<div class="commentColumn">Start Region:</div>
						<div class="commentColumn">3</div>
						<div class="commentColumn">End Region:</div>
						<div class="commentColumn">11</div>
						<div class="commentText">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br><br><br> quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
					</div>
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

function clickedComment(id){
	console.log("Clicking Comment: " + id)
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
			window.location.href = ('./notfound.php');
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
