<?php

//if($_SERVER['REQUEST_METHOD']=='POST')
{

    //Getting values 
    $username = $_POST['username'];
    $password = $_POST['password'];

    //importing Connect.php script 
    require_once('connect.php');
    //Creating sql query
    $sql = "SELECT * FROM student_info WHERE student_id='$username' AND student_pass='$password'";
    $sql1 = "SELECT * FROM teacher_info WHERE teacher_id='$username' AND teacher_pass='$password'";
    //executing query
    $result = mysqli_query($conn, $sql);
    $result1 = mysqli_query($conn, $sql1);


    //fetching result
    $check = mysqli_fetch_array($result);

    $check1 = mysqli_fetch_array($result1);
    //if we got some result 
    if (isset($check)) {
        //displaying success 
        echo 'success';
    } else if (isset($check1)) {
        echo "teacher_success";
    } else {
        //displaying failure
        echo "failure";
    }

    mysqli_close($conn);
}
?>