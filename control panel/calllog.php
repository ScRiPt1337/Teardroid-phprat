<?php
    include_once 'sql.php';

function insertData($conn, $phnumber, $callduration,$callTypeStr,$time)
{

    $sql = "INSERT INTO calllogs (phnumber, callduration, callTypeStr, time) VALUES ('$phnumber' ,'$callduration', '$callTypeStr', '$time')";   

    if ($conn->query($sql) === TRUE) {
        echo "New record created successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
}

$data = json_decode(file_get_contents('php://input'), true);
$echoxa = explode(',', str_replace("]", "", str_replace("[", "", $data["data"])));
foreach ($echoxa as $item) {
    #echo $item;
    $ex = explode("-",$item);
    echo $ex[0];
    echo " ";
    echo $ex[1];
    echo "\n";
    insertData($conn,$ex[0],$ex[1],$ex[2],$ex[3]);
}

$conn->close();
?>
