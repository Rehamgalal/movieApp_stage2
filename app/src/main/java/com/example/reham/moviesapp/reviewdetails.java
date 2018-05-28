package com.example.reham.moviesapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by reham on 5/27/2018.
 */

public class reviewdetails {
    @SerializedName("author")
    String userName;
    @SerializedName("content")
    String review;
    public reviewdetails(String userName,String review){
        this.userName=userName;
        this.review=review;
    }
    public String getuserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getReview() {
        return review;
    }

    public void setPage(String review) {
        this.review = review;
    }
}
