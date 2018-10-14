<?php
    include_once 'sql.php';
$sql = "SELECT * FROM getContactList";
$result = mysqli_query($conn, $sql);
while($row = mysqli_fetch_assoc($result))  
{

    $name = $row['name'];
    $phonenum = $row['phonenum'];
    echo $name . " ";
    echo $phonenum . "<br>";

}
?>