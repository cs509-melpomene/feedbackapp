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
				<label>Code: </label><br><textarea rows="30" cols="50"></textarea><br>
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

</html>