package com.example.reham.moviesapp;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reham on 5/19/2018.
 */

public class FavMovies {
   private List<Movie> FavList;
   private Context context;


    public List<Movie> getFavList(Context c) {
        FavList = new ArrayList<>();
        context = c;
        Cursor cursor = c.getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI, null, null, null, FavoriteContract.FavoriteEntry._ID);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index1 = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.favID);
                int index2 = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.FavName);
                int index3 = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.FavPoster);
                int Id = cursor.getInt(index1);
                String name = cursor.getString(index2);
                String poster = cursor.getString(index3);
                Movie movie = new Movie(poster, false, null, null, null, Id, name, null, null, null, null, null, null, null);
                FavList.add(movie);

            } while (cursor.moveToNext());
        }
        return FavList;
    }
cursor.close();

}
