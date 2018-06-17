<? php
require_once('connect.php');

$sql="select student_id from student_info;";

echo $sql;
echo "vaibhav";
$result=mysqli_query($conn, $sql);
$response=array();

while($row = mysqli_fetch_array($result))
{
array_push($response, array("name" => $row[0]));
}
echo json_encode(array("student_list"=>$response));
mysqli_close($conn);

?>