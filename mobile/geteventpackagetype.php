<?php
include ('config.php');
$query = "select * from eventpackage";
$result = mysql_query ( $query );
while ( $row = mysql_fetch_array ( $result ) ) {
	$data [] = array (
			'pacid' => $row ['iEPackageId'],
			'pacname' => $row ['vEPackageType'] 
	);
}
$data1 ['paclist'] = $data;
echo json_encode ( $data1 );
?>