<?php
$servername = "localhost";
$username = "<username not here for security reason";
$password = "<password>";
$dbname = "<database name>";

// Create connection
$con = mysqli_connect($servername, $username, $password, $dbname);

//if there is some error connecting to the database
//with die we will stop the further execution by displaying a message causing the error 
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
 
//if everything is fine
 
//creating an array for storing the data 
$product_info = array(); 
 
//this is our sql query 
$sql = "SELECT name, email, pass FROM product_info;";
 
//creating an statment with the query
$stmt = $con->prepare($sql);
 
//executing that statment
$stmt->execute();
 
//binding results for that statment 
$stmt->bind_result($name, $email, $pass);
 
//looping through all the records
while($stmt->fetch()){
 
 //pushing fetched data in an array 
 $temp = [
 'name'=>$name,
 'email'=>$email,
 'pass'=>$pass,
 ];
 
 //pushing the array inside the hero array 
 array_push($product_info, $temp);
}
 
//displaying the data in json format 
echo json_encode($product_info);
