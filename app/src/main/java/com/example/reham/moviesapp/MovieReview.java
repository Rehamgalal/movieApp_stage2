package com.example.reham.moviesapp;

import android.app.ListFragment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by reham on 5/27/2018.
 */

public class MovieReview {
    @SerializedName("results")
    public List<reviewdetails>results;
@SerializedName("id")
public String id;
@SerializedName("total_pages")
int totalpages;
@SerializedName("total_results")
int totalresults;
    public List<reviewdetails> getResults() {
        return results;
    }

    public void setResults(List<reviewdetails> results) {
        this.results = results;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalresults() {
        return totalresults;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public String getId() {
        return id;
    }

    public void setTotalresults(int totalresults) {
        this.totalresults = totalresults;
    }
}
