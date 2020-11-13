<html>

<head>
	<title>Feedback Application</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
	<link rel="stylesheet" href="mainGrid.css"/>
	<link rel="icon" type="image/png" href="favicon.png"/>
	<link rel="stylesheet" href="createSnippet.css" />
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
<script src="createSnippet.js"></script>
</html>
