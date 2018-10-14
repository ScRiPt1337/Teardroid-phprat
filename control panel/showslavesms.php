<?php
    include_once 'sql.php';
$sql = "SELECT * FROM smslog";
$result = mysqli_query($conn, $sql);
while($row = mysqli_fetch_assoc($result))  
{

    $Address = $row['address'];
    $Message = $row['massage'];
    echo $Address . " ";
    echo $Message . "<br>";

}
?>