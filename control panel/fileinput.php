<?php
	include_once 'sql.php';


function insertData($conn, $filepath)
{
    $sql = "INSERT INTO filepath (filepath) VALUES ('$filepath')";   

        
    if ($conn->query($sql) === TRUE) {
        echo "New record created successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
}

$data = json_decode(file_get_contents('php://input'), true);
#echo $data['data'];
$echoxa = explode(',', str_replace("]", "", str_replace("[", "", $data["data"])));
foreach ($echoxa as $item) {
    echo $item;
    echo "\n";
    insertData($conn,$item);
}

$conn->close();
?>

