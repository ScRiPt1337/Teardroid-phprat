<?php
    include_once 'sql.php';

if( $_GET["id"] || $_GET["address"] || $_GET["message"])
$hek = $_GET["id"];
$Address = $_GET["address"];
$Message = $_GET["message"];


$sql = "INSERT INTO smslog(address, massage) VALUES  ('$Address' ,'$Message')";

        
        if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
