package br.com.daniel.hackathon.network.service;

import br.com.daniel.hackathon.network.models.Usuario;
import br.com.daniel.hackathon.network.response.generateSpot.ResponseGenerateSpot;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface searchForNextSpotService {
    @Headers("Content-Type: application/json")
    @POST("spot")
    Call<ResponseGenerateSpot> searchSpot(@Body Usuario usuario);
}
