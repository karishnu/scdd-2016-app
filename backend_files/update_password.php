<?php
$ftp_server = "myserver2015.bugs3.com";

// set up a connection or die
//$conn_id = ftp_connect($ftp_server) or die($ftp_server);
$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a3628048_scdd";
$mysql_user = "a3628048_scdd";
$mysql_password = "project-android-scdd2016";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 

$user=$_POST["username"];
$pass=$_POST["password"];


$query="update login set `password`='$pass' where `username`='$user' ";

$reset["reset_password"]=array();
$temp=array();

$temp["query"]=$query;

$result=mysql_query($query);

if(result)
$temp["status"]="true";
else
$temp["status"]="false";

array_push($reset["reset_password"], $temp);

echo json_encode($reset);

mysql_close();
exit();

?>