package com.example.daniellitvakproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface AnimalApiService {
    @GET("v1/taxa")
    Call<AnimalResponse> getAnimals(
            @Query("rank") String rank,
            @Query("iconic_taxa") String type,
            @Query("per_page") int amount
    );
    @GET("v1/taxa/{id}")
    Call<AnimalResponse> getAnimalDetails(
            @Path("id") int id
    );
}
