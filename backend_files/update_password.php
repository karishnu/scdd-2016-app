<?php

$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

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