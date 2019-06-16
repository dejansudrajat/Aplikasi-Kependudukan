package com.kepependudukan.model;

import java.io.Serializable;

public class ModelPenduduk {
    //private int  jen_kel, agama, pendidikan, status_perkawinan, kewarganegaraan, kode_pos, rt, rw;
    private String jen_kel, agama, pendidikan, status_perkawinan, kewarganegaraan, kode_pos, rt, rw;
    private String nik_warga, nama_warga, tmpt_lahir, tgl_lahir, pekerjaan, provinsi, kabupaten_kota, kecamatan, desa_kelurahan, alamat;

    public ModelPenduduk(){

    }
    public ModelPenduduk(String nik_warga, String nama_warga, String jen_kel, String tmpt_lahir, String tgl_lahir, String agama,
                         String pendidikan, String pekerjaan, String status_perkawinan, String kewarganegaraan, String provinsi,
                         String kode_pos, String kabupaten_kota, String kecamatan, String desa_kelurahan, String rt, String rw,
                         String alamat) {
        this.nik_warga = nik_warga;
        this.nama_warga = nama_warga;
        this.jen_kel = jen_kel;
        this.tmpt_lahir = tmpt_lahir;
        this.tgl_lahir = tgl_lahir;
        this.agama = agama;
        this.pendidikan = pendidikan;
        this.pekerjaan = pekerjaan;
        this.status_perkawinan = status_perkawinan;
        this.kewarganegaraan = kewarganegaraan;
        this.provinsi = provinsi;
        this.kode_pos = kode_pos;
        this.kabupaten_kota = kabupaten_kota;
        this.kecamatan = kecamatan;
        this.desa_kelurahan = desa_kelurahan;
        this.rt = rt;
        this.rw = rw;
        this.alamat = alamat;
    }

    public ModelPenduduk(String nik_warga, String nama_warga) {
        this.nik_warga = nik_warga;
        this.nama_warga = nama_warga;
    }

    public ModelPenduduk(String nik_warga, String nama_warga, String jen_kel, String desa_kelurahan) {
        this.nik_warga = nik_warga;
        this.nama_warga = nama_warga;
        this.jen_kel = jen_kel;
        this.desa_kelurahan = desa_kelurahan;
    }

    public String getNik_warga() {
        return nik_warga;
    }

    public void setNik_warga(String nik_warga) {
        this.nik_warga = nik_warga;
    }

    public String getNama_warga() {
        return nama_warga;
    }

    public void setNama_warga(String nama_warga) {
        this.nama_warga = nama_warga;
    }

    public String getJen_kel() {
        return jen_kel;
    }

    public void setJen_kel(String jen_kel) {
        this.jen_kel = jen_kel;
    }

    public String getTmpt_lahir() {
        return tmpt_lahir;
    }

    public void setTmpt_lahir(String tmpt_lahir) {
        this.tmpt_lahir = tmpt_lahir;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getStatus_perkawinan() {
        return status_perkawinan;
    }

    public void setStatus_perkawinan(String status_perkawinan) {
        this.status_perkawinan = status_perkawinan;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getKabupaten_kota() {
        return kabupaten_kota;
    }

    public void setKabupaten_kota(String kabupaten_kota) {
        this.kabupaten_kota = kabupaten_kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa_kelurahan() {
        return desa_kelurahan;
    }

    public void setDesa_kelurahan(String desa_kelurahan) {
        this.desa_kelurahan = desa_kelurahan;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
