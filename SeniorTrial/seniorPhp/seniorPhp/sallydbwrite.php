<?php 
require_once 'dbConnection.php';

// Get date and time variables
date_default_timezone_set('Asia/Beirut');  // for other timezones, refer:- https://www.php.net/manual/en/timezones.asia.php
    $d = date("Y-m-d");
    $t = date("H:i:s");
    
// If values send by NodeMCU are not empty then insert into MySQL database table

  if(!empty($_GET['sendval']) && !empty($_GET['sendval2']) )
    {
		$val = $_GET['sendval'];
                $val2 = $_GET['sendval2'];


// Update your tablename here

	        $sql = "INSERT INTO nodemcu(val, val2, Date, Time) VALUES('$val','$val2', '$d', '$t')"; 
 


		if ($conn->query($sql) === TRUE) {
		    echo "Values inserted in MySQL database table.";
		} else {
		    echo "Error: " . $sql . "<br>" . $conn->error;
		}
	}


// Close MySQL connection
$conn->close();



?>