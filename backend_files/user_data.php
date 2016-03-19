<?php
$ftp_server = "myserver2015.bugs3.com";


$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a3628048_scdd";
$mysql_user = "a3628048_scdd";
$mysql_password = "project-android-scdd2016";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 



$query="select * from profile";

$user_data["user_data"] = array();

$result=mysql_query($query);


if($result){
	
  while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $temp=array();

  	$temp["success"]="true";
  	$temp["username"]=$row["username"];
    $temp["email"]=$row["email"];
    $temp["phone"]=$row["mobile"];
    $temp["first_name"]=$row["first_name"];
    $temp["last_name"]=$row["last_name"];
    $temp["company_name"]=$row["com_name"];
    $temp["designation"]=$row["designation"];
    $temp["address"]=$row["address"];

    array_push($user_data["user_data"], $temp);
}
}
else
{
	 $temp=array();
  	$temp["success"]="false";

  	array_push($user_data["user_data"], $temp);
}

echo json_encode($user_data);



mysql_close();
exit();

 ?>