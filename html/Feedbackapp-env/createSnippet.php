<html>

<head>
	<title>Feedback Application</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
	<link rel="stylesheet" href="mainGrid.css"/>
	<link rel="icon" type="image/png" href="favicon.png"/>
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
					Time Stamp: <?php
					    date_default_timezone_set("America/New_York");
						echo(date("m-d-Y h:i a"));
						?>
					<br>Programming Language: <br><input type="text" id="Planguage"
					<?php
                    	if (strcmp($_POST["isCreator"] , "true" ) != 0){
                                echo("readonly");
                    		}
                    ?>><br>
					<div>
                    </div>
					<label>Information:</label<br><textarea rows="20" cols="30" id="info"
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
				<label>Code: </label><br><textarea id="text" oninput="codeFunction()" rows="27" cols="68"></textarea><br>
			</div>
			<div class="col-md-3" style="border-style: solid;">
				<br>
				<label>Comments: </label><br><textarea rows="27" cols="30" id="comment"></textarea><br>
				<br>
				<br>
			</div>
		</div>
	</div>
	</form>
</body>
<script>
function deleteSnippet(){

}
function codeFunction() {
  httpRequest = new XMLHttpRequest();
  httpRequest.open('POST', 'https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/<?php echo($_GET["snippetID"])?>', true);
  httpRequest.setRequestHeader('Content-Type', 'application/json');
  let body = '{"action":"update","text":"' + document.getElementById("text").value + '"}';
  console.log(document.getElementById("text").value);
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
		document.getElementById("text").value = obj['snippet']['text']
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
