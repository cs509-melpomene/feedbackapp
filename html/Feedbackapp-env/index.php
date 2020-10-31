<?php header('Access-Control-Allow-Origin: *'); ?>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="main.css" />
<meta charset="UTF-8" />
<title>Feedback Application</title>
</head>
<body>
   <div class = "center">
      <form>
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
		console.log(obj.snippetID)
		window.location.href = ('./createSnippet.php?snippetID=' + obj.snippetID);
      } else {
        console.log("status: " + httpRequest.status)
      }

  } else {
	  console.log("bad")
  }
}
</script>
</html>