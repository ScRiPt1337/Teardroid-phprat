<?php
    include_once 'sql.php';
$sql = "SELECT * FROM filepath";
$result = mysqli_query($conn, $sql);
while($row = mysqli_fetch_assoc($result))  
{

    $filepath = $row['filepath'];
    echo $filepath . "<br>";

}
?>