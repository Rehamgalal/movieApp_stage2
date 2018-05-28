package com.example.reham.moviesapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retrofit2 {
    public interface ApiInterface {
        @GET("movie/top_rated/")
        Call<Feed> getTopRatedMovies(@Query("api_key") String apiKey);

        @GET("movie/popular/")
        Call<Feed> getPopularMovies(@Query("api_key") String apiKey);

        @GET("movie/{id}")
        Call<Feed> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

        @GET("movie/{id}/reviews")
        Call<MovieReview> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);
    }}
