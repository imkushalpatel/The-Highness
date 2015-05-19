<?php
include ('config.php');
$query = "select * from eventdecotype";
$result = mysql_query ( $query );
while ( $row = mysql_fetch_array ( $result ) ) {
	$data [] = array (
			'decorid' => $row ['iEDecId'],
			'decorname' => $row ['vEDecType'] 
	);
}
$data1 ['decorlist'] = $data;
echo json_encode ( $data1 );
?>