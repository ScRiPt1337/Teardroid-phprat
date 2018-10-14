<?php
	include_once 'sql.php';

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
if( $_GET["id"])
$hek = $_GET["id"];
{
    $sql = "UPDATE text SET text = 'wait'";
}

if ($conn->query($sql) === TRUE) {
    echo "Record updated successfully";
} else {
    echo "Error updating record: " . $conn->error;
}

$conn->close();

?>