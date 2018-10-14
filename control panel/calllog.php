<?php
    include_once 'sql.php';

if( $_GET["id"] || $_GET["phnumber"] || $_GET["callduration"] || $_GET["callTypeStr"] || $_GET["time"])
$hek = $_GET["id"];
$phnumber = $_GET["phnumber"];
$callduration = $_GET["callduration"];
$callTypeStr = $_GET["callTypeStr"];
$time = $_GET["time"];



$sql = "INSERT INTO calllogs (phnumber, callduration, callTypeStr, time) VALUES ('$phnumber' ,'$callduration', '$callTypeStr', '$time')";   

        
        if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
