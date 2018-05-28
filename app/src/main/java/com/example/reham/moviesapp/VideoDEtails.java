package com.example.reham.moviesapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by reham on 5/28/2018.
 */

public class VideoDEtails {
    @SerializedName("key")
    String Key;
    @SerializedName("name")
    String Name;
    public String getKey() {
        return Key;
    }

    public String getName() {
        return Name;
    }

    public void setKey(String key) {
        Key = key;
    }

    public void setName(String name) {
        Name = name;
    }
}
