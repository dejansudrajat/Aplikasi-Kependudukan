package com.kepependudukan.api;

public class API {


    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private static final String ROOT_URL = "http://127.0.0.1:8080/android/penduduk/v1/Api.php?apicall=";

    public static final String URL_SEARCH = ROOT_URL + "search&query=";
    public static final String URL_CREATE_PENDUDUK = ROOT_URL + "creatependuduk";
    public static final String URL_READ_PENDUDUK = ROOT_URL + "ambilpenduduk";
    public static final String URL_GET_PENDUDUK = ROOT_URL + "getpenduduk&nik=";
    public static final String URL_UPDATE_PENDUDUK = ROOT_URL + "updatependuduk";
    public static final String URL_DELETE_PENDUDUK = ROOT_URL + "deletependuduk&nik=";
    public static final String URL_READ_DET_PENDUDUK = ROOT_URL + "ambildetailpenduduk&nik=";

    //parameter untuk isi listview

    public static final String url_agama = "http://127.0.0.1:8080/android/penduduk/v1/API.php?apicall=ambilagama";
    public static final String url_pendidikan = "http://127.0.0.1:8080/android/penduduk/v1/API.php?apicall=ambilpendidikan";
    public static final String url_status_perkawinan = "http://127.0.0.1:8080/android/penduduk/v1/API.php?apicall=ambilstatusperkawinan";
    public static final String url_kewarganegaraan = "http://127.0.0.1:8080/android/penduduk/v1/API.php?apicall=ambilkewarganegaraan";

    public static final String TAG_ID_AGAMA = "id_agama";
    public static final String TAG_ID_PENDIDIKAN = "id_pendidikan";
    public static final String TAG_ID_STATUS_PERKAWINAN = "id_status_perkawinan";
    public static final String TAG_ID_KEWARGANEGARAAN = "id_kewarganegaraan";

    public static final String TAG_AGAMA = "agama";
    public static final String TAG_PENDIDIKAN = "pendidikan";
    public static final String TAG_STATUS_PERKAWINAN = "status_perkawinan";
    public static final String TAG_KEWARGANEGARAAN = "kewarganegaraan";


}
