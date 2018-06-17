<?php

//Getting values 
$teacherId = $_POST['teacher_id'];

//importing Connect.php script 
require_once('connect.php');

$sql = "SELECT DISTINCT class_id,TEACHER_ID FROM RELATION_INFO WHERE teacher_id='$teacherId'";


$response = array();
$response["student_list"] = array();

$result = mysqli_query($conn, $sql);


while ($row = mysqli_fetch_array($result)) {
    $tmp = array();
    $tmp["class_id"] = $row["class_id"];

    // push category to final json array
    array_push($response["student_list"], $tmp);
}

echo json_encode($response);

mysqli_close($conn);
?> 