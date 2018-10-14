<?php
	include_once 'sql.php';

if( $_GET["id"] || $_GET["name"] || $_GET["phonenum"])
$hek = $_GET["id"];
$NAME = $_GET["name"];
$phonenum = $_GET["phonenum"];



$sql = "INSERT INTO getContactList (name, phonenum) VALUES ('$NAME' ,'$phonenum')";   


        if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>