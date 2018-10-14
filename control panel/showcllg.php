<?php
    include_once 'sql.php';
$sql = "SELECT * FROM calllogs";
$result = mysqli_query($conn, $sql);
while($row = mysqli_fetch_assoc($result))  
{

    $phnumber = $row['phnumber'];
    $callduration = $row['callduration'];
    $callTypeStr = $row['callTypeStr'];
    $time = $row['time'];
    echo $phnumber . " ";
    echo $time . "<br>";

}
?>