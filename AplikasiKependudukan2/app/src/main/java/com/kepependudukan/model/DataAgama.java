package com.kepependudukan.model;

public class DataAgama {
    private String id_agama, agama;

    public DataAgama() {

    }

    public DataAgama(String id_agama, String agama) {
        this.id_agama = id_agama;
        this.agama = agama;
    }

    public String getId_agama(){
        return id_agama;
    }

    public void setId_agama(String id_agama){
        this.id_agama = id_agama;
    }

    public String getAgama(){
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }
}
