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

	 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            the first request id will be zero.*/	
	$cat = $_GET["category"];
	
	$sql= "Select * from rest_menu where food_category = '$cat' ";
	
	$result = mysqli_query($con ,$sql);
	
	while ($row = mysqli_fetch_assoc($result)) {
		
		$array[] = $row;
		
	}
	header('Content-Type:Application/json');
	
	echo json_encode($array);
 
    mysqli_free_result($result);
 
    mysqli_close($con);
  
 ?>