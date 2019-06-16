<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
	
	/*
	* The create operation
	* When this method is called a new record is created in the database
	*/
	function createPenduduk($nik_warga, $nama_warga, $jen_kel, $tmpt_lahir, $tgl_lahir, $agama, $pendidikan, $pekerjaan, $status_perkawinan, $kewarganegaraan, $provinsi, $kode_pos, $kabupaten_kota, $kecamatan, $desa_kelurahan, $rt, $rw, $alamat) {
		$stmt = $this->con->prepare("INSERT INTO penduduk (nik_warga, nama_warga, id_jen_kel, tmpt_lahir, tgl_lahir, id_agama, id_pendidikan, pekerjaan, id_status_perkawinan, id_kewarganegaraan, provinsi, kode_pos, kabupaten_kota, kecamatan, desa_kelurahan, rt, rw, alamat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("isissiisiisisssiis", $nik_warga, $nama_warga, $jen_kel, $tmpt_lahir, $tgl_lahir, $agama, $pendidikan, $pekerjaan, $status_perkawinan, $kewarganegaraan, $provinsi, $kode_pos, $kabupaten_kota, $kecamatan, $desa_kelurahan, $rt, $rw, $alamat);
		if($stmt->execute())
			return true;
		return false;
	}

	/*
	* The read operation
	* When this method is called it is returning all the existing record of the database
	*/

	function getPenduduk($nik_warga){
		$stmt = $this->con->prepare("SELECT * FROM penduduk WHERE nik_warga = ?");
		$stmt->bind_param('i', $nik_warga);
		$stmt->execute();
		$stmt->bind_result($nik_warga, $nama_warga, $jen_kel, $tmpt_lahir, $tgl_lahir, $agama, $pendidikan, $pekerjaan, $status_perkawinan, $kewarganegaraan, $provinsi, $kode_pos, $kabupaten_kota, $kecamatan, $desa_kelurahan, $rt, $rw, $alamat);

		$penduduk = array();

		while ($stmt->fetch()) {
			# code...
			$penduduks = array();
			$penduduks['nik_warga'] = $nik_warga;
			$penduduks['nama_warga'] = $nama_warga;
			$penduduks['jen_kel'] = $jen_kel;
			$penduduks['tmpt_lahir'] = $tmpt_lahir;
			$penduduks['tgl_lahir'] = $tgl_lahir;
			$penduduks['agama'] = $agama;
			$penduduks['pendidikan'] = $pendidikan;
			$penduduks['pekerjaan'] = $pekerjaan;
			$penduduks['status_perkawinan'] = $status_perkawinan;
			$penduduks['kewarganegaraan'] = $kewarganegaraan;
			$penduduks['provinsi'] = $provinsi;
			$penduduks['kode_pos'] = $kode_pos;
			$penduduks['kabupaten_kota'] = $kabupaten_kota;
			$penduduks['kecamatan'] = $kecamatan;
			$penduduks['desa_kelurahan'] = $desa_kelurahan;
			$penduduks['rt'] = $rt;
			$penduduks['rw'] = $rw;
			$penduduks['alamat'] = $alamat;

			array_push($penduduk, $penduduks);
		}
		return $penduduk;
	}

	function ambilPenduduk(){
		$stmt = $this->con->prepare("SELECT penduduk.nik_warga, penduduk.nama_warga,
		ref_jen_kel.jen_kel, penduduk.desa_kelurahan FROM penduduk INNER JOIN ref_jen_kel 
		ON penduduk.id_jen_kel = ref_jen_kel.id_jen_kel 
		ORDER BY penduduk.nama_warga ASC");
		$stmt->execute();
		$stmt->bind_result($nik_warga, $nama_warga, $jen_kel, $desa_kelurahan);

		$penduduk = array();

		while ($stmt->fetch()) {
			# code...
			$penduduks = array();
			$penduduks['nik_warga'] = $nik_warga;
			$penduduks['nama_warga'] = $nama_warga;
			$penduduks['jen_kel'] = $jen_kel;
			$penduduks['desa_kelurahan'] = $desa_kelurahan;

			array_push($penduduk, $penduduks);
		}
		return $penduduk;
	}


	function ambilDetailPenduduk($nik_warga){
		$stmt = $this->con->prepare("SELECT 
			penduduk.nik_warga, 
			penduduk.nama_warga, 
			ref_jen_kel.jen_kel,
			penduduk.tmpt_lahir,
			penduduk.tgl_lahir,
			ref_agama.agama,
			ref_pendidikan.pendidikan,
			penduduk.pekerjaan,
			ref_status_perkawinan.status_perkawinan,
			ref_kewarganegaraan.kewarganegaraan,
			penduduk.provinsi,
			penduduk.kode_pos,
			penduduk.kabupaten_kota,
			penduduk.kecamatan,
			penduduk.desa_kelurahan,
			penduduk.rt,
			penduduk.rw,
			penduduk.alamat

			FROM penduduk 

			INNER JOIN ref_jen_kel ON ref_jen_kel.id_jen_kel = penduduk.id_jen_kel
			INNER JOIN ref_agama ON ref_agama.id_agama = penduduk.id_agama
			INNER JOIN ref_pendidikan ON ref_pendidikan.id_pendidikan = penduduk.id_pendidikan
			INNER JOIN ref_status_perkawinan ON ref_status_perkawinan.id_status_perkawinan = penduduk.id_status_perkawinan
			INNER JOIN ref_kewarganegaraan ON ref_kewarganegaraan.id_kewarganegaraan = penduduk.id_kewarganegaraan
			WHERE penduduk.nik_warga = ? ");
		
		$stmt->bind_param('i', $nik_warga);
		if($stmt->execute())

		$stmt->bind_result($nik_warga, $nama_warga, $jen_kel, $tmpt_lahir, $tgl_lahir, $agama, $pendidikan, $pekerjaan, $status_perkawinan, $kewarganegaraan, $provinsi, $kode_pos, $kabupaten_kota, $kecamatan, $desa_kelurahan, $rt, $rw, $alamat);

		$penduduk = array();

		while ($stmt->fetch()) {
			# code...
			$penduduks = array();
			$penduduks['nik_warga'] = $nik_warga;
			$penduduks['nama_warga'] = $nama_warga;
			$penduduks['jen_kel'] = $jen_kel;
			$penduduks['tmpt_lahir'] = $tmpt_lahir;
			$penduduks['tgl_lahir'] = $tgl_lahir;
			$penduduks['agama'] = $agama;
			$penduduks['pendidikan'] = $pendidikan;
			$penduduks['pekerjaan'] = $pekerjaan;
			$penduduks['status_perkawinan'] = $status_perkawinan;
			$penduduks['kewarganegaraan'] = $kewarganegaraan;
			$penduduks['provinsi'] = $provinsi;
			$penduduks['kode_pos'] = $kode_pos;
			$penduduks['kabupaten_kota'] = $kabupaten_kota;
			$penduduks['kecamatan'] = $kecamatan;
			$penduduks['desa_kelurahan'] = $desa_kelurahan;
			$penduduks['rt'] = $rt;
			$penduduks['rw'] = $rw;
			$penduduks['alamat'] = $alamat;

			array_push($penduduk, $penduduks);
			
			return $penduduk;

			return false;
			}
	}

	/*
	* The update operation
	* When this method is called the record with the given id is updated with the new given values
	*/

	function updatePenduduk($nik_warga, $nama_warga, $jen_kel, $tmpt_lahir, $tgl_lahir, $agama, $pendidikan, $pekerjaan, $status_perkawinan, $kewarganegaraan, $provinsi, $kode_pos, $kabupaten_kota, $kecamatan, $desa_kelurahan, $rt, $rw, $alamat){
		$stmt = $this->con->prepare("UPDATE penduduk SET nama_warga = ?, id_jen_kel = ?, tmpt_lahir = ?, tgl_lahir = ?, id_agama = ?, id_pendidikan = ?, pekerjaan = ?, id_status_perkawinan = ?, id_kewarganegaraan = ?, provinsi = ?, kode_pos = ?, kabupaten_kota = ?, kecamatan = ?, desa_kelurahan = ?, rt = ?, rw = ?, alamat = ? WHERE nik_warga = ?");
		$stmt->bind_param("sissiisississsiisi", $nama_warga, $jen_kel, $tmpt_lahir, $tgl_lahir, $agama, $pendidikan, $pekerjaan, $status_perkawinan, $kewarganegaraan, $provinsi, $kode_pos, $kabupaten_kota, $kecamatan, $desa_kelurahan, $rt, $rw, $alamat, $nik_warga);
		if($stmt->execute())
			return true;
		return false;
	}

	/*
	* The delete operation
	* When this method is called record is deleted for the given id 
	*/
	
	function deletePenduduk($nik){
		$stmt = $this->con->prepare("DELETE FROM penduduk WHERE nik_warga = ?");
		$stmt->bind_param("i", $nik);
		if($stmt->execute())
			return true;
		return false;
	}


	//fungsi listview agama
	function ambilAgama(){
		$stmt = $this->con->prepare("SELECT id_agama, agama FROM ref_agama");
		$stmt->execute();
		$stmt->bind_result($id_agama, $agama);

		$hasil = array();

		while ($stmt->fetch()) {
			# code...
			$hasils = array();
			$hasils['id_agama'] = $id_agama;
			$hasils['agama'] = $agama;

			array_push($hasil, $hasils);
		}
		return $hasil;
	}

	//fungsi listview pendidikan
	function ambilPendidikan(){
		$stmt = $this->con->prepare("SELECT id_pendidikan, pendidikan FROM ref_pendidikan");
		$stmt->execute();
		$stmt->bind_result($id_pendidikan, $pendidikan);

		$hasil = array();

		while ($stmt->fetch()) {
			# code...
			$hasils = array();
			$hasils['id_pendidikan'] = $id_pendidikan;
			$hasils['pendidikan'] = $pendidikan;

			array_push($hasil, $hasils);
		}
		return $hasil;
	}

	//fungsi listview status perkawinan
	function ambilStatusPerkawinan(){
		$stmt = $this->con->prepare("SELECT id_status_perkawinan, status_perkawinan FROM ref_status_perkawinan");
		$stmt->execute();
		$stmt->bind_result($id_status_perkawinan, $status_perkawinan);

		$hasil = array();

		while ($stmt->fetch()) {
			# code...
			$hasils = array();
			$hasils['id_status_perkawinan'] = $id_status_perkawinan;
			$hasils['status_perkawinan'] = $status_perkawinan;

			array_push($hasil, $hasils);
		}
		return $hasil;
	}

	//fungsi listview kewarganegaraan
	function ambilKewarganegaraan(){
		$stmt = $this->con->prepare("SELECT id_kewarganegaraan, kewarganegaraan FROM ref_kewarganegaraan");
		$stmt->execute();
		$stmt->bind_result($id_kewarganegaraan, $kewarganegaraan);

		$hasil = array();

		while ($stmt->fetch()) {
			# code...
			$hasils = array();
			$hasils['id_kewarganegaraan'] = $id_kewarganegaraan;
			$hasils['kewarganegaraan'] = $kewarganegaraan;

			array_push($hasil, $hasils);
		}
		return $hasil;
	}
}