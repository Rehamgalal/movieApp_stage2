package com.example.reham.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Created by reham on 5/19/2018.
 */

public class FavMovies  {
    ArrayList<Movie> FavList = new ArrayList<>();
Context context;


    @RequiresApi(api = Build.VERSION_CODES.O)
public ArrayList<Movie> FavMovies(Context c ){
    context=c;

    Cursor cursor = c.getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI,null,null,null);
    while (cursor.moveToNext()){
        int index1=cursor.getColumnIndex(FavoriteContract.FavoriteEntry.favID);
        int index2=cursor.getColumnIndex(FavoriteContract.FavoriteEntry.FavName);
        int index3=cursor.getColumnIndex(FavoriteContract.FavoriteEntry.FavPoster);
        int Id= cursor.getInt(index1);
        String name = cursor.getString(index2);
        String poster = cursor.getString(index3);



    }
return FavList;
}

}
