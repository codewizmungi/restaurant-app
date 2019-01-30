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
 
date_default_timezone_set("Africa/Lagos");

 $order_items = $_POST['order_items'];
 $order_amount = $_POST['order_amount'];
 $person_name = $_POST['personName'];
 $person_contact = $_POST['personPhone'];
 $person_address = $_POST['personAddress'];
 $person_summary = $_POST['personSummary'];
 $order_time = date("Y-m-d h:i:sa");
 $order_status = "pending";
 
 
 $Sql_Query = "insert into rest_orders (order_id,order_items,order_amount,person_name,person_contact,seat_number,order_summary,
 order_time,order_confirm) values ('','$order_items','$order_amount','$person_name','$person_contact',
 '$person_address','$person_summary','$order_time','$order_status')";
 
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Data Inserted Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>