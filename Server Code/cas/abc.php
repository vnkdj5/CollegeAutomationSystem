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
 <?php     // if form not yet submitted     // display form   
  if (!isset($_POST['submit'])) 
  {
	  ?> 
 <table>
 <form action="abc.php" method="POST">
 
 <?php
 while($row = mysqli_fetch_array($result))
 { 

	$tmp=array();
        $tmp["student_id"] = $row["student_id"];
	 echo "<tr><td>".$tmp["student_id"]."</td><td><input type='text' name='marks' /></td></tr>";
 } 
 echo "<input type='submit' value='Submit' />"; ?>
 
 </form>
 </table>
     <?php  
	  // if form submitted     // process form input
	  } else {
              ?>
           
    <table>
   <thead>
      <th>Student_ID</th>
      <th>Marks</th>
   </thead>
   <tbody>
   <?php
  
				
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
    } ?>  
 </body>
 </html>