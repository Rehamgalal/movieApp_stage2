package com.example.reham.moviesapp;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by reham on 5/18/2018.
 */

public class FavoriteContract {
    public static final String Authority="com.example.reham.moviesapp";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+Authority);
    public static final String path = "favoritemovies";

    public static class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(path).build();
        public static String tableName= "favoritemovies";
        public static String favID = "movieID";
        public static String FavName = "MovieTitle";
        public static String FavPoster="FavoriteMoviePoster";

    }
}