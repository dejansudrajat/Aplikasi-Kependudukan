package com.kepependudukan.model;

public class DataPerkawinan {

    private String id_status_perkawinan, status_perkawinan;

    public DataPerkawinan() {

    }

    public DataPerkawinan(String id_status_perkawinan, String status_perkawinan) {
        this.id_status_perkawinan = id_status_perkawinan;
        this.status_perkawinan = status_perkawinan;
    }

    public String getId_status_perkawinan() {
        return id_status_perkawinan;
    }

    public void setId_status_perkawinan(String id_status_perkawinan) {
        this.id_status_perkawinan = id_status_perkawinan;
    }

    public String getStatus_perkawinan() {
        return status_perkawinan;
    }

    public void setStatus_perkawinan(String status_perkawinan) {
        this.status_perkawinan = status_perkawinan;
    }
}
