package br.com.daniel.hackathon.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLongitude(){
        return longitude;
    }
}
