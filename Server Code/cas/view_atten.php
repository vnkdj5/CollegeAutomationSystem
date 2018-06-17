<?php

//importing Connect.php script 
require_once('connect.php');

$sql = "SELECT subject_id from relation_info where class_id=(select class_id from student_info where student_id='1306068')";
$result = mysqli_query($conn, $sql);
$num = mysqli_num_rows($result);
$response = array();
$response["attendance"] = array();
$tmp = array();
while ($row = mysqli_fetch_array($result)) {
    $tmp["subject_id"] = $row[0];

	
	
    $sql1 = "select status from attendance where student_id='1306068' and status='1' and attendance_type='Practical' and subject_id='$row[0]'";
    $result1 = mysqli_query($conn, $sql1);
    $num1 = mysqli_num_rows($result1);
    $tmp["PP"] = $num1 . "";

    $sql1 = "select status from attendance where student_id='1306068' and status='0' and attendance_type='Practical' and subject_id='$row[0]'";
    $result1 = mysqli_query($conn, $sql1);
    $num1 = mysqli_num_rows($result1);
    $tmp["PA"] = $num1 . "";

    $sql1 = "select status from attendance where student_id='1306068' and status='1' and attendance_type='LECTURE' and subject_id='$row[0]'";
    $result1 = mysqli_query($conn, $sql1);
    $num1 = mysqli_num_rows($result1);
    $tmp["LP"] = $num1 . "";

    $sql1 = "select status from attendance where student_id='1306068' and status='0' and attendance_type='LECTURE' and subject_id='$row[0]'";
    $result1 = mysqli_query($conn, $sql1);
    $num1 = mysqli_num_rows($result1);
    $tmp["LA"] = $num1 . "";

    //Calculating percentage

    $perc = (($tmp["LP"] + $tmp["PP"]) / ($tmp["LP"] + $tmp["PP"] + $tmp["LA"] + $tmp["PA"])) * 100;

    $tmp["perc"] = round($perc) . "";

    array_push($response["attendance"], $tmp);
}
echo json_encode($response);
?>