<?php


require_once 'dbConnection.php';
   
// Get date and time variables
    date_default_timezone_set('Asia/Beirut');  // for other timezones, refer:- https://www.php.net/manual/en/timezones.asia.php
    $d = date("Y-m-d");
    $t = date("H:i:s");
    
// If values send by NodeMCU are not empty then insert into MySQL database table

  if(!empty($_POST['sendval']))
    {
		$val = $_POST['sendval'];
                


// Update your tablename here
	        $sql = "UPDATE led SET status= '$stat', date= '$d', time= '$t'"; 
 
		if ($conn->query($sql) === TRUE) {
		    echo "Values updated in MySQL database table.";
		} else {
		    echo "Error: " . $sql . "<br>" . $conn->error;
		}
	}


// Close MySQL connection
$conn->close();



?>
