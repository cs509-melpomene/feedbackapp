<?php

var_dump(parse_url($_SERVER['REQUEST_URI']));
echo $_SERVER['REQUEST_URI'];
if (false)
   include 'login.php';
else
   echo "bye";
?>