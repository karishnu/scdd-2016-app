<?php

$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 

$user=$_POST["user_email"];
$code=$_POST["code"];


$forgotpass["reply"] = array();

$temp=array();
$temp["exists_username"]="false";
$temp["exists_email"]="false";
$temp["email_id"]="123";
$temp["username"]="123";

$query="select * from login where `username`='$user' ";
$query1="select `username` from profile where `email`='$user' ";

$result=mysql_query($query);
$result1=mysql_query($query1);

if($result&&$result1){

	$temp["success"]="true";

if(mysql_num_rows($result)<=0)
{
	$temp["exists_username"]="false";

	if(mysql_num_rows($result1)<=0)
			$temp["exists_email"]="false";
		else
		{
			$temp["exists_email"]="true";
			$row = mysql_fetch_array($result1, MYSQL_ASSOC);
			$username=$row["username"];

			$subject="Startups Club password recovery assistance";
			$header="From:developers.scdd@startupsclub.org";

			$msg="Dear ".$username."\n\n\t\tYou just requested for a new password.\n\t\tYour verification code is \t ".$code."\n\t\tPlease enter this verification code when asked.\n\nTeam Startups Club";

			$temp["msg_sent"]=$msg;
			$temp["email_id"]=$user;
			$temp["username"]=$username;

			$temp["email_sent"]=mail($user, $subject, $msg ,$header);

		}

}
else{
$temp["exists_username"]="true";

$query="select * from profile where `username`='$user' ";
$result=mysql_query($query);

$row = mysql_fetch_array($result, MYSQL_ASSOC);

$to=$row["email"];

$subject="Startups Club password recovery assistance";
$header="From:developers.scdd@startupsclub.org";

$msg="Dear ".$user."\n\n\t\tYou just requested for a new password.\n\t\tYour verification code is \t ".$code."\n\t\tPlease enter this verification code when asked.\n\nTeam Startups Club";
$temp["msg_sent"]=$msg;
$temp["email_id"]=$to;
$temp["username"]=$user;

	$temp["email_sent"]=mail($to, $subject, $msg ,$header);
}
}
else
$temp["success"]="false";


 array_push($forgotpass["reply"], $temp);

echo json_encode($forgotpass);

mysql_close();
exit();
?>


