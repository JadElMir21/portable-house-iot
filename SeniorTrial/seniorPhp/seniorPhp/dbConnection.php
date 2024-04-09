<?php 
    
    $host = "localhost";		         // host = localhost because database hosted on the same server where PHP files are hosted
    $dbname = "id21997559_seniorprojectdb";              // Database name
    $username = "id21997559_db";		// Database username
    $password = "Senior123#";	        // Database password


// Establish connection to MySQL database
$conn = new mysqli($host, $username, $password, $dbname);


// Check if connection established successfully
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

else { echo "Connected to mysql database. <br>"; }

?>