<?php

$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a3628048_scdd";
$mysql_user = "a3628048_scdd";
$mysql_password = "project-android-scdd2016";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 

$username=$_POST["username"];
$rev=$_POST["review"];

$review["review"]=array();
 $temp=array();

$query="select `first_name` , `last_name` , `email` from profile where `username` = '$username' ; ";
$result=mysql_query($query);

if($result)
{

	$row = mysql_fetch_array($result, MYSQL_ASSOC);
	$fn=$row["first_name"];
	$ln=$row["last_name"];
	$email=$row["email"];

	$query="insert into feedback values ('$username','$fn','$ln','$email','$rev'); ";

	$result=mysql_query($query);

	$temp["query"]=$query;

	if($result)
		$temp["status"]="1";
	else
		$temp["status"]="0.0";

}
else
 	$temp["status"]="0.1";

array_push($review["review"],$temp);


echo json_encode($review);

exit();

 ?>