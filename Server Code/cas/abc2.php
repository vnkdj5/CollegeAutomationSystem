<html>
<head></head>
<body>
<?php
 require_once('connect.php');
 
 $sql="select student_id from student_info;";
 
 echo $sql;
 echo "vaibhav";
 $result=mysqli_query($conn, $sql);
 $response=array(); ?>
 <table>
   <thead>
      <th>Student_ID</th>
      <th>Marks</th>
   </thead>
   <tbody>
   <?php
   if(isset($_POST['submit'])) 
	{
				
      while($row = mysql_fetch_array($result))
	  {
		  $marks=$_POST['marks'];
         echo "<tr><td>".$tmp['student_id']."</td><td>$marks</td></tr>";
	  }
	}
         //echo "<tr>$marks</tr>";
   ?>
   </tbody>
</table>