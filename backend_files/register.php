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
$email=$_POST["email"];
$phone=$_POST["mobile"];
$fn=$_POST["fn"];
$ln=$_POST["ln"];

$register["register"] = array();
$temp=array();


$query="select * from login where `username`='$user' ";
$result=mysql_query($query);

if(result){
if(mysql_num_rows($result)<=0){

$quer1="insert into login values ('$user' , '$pass' ) ";

$quer2="insert into profile (`username`,`email`,`first_name`,`last_name`,`mobile`) values ('$user','$email' , '$fn' ,'$ln' , '$phone')  ";

$result1=mysql_query($quer1);
$result2=mysql_query($quer2);

if($result1 && $result2){
$temp["status"]="1";				//success	

//sending email

$to=$email;

$subject="Startups Club Team";

$header="From:developers.scdd@startupsclub.org";

$msg="Dear ".$user."\n\n\t\tThank You for registering with us.\n\t\tWe hope to see you soon.\n\nTeam Startups Club\nSCDD Team 4";

$msg=wordwrap($msg,70);

$reply= mail($to, $subject, $msg , $header);

$temp["reply"]=$reply ;

}			
else{
$temp["status"]="3.1";				//server error
$temp["query2"]=$quer2;
}              
}
else 
$temp["status"]="2";				//username exists
}

else{
$temp["status"]="3.2";               //server error

$temp["query"]=$query;
}
array_push($register["register"], $temp);
echo json_encode($register);

mysql_close();
exit();
?>


