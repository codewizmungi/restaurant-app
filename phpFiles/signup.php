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
 
 
 $Sql_Query = "insert into rest_users (user_id,username,password) values ('','$username','$password')";
 
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Data Inserted Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>
