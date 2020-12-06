<html>

<head>
	<title>Feedback Application</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
	<link rel="stylesheet" href="mainGrid.css"/>
	<link rel="icon" type="image/png" href="favicon.png"/>
	<link rel="stylesheet" href="snippet.css" />
	<link rel="stylesheet" href="./highlight/styles/default.css">
	<title>Feedback Application</title>
</head>

<body>
    <form>
	<div class="container-fluid">
		<div class="row" >
			<div class="col-md-3" id="snippetInfoSidePanel" style="border-style: solid;">
				<br>
					Unique ID: <?php echo($_GET["snippetID"])?><br>
					<a href="./snippet.php?snippetID=<?php echo($_GET["snippetID"])?>" target="_blank">Click to open Viewer Screen</a>
					                <br>
					Time Stamp: <div id='timestampDiv'></div>
					Viewer Password: <input id='viewerPasswordInput' type='password' value='Placeholder' readonly/>
					<input type='checkbox' onclick='togglePasswordText()' />
					<input type='button' onclick='copyViewerPassword()' value='Copy' />
					<br>Programming Language: <br><input type="text" id="codeLanguage" oninput="updateSnippetHTTPRequest('codeLanguage')"
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
				<pre><code readonly id="numbers" class="snippetLineNumbers numbers" cols="4" rows="27"><?php for($i = 1; $i <= 500; $i++){echo $i; echo ("\n");}?></code></pre>
			</div>
			<div class="col-md-5" id="snippetTextPanel" style="border-style: solid none solid none;">
				<br>
				<label>Code: </label><br>
				<div class="highlightWrapper" id="highlightWrapper">
					<div class="highlight" id="highlight"></div>
				</div>
				<pre><code class="snippetText" id="text" oninput="updateSnippetHTTPRequest('text')" onscroll="codeScrolling()" contenteditable></code></pre>
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
	<div id='blurredDiv' class='blurred'>
		<div class='viewerPasswordInputDiv'>
			<form>
				<label>Viewer Password:</label><br>
				<input type='text' id='unlockViewerPasswordText' /><br>
				<input type='button' value='Unlock' onclick='unlockViewerPassword()'/>
			</form>
		</div>
	</div>
	<?php
		if (strcmp($_POST["isCreator"] , "true" ) == 0){
			echo('<div id="isCreator" hidden />');
		}
	?>>
</body>
<script type="module" src="snippet.js"></script>
<script src="./highlight/highlight.pack.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
</html>
