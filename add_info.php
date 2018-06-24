<?php
require "init.php";

$name=$_POST["name"];
$email=$_POST["email"];
$pass=$_POST["pass"];
$servername = "localhost";
$username = "<username not here for security reasons>";
$password = "<password>";
$dbname = "<database name>";

// Create connection
$con = mysqli_connect($servername, $username, $password, $dbname);

$sql = "INSERT INTO product_info(name, email, pass)
VALUES ('$name', '$email', '$pass')";

if (mysqli_query($con, $sql))
{
	echo "<h3>Row Inserted</h3>";
}
else
{
	echo "<h3>Error in insertion</h3>" . mysqli_error($con);
}
?>
