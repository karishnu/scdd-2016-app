<?php



$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 


$user=$_POST["username"];

$query="select * from profile where `username`='$user'";
$query1="select * from events";
$query2="select * from agenda";

$sync["sync"] = array();

$result=mysql_query($query);
$user_data["user_data"]=array();
$events_data["events_data"]=array();
$agenda_data["agenda_data"]=array();

if($result){
	
    $temp=array();

  	$row = mysql_fetch_array($result, MYSQL_ASSOC);

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
else
{
	 $temp=array();
  	$temp["success"]="false";

  	array_push($user_data["user_data"], $temp);
}

     

$result=mysql_query($query1);

if($result)
{	
while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {

 $temp=array();

	$temp["success"]="true";
  	$temp["title"]=$row["title"];
    $temp["place"]=$row["place"];
    $temp["venue"]=$row["venue"];
    $temp["year"]=$row["year"];
    $temp["date"]=$row["date"];
    $temp["weekday"]=$row["weekday"];
    $temp["latitude"]=$row["latitude"];
    $temp["longitude"]=$row["longitude"];

array_push($events_data["events_data"], $temp);
}
}
else
{
	 $temp=array();

  	$temp["success"]="false";
  	array_push($events_data["events_data"], $temp);
}

 $result=mysql_query($query2);

if($result)
{	
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

array_push($sync["sync"],$user_data);
array_push($sync["sync"],$events_data);
array_push($sync["sync"],$agenda_data);

 echo json_encode($sync);


exit();

 ?>