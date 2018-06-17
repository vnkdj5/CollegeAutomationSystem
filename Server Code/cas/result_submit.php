<?php

$resultType = $_GET['result_type'];
$maxMarks = $_GET['max_marks'];
$subjectId = $_GET['subject_id'];
require_once('connect.php');

$json = file_get_contents('php://input');
$obj = json_decode($json, true);
//echo $json;

$st = mysqli_prepare($conn, 'INSERT INTO result(student_id, marks,max_marks, subject,result_type) VALUES (?, ?, ?,?,?)');

// bind variables to insert query params
mysqli_stmt_bind_param($st, 'siiss', $student_id, $mark, $max_marks, $subject, $result_type);
foreach ($obj as $row) {
    $student_id = $row['student_id'];
    $mark = $row['marks'];
    $max_marks = $maxMarks;
    $subject = $subjectId;
    $result_type = $resultType;

    // execute insert query
    mysqli_stmt_execute($st);
}

//echo "Result Submitted Successfully.";

