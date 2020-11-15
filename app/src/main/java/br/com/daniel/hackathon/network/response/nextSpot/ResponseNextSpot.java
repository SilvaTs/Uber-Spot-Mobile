package br.com.daniel.hackathon.network.response.nextSpot;

import com.google.gson.annotations.SerializedName;

public class ResponseNextSpot{

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("longitude")
	private double longitude;

	public double getLatitude(){
		return latitude;
	}

	public double getLongitude(){
		return longitude;
	}
}