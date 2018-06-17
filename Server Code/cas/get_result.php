<?php

//Getting parameters
$studentId=$_POST['student_id'];
$subjectId=$_POST['subject_id'];

require_once('connect.php');

$sql="select result_type,marks,max_marks from result where student_id='$studentId' and subject='$subjectId';";

$response=array();
$response["result_list"]=array();
 
$result=mysqli_query($conn,$sql);

while($row=mysqli_fetch_array($result))
{
$tmp=array();
$tmp["test_type"] = $row["result_type"];
$tmp["marks"] = $row["marks"];
$tmp["max_marks"] = $row["max_marks"];
// push category to final json array
array_push($response["result_list"], $tmp);

}

echo json_encode($response);

mysqli_close($conn);
?> 