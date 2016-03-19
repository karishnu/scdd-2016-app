<?php

$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 

$user=$_POST["username"];
$pass=$_POST["password"];
$email=$_POST[""];
$phone=$_POST["mobile"];
$fn=$_POST["fn"];
$ln=$_POST["ln"];

$register["register"] = array();
$temp=array();



$query="select * from login where `username`='$user' ";
$result=mysql_query($query);


mysql_close();
exit();
?>


