<?php

$con = mysqli_connect("localhost", "root", "", "cas_db");
// Check connection
if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$sql = "SELECT student_id,student_name FROM student_info ";
$response = array();
$response["timetable"] = array();
$result = mysqli_query($con, $sql);


while ($row = mysqli_fetch_array($result)) {
    $tmp = array();
    $tmp["time"] = $row["Time"];

    // push category to final json array
    array_push($response["student_list"], $tmp);
}

echo json_encode($response);

mysqli_close($con);
?> 