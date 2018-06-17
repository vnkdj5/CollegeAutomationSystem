<?php

//Getting values 
$userId = $_POST['user_id'];
$day = $_POST['day'];
$userType = $_POST['user_type'];

//importing Connect.php script 
require_once('connect.php');



if ($userType === "staff") {
    $sql = "SELECT `Time`,`$day` FROM  timetable where class_id='$userId'";
}
if ($userType === "student") {
    $sql = "SELECT `Time`,`$day` FROM  timetable where class_id=(select class_id from student_info where student_id='$userId')";
}

$response = array();
$response["timetable"] = array();

$result = mysqli_query($conn, $sql);


while ($row = mysqli_fetch_array($result)) {
    $tmp = array();
    $tmp["time"] = $row["Time"];
    $tmp["period"] = $row["$day"];

    // push category to final json array
    array_push($response["timetable"], $tmp);
}






echo json_encode($response);
mysqli_close($conn);
?> 