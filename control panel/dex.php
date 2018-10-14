
<html lang="en" >
<?php
    include_once 'sql.php';
?>
<head>
  <meta charset="UTF-8">
  <title>Teardroid</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  
      <link rel="stylesheet" href="xss/style.css">

  
</head>

<body>

  <body id="particles-js">
  <style id="cmd-css">
  </style>
<div class='console'>
  <div class='console-inner'>
    <div id="outputs">
    	<?php
$sql = "SELECT * FROM $hek";
$result = mysqli_query($conn, $sql);
while($row = mysqli_fetch_assoc($result))  
{

    $Address = $row['Address'];
    $Message = $row['Message'];
    echo $Address . " ";
    echo $Message . "<br>";

}
?>
    </div>

  </div>
</div>
</body>
  
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js'></script>
<script src='http://s.codepen.io/assets/libs/modernizr.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/particles.js/2.0.0/particles.min.js'></script>

  

    <script  src="ss/index.js"></script>




</body>

</html>
