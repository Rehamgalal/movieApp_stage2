package com.example.reham.moviesapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by reham on 5/28/2018.
 */

public class Video {
    @SerializedName("results")
    List<VideoDEtails> Results;

    public void setResults(List<VideoDEtails> results) {
        Results = results;
    }

    public List<VideoDEtails> getResults() {
        return Results;
    }
}
