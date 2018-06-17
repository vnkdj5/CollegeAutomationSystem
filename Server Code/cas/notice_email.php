<?php

//Getting parameters
$classId=$_POST['class_id'];


require_once('connect.php');

$sql="select distinct email_id from student_info where class_id='$classId';";

$response=array();
$response["email_list"]=array();
 
$result=mysqli_query($conn,$sql);

while($row=mysqli_fetch_array($result))
{
$tmp=array();
$tmp["email_id"] = $row["email_id"];
//$tmp["student_id"] = $row["student_id"];

// push category to final json array
array_push($response["email_list"], $tmp);
}

echo json_encode($response);

mysqli_close($conn);
?> 