<?php
//Getting values 
$studentId = $_POST['student_id'];
		
//importing Connect.php script 
require_once('connect.php');

$sql = "SELECT DISTINCT subject FROM result WHERE student_id='$studentId'";

$response=array();
$response["subject_list"]=array();
 
$result=mysqli_query($conn,$sql);

while($row=mysqli_fetch_array($result))
{
$tmp=array();
$tmp["subject_id"] = $row["subject"];
// push category to final json array
array_push($response["subject_list"], $tmp);
}

echo json_encode($response);

mysqli_close($conn);
?> 