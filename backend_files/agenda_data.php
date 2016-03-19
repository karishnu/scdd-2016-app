<?php

$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

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