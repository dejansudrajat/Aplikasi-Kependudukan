<?php 
	require_once '../includes/DbOperation.php';

	function isTheseParametersAvailable($params){
		//assuming all parameters are available 
		$available = true; 
		$missingparams = ""; 
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available = false; 
				$missingparams = $missingparams . ", " . $param; 
			}
		}
		
		//if parameters are missing 
		if(!$available){
			$response = array(); 
			$response['error'] = true; 
			$response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
			
			//displaying error
			echo json_encode($response);
			
			//stopping further execution
			die();
		}
	}
	
	//an array to display response
	$response = array();
	
	//if it is an api call 
	//that means a get parameter named api call is set in the URL 
	//and with this parameter we are concluding that it is an api call
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
			
			//the CREATE operation
			//if the api call value is 'createhero'
			//we will create a record in the database

			case 'creatependuduk':
				# code...

				isTheseParametersAvailable(array('nik_warga', 'nama_warga', 'jen_kel', 'tmpt_lahir', 'tgl_lahir', 'agama', 'pendidikan', 'pekerjaan', 'status_perkawinan', 'kewarganegaraan', 'provinsi', 'kode_pos', 'kabupaten_kota', 'kecamatan', 'desa_kelurahan', 'rt', 'rw', 'alamat'));

				//create a new dboperation object
				$db = new DbOperation();

				//creating a new record in the database
				$result = $db->createPenduduk(
					$_POST['nik_warga'],
					$_POST['nama_warga'],
					$_POST['jen_kel'],
					$_POST['tmpt_lahir'],
					$_POST['tgl_lahir'],
					$_POST['agama'],
					$_POST['pendidikan'],
					$_POST['pekerjaan'],
					$_POST['status_perkawinan'],
					$_POST['kewarganegaraan'],
					$_POST['provinsi'],
					$_POST['kode_pos'],
					$_POST['kabupaten_kota'],
					$_POST['kecamatan'],
					$_POST['desa_kelurahan'],
					$_POST['rt'],
					$_POST['rw'],
					$_POST['alamat']
				);

				//if the record is created adding success to response
				if($result){
					//record is creayed means there is no error
					$response['error'] = false;

					//in message we have a success message
					$response['message'] = 'success';

					//and we are getting all the penduduk from the database in the response
					$response['penduduk'] = $db->ambilPenduduk();
				}else{
					//if record not added that means there is an error
					$response['error'] = true;

					//and we have the error message
					$response['message'] = 'failed';
				}
				
				break;

			//the READ operation
			case 'ambilpenduduk':
				# code...
				$db = new DbOperation();
				$response['error'] = false;
				$response['message'] = 'success';
				$response['penduduk'] = $db->ambilPenduduk();
			break;

			case 'getpenduduk':
				# code...
				if(isset($_GET['nik'])){
					$db = new DbOperation();
					if($db->ambilPenduduk($_GET['nik'])){
						$response['error'] = false;
						$response['message'] = 'success';
						$response['penduduk'] = $db->getPenduduk($_GET['nik']);
					} else {
						$response['error'] = false;
						$response['message'] = 'failed, NIK = '.$_GET['nik'].' Tidak ada';
					}
				} else {
					$response['error'] = true;
					$response['message'] = 'failed';
				}
			break;

			case 'ambilagama':
				# code...
				$db = new DbOperation();
				$response['error'] = false;
				$response = $db->ambilagama();
			break;

			case 'ambilpendidikan':
				# code...
				$db = new DbOperation();
				$response['error'] = false;
				$response = $db->ambilPendidikan();
			break;

			case 'ambilstatusperkawinan':
				# code...
				$db = new DbOperation();
				$response['error'] = false;
				$response = $db->ambilStatusPerkawinan();
			break;

			case 'ambilkewarganegaraan':
				# code...
				$db = new DbOperation();
				$response['error'] = false;
				$response = $db->ambilKewarganegaraan();
			break;

			case 'ambildetailpenduduk':
				# code...
				if(isset($_GET['nik'])){
					$db = new DbOperation();
					if($db->ambilDetailPenduduk($_GET['nik'])){
						$response['error'] = false;
						$response['message'] = 'success';
						$response['penduduk'] = $db->ambildetailpenduduk($_GET['nik']);
					} else {
						$response['error'] = false;
						$response['message'] = 'failed, NIK = '.$_GET['nik'].' Tidak ada';
					}
				} else {
					$response['error'] = true;
					$response['message'] = 'failed';
				}
			break;

			//the UPDATE operation
			case 'updatependuduk':
				# code...
				isTheseParametersAvailable(array('nik_warga', 'nama_warga', 'jen_kel', 'tmpt_lahir', 'tgl_lahir', 'agama', 'pendidikan', 'pekerjaan', 'status_perkawinan', 'kewarganegaraan', 'provinsi', 'kode_pos', 'kabupaten_kota', 'kecamatan', 'desa_kelurahan', 'rt', 'rw', 'alamat'));
				$db = new DbOperation();
				$result = $db->updatePenduduk(
					$_POST['nik_warga'],
					$_POST['nama_warga'],
					$_POST['jen_kel'],
					$_POST['tmpt_lahir'],
					$_POST['tgl_lahir'],
					$_POST['agama'],
					$_POST['pendidikan'],
					$_POST['pekerjaan'],
					$_POST['status_perkawinan'],
					$_POST['kewarganegaraan'],
					$_POST['provinsi'],
					$_POST['kode_pos'],
					$_POST['kabupaten_kota'],
					$_POST['kecamatan'],
					$_POST['desa_kelurahan'],
					$_POST['rt'],
					$_POST['rw'],
					$_POST['alamat']
				);

				if($result){
					$response['error'] = false;
					$response['message'] = 'success';
					$response['penduduk'] = $db->getPenduduk();
				}else{
					$response['error'] = true;
					$response['message'] = 'failed';
				}
			break;

			//the delete operation
			case 'deletependuduk':
				# code...
				//for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
				if(isset($_GET['nik'])){
					$db = new DbOperation();
					if($db->deletePenduduk($_GET['nik'])){
						$response['error'] = false;
						$response['message'] = 'success';
						$response['penduduk'] = $db->getPenduduk();
					} else {
						$response['error'] = true;
						$response['message'] = 'failed';
					}
				} else {
					$response['error'] = true;
					$response['message'] = 'failed';
				}
			break;
		}
	} else {
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	//displaying the response in json structure 
	echo json_encode($response);