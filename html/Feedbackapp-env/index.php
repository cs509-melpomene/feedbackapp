<?php header('Access-Control-Allow-Origin: *'); ?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="main.css" />
    <link rel="icon" type="image/png" href="favicon.png"/>
    <meta charset="UTF-8" />
    <title>Feedback Application</title>
</head>
<body>
   <div class = "center">
      <form id="form" method="POST" action="createSnippet.php?snippetID=">
          <input type="hidden" name="isCreator" value="true" style = "font-size:20px">
         <input type="button" onclick="myFunction()" value="Create Snippet" style = "font-size:20px">
      </form> 
      <br>
      <br>
      <form action="Admin.php">
         <label for="adminPass" id="adminPassLabel">Admin Password:</label><br>
         <input type="text" id="adminPass" name="adminPass" ><br>
         <input type="submit" value="Login" style = "font-size:20px;padding-left:50px;padding-right:50px" >
      </form>
   </div>
</body>
<script>
function myFunction() {
  httpRequest = new XMLHttpRequest();
  httpRequest.open('GET', 'https://pg407hi45l.execute-api.us-east-2.amazonaws.com/beta/create-snippet', true);
  //httpRequest.setRequestHeader('Origin', 'blah');
  httpRequest.send();
  httpRequest.onreadystatechange = nameOfTheFunction;
}
function nameOfTheFunction() {
  if (httpRequest.readyState === XMLHttpRequest.DONE) {
      if (httpRequest.status === 200) {
		console.log("responseText: " + httpRequest.responseText)
		const obj = JSON.parse(httpRequest.responseText);
      if (obj['httpCode'] !== 200) {
         console.log('Failed to create snippet.')
         return;
      }
		console.log("snippetID " + obj.snippetID);
      console.log("form " + document.getElementById("form").action);
      document.getElementById("form").action = document.getElementById("form").action + obj.snippetID;
      console.log("form " + document.getElementById("form").action);
      document.getElementById("form").submit();
      } else {
        console.log("status: " + httpRequest.status)
      }
  } else {
	  console.log("bad")
  }
}
</script>
</html>
