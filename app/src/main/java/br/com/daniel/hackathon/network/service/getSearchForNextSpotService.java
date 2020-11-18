package br.com.daniel.hackathon.network.service;

import br.com.daniel.hackathon.network.response.nextSpot.ResponseNextSpot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface getSearchForNextSpotService {
    @Headers("Content-Type: application/json")
    @GET("spot")
    Call<ResponseNextSpot> searchSpot();
}
