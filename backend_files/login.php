<?php
$ftp_server = "myserver2015.bugs3.com";

// set up a connection or die
//$conn_id = ftp_connect($ftp_server) or die($ftp_server);
$mysql_host = "mysql7.000webhost.com";
$mysql_database = "a3628048_scdd";
$mysql_user = "a3628048_scdd";
$mysql_password = "project-android-scdd2016";

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