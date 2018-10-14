<?php
 if (isset($_FILES["uploaded_file"]["name"])) {

$name = $_FILES["uploaded_file"]["name"];
$tmp_name = $_FILES['uploaded_file']['tmp_name'];
$error = $_FILES['uploaded_file']['error'];

if (!empty($name)) {
    $location = './upload/';

 if ( ! is_dir($location)) {
 mkdir($location);
 }

    if  (move_uploaded_file($tmp_name, $location.$name)){
        echo 'Uploaded';
    }

} else {
    echo 'please choose a file';
}
}
 ?>