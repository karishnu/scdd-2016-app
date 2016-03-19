<?php

$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 

$user=$_POST["username"];
$fn=$_POST["first_name"];
$ln=$_POST["last_name"];
$email=$_POST["email"];
$mob=$_POST["mobile"];
$comp=$_POST["company_name"];
$des=$_POST["designation"];
$add=$_POST["address"];


$query="update profile set `first_name`='$fn' ,  `last_name`='$ln' , `email`='$email' , `mobile`='$mob' , `com_name`='$comp' , `designation`='$des' , `address`='$add' where `username`='$user' ";

$reset["update_profile"]=array();
$temp=array();

$temp["query"]=$query;

$result=mysql_query($query);

if(result)
$temp["status"]="true";
else
$temp["status"]="false";

array_push($reset["update_profile"], $temp);

echo json_encode($reset);

mysql_close();
exit();

?>