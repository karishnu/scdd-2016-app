<?php
$ftp_server = "myserver2015.bugs3.com";


$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a3628048_scdd";
$mysql_user = "a3628048_scdd";
$mysql_password = "project-android-scdd2016";

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


