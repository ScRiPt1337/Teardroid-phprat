<?php
	include_once 'sql.php';

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
if( $_GET["id"])
$hek = $_GET["id"];
{
    $sql = "UPDATE hack SET command = 'wait' WHERE `id` = '$hek'";
}

if ($conn->query($sql) === TRUE) {
    echo "Record updated successfully";
} else {
    echo "Error updating record: " . $conn->error;
}

$conn->close();

?>