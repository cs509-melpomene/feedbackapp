<html>

<head>
	<title>Feedback Application</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
	<link rel="stylesheet" href="mainGrid.css"/>
</head>

<body>
	<form action="welcome.php" method="post">
	<div class="container-fluid">
		<div class="row" >
			<div class="col-md-3" style="border-style: solid;">
				<br>
					Unique ID: <?php echo($_GET["snippetID"])?><br>
					Viewer Password: <br><input type="password" name="vPass"><br>
					Time Stamp: <?php
					    date_default_timezone_set("America/New_York");
						echo(date("m-d-Y h:i a"));
						?>
					<br>Programming Language: <br><input type="text" name="language"><br>
					<div>
					    <br>
                       <input type="submit" value="Edit Information">
                       <br>
                    </div>
					<label>Information:</label<br><textarea rows="20" cols="30"></textarea><br>
					<div>
					    <br>
                      <input type="submit" value="Delete Snippet">
                    </div>
					
			</div>
			<div class="col-md-1" style="border-style: solid none solid none; text-align: right;">
			</div>
			<div class="col-md-5" style="border-style: solid none solid none;">
				<br>
				<label>Code: </label><br><textarea id="text" oninput="myFunction()" rows="30" cols="50"></textarea><br>
			</div>
			<div class="col-md-3" style="border-style: solid;">
				<br>
				<label>Comments: </label><br><textarea rows="30" cols="30"></textarea><br>
				<br>
				<br>
			</div>
		</div>
	</div>
</body>
<script>
console.log("<?php echo $_POST["isCreator"] ?>")
function myFunction() {
  httpRequest = new XMLHttpRequest();
  httpRequest.open('POST', 'https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/snippet/<?php echo($_GET["snippetID"])?>', true);
  httpRequest.setRequestHeader('Content-Type', 'application/json');
  let body = '{"action":"update","text":"' + document.getElementById("text").value + '"}'
  httpRequest.send(body);
  httpRequest.onreadystatechange = nameOfTheFunction;
}
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
	  console.log("bad")
  }
}
</script>
</html>