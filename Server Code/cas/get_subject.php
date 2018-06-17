<?php

//Getting values 
$teacherId = $_POST['teacher_id'];

//importing Connect.php script 
require_once('connect.php');
$sql = "SELECT distinct subject_id,teacher_id FROM RELATION_INFO WHERE teacher_id='$teacherId'";

$response = array();
$response["subject_list"] = array();

$result = mysqli_query($conn, $sql);
while ($row = mysqli_fetch_array($result)) {
    $tmp = array();
    $tmp["subject_id"] = $row["subject_id"];

    // push category to final json array
    array_push($response["subject_list"], $tmp);
}

echo json_encode($response);

mysqli_close($conn);
?> 