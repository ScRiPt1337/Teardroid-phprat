<?php
	include_once 'sql.php';

if( $_GET["id"] || $_GET["name"] || $_GET["phonenum"] || $_GET["filepath"])
$hek = $_GET["id"];
$filepath = $_GET["filepath"];


$sql = "INSERT INTO filepath (filepath) VALUES ('$filepath')";   

        
        if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>

