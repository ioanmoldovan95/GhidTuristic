package com.example.john.ghidturistic.Models;

import java.io.Serializable;

/**
 * Created by John on 5/24/2017.
 */

public class Position implements Serializable {


    private double lat=0.0f,lng=0.0f;

    public Position(){
        lat=0.0f;
        lng=0.0f;
    }

    public Position(double lat, double lng){
        this.lat=lat;
        this.lng=lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
