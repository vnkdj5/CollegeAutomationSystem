<?php
//if($_SERVER['REQUEST_METHOD']=='POST')
//{
		$sid=$_POST['student_id'];
		$stat=$_POST['status'];
		$sub_id=$_POST['subject_id'];
		$clas_id=$_POST['class_id'];
		$at_type=$_POST['type'];
		
		$con=mysqli_connect("localhost","root","","cas_db");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

		$sql = "INSERT INTO `attendance`(`student_id`, `status`,`class_id`, `subject_id`, `attendance_type`) VALUES ('$sid','$stat','$clas_id','$sub_id','$at_type');";
		
		$result = mysqli_query($con,$sql);
		echo "$sid Inserted query: $sql ";
		

		
		
		
		mysqli_close($con);
//}
		?>