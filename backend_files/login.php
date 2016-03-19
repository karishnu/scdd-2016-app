<?php

$mysql_host = "my*****";
$mysql_database = "a36280*****";
$mysql_user = "a36280*****";
$mysql_password = "proj*****";

$connect = @mysql_connect($mysql_host, $mysql_user, $mysql_password)or die(mysql_error());
$db = @mysql_select_db($mysql_database,$connect)or die(mysql_error());  
 

$user=$_POST["username"];
$pass=$_POST["password"];


$query="select * from login where `username`='$user' ";


$result=mysql_query($query);

if(result){
if(mysql_num_rows($result)<=0)
echo "-1";                       //Username doesn't exists
else
{
$query="select * from login where `username`='$user' and `password`='$pass' ";
$result=mysql_query($query);

if($result)
{
if(mysql_num_rows($result)<=0)
echo "0";                      //Invalid Password
else
echo "1"	;					//Successful login
}
}
}
else
echo "-2";                //server error

mysql_close();
exit();

?>