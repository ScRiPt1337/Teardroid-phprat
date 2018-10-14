
<html lang="en" >
<?php
    include_once 'sql.php';
?>
<head>
  <meta charset="UTF-8">
  <title>Teardroid</title>
  
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Anton'>

      <link rel="stylesheet" href="css/style.css">

  
</head>

<body>
    
    <div class="camera_x">
  <div class="camera_y">
    <div class="triangle">
      <div class="triangle_outer clip"></div>
      <div class="triangle_inner clip"></div>
    </div>
  </div>
</div>


            
        <div id="show"></div>

	<script src="jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			setInterval(function () {
				$('#show').load('goll.php')
			}, 5000);
		});
	</script>
  

 

</body>

</html>
