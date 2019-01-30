<?php
//Define your host here.
$hostname = "localhost";
//Define your database username here.
$username = "dbuser";
//Define your database password here.
$password = "dbpass";
//Define your database name here.
$dbname = "dbname";

$con = mysqli_connect($hostname,$username,$password,$dbname);
 
$username = $_POST['username'];
$password = $_POST['password'];

$sql = "select * from rest_users where username='$username' and password='$password'";

$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);

if(isset($check)){

echo 'success';

}
else{

echo 'failure';

}
 
mysqli_close($con);

?>