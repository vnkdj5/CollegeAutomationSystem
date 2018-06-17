<?php

$clas_id = $_GET['class_id'];
$teacherId = $_GET['teacher_id'];
$con = mysqli_connect("localhost", "root", "root", "cas_db");
// Check connection
if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
//$sql="SELECT student_id,student_name FROM student_info";
$sql = "SELECT student_id,student_name FROM student_info where class_id=(select class_id from relation_info where class_id='$clas_id' and teacher_id='$teacherId') "; // and subject_id=\'AJAVA\'
$response = array();

$result = mysqli_query($con, $sql);

while ($row = mysqli_fetch_array($result)) {
    $tmp = array();
    $tmp["student_id"] = $row["student_id"];

    // push category to final json array
    array_push($response, $tmp);
}

echo json_encode($response);

mysqli_close($con);
?> 
