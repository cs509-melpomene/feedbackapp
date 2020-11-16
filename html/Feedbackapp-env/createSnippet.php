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
					<label>Information:</label><br><textarea id="info" oninput="updateSnippetHTTPRequest('info')" rows="15" cols="30" id="info"
					    <?php
                            if (strcmp($_POST["isCreator"] , "true" ) != 0){
                              echo("readonly");
                           }
                        ?>></textarea><br>
					<div>
					</div>
					<?php if (strcmp($_POST["isCreator"] , "true" ) == 0): ?>
                        <input type="button" onclick="deleteSnippetHTTPRequest()" value="Delete Snippet" style = "font-size:20px">
                    <?php endif; ?>

			</div>
			<div class="col-md-1" id="codeNumbersSidePanel" style="border-style: solid none solid none; text-align: right;">
				<br>
				<br>
				<textarea readonly id="numbers" class="snippetLineNumbers numbers" cols="4" rows="27"><?php for($i = 1; $i <= 500; $i++){echo $i; echo ("\n");}?></textarea>
			</div>
			<div class="col-md-5" id="snippetTextPanel" style="border-style: solid none solid none;">
				<br>
				<label>Code: </label><br>
				<div class="highlightWrapper" id="highlightWrapper">
					<div class="highlight" id="highlight"></div>
				</div>
				<textarea class="snippetText" id="text" oninput="updateSnippetHTTPRequest('text')" onscroll="codeScrolling()"></textarea>
				<br>
			</div>
			<div class="commentSidebar" id="commentSidebar" style="border-style: solid;">
				<br>
				<label>Comments: </label><br>
				<div class="singleComment">
				<form id="commentForm" action="">
					<label class="commentFormColumnLabel" for="regionStart">Start Region:</label>
					<input class="commentFormColumnInput" type="text" id="regionStartID" name="regionStart">
					<label class="commentFormColumnLabel" for="regionEnd">End Region:</label>
					<input class="commentFormColumnInput" type="text" id="regionEndID" name="regionEnd">
					<textarea class="columnFormText" id="commentTextID"></textarea><br>
					<input class="submitColumnButton" type="button" onclick="createCommentHTTPRequest()" value="Submit" /><br>
				</form>
				</div>
				<div id="comments">
				</div>
			</div>
		</div>
	</div>
	</form>
</body>
<script type="module" src="createSnippet.js"></script>
</html>
