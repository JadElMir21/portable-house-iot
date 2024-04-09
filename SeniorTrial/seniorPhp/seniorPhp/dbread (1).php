<?php

require_once 'dbConnection.php';



// Select values from MySQL database table

$sql = "SELECT id, val, val2, date, time FROM nodemcu";  // Update your tablename here

$result = $conn->query($sql);

echo "<center>";



if ($result->num_rows > 0) {


    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo "<strong> Id:</strong> " . $row["id"]. " &nbsp <strong>val:</strong> " . $row["val"]. " &nbsp <strong>val2:</strong> " . $row["val2"]. " &nbsp <strong>Date:</strong> " . $row["date"]." &nbsp <strong>Time:</strong>" .$row["time"]. "<p>";
    


}
} else {
    echo "0 results";
}

echo "</center>";

$conn->close();



?>
