<?php
$ftp_server = "myserver2015.bugs3.com";


$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a3628048_scdd";
$mysql_user = "a3628048_scdd";
$mysql_password = "project-android-scdd2016";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 



$query="select * from agenda";

$agenda_data["agenda_data"] = array();

$result=mysql_query($query);


if($result){
	
  while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $temp=array();

  $temp["success"]="true";
    $temp["title"]=$row["title"];
    $temp["time"]=$row["time"];

    array_push($agenda_data["agenda_data"], $temp);
}
}
else
{
	 $temp=array();
  	$temp["success"]="false";

  	array_push($agenda_data["agenda_data"], $temp);
}

echo json_encode($agenda_data);



mysql_close();
exit();

 ?>