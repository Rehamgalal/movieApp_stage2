package com.example.reham.moviesapp;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reham on 5/19/2018.
 */

public class FavMovies  {
    List<Movie> FavList;
Context context;



public List<Movie> getFavList(Context c ){
    context=c;

    Cursor cursor = c.getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI,null,null,null, FavoriteContract.FavoriteEntry._ID);
    if (cursor != null && cursor.moveToFirst()){
    do {
        FavDBHelper table=new FavDBHelper(context);
        SQLiteDatabase db = table.getWritableDatabase();
        String count = "SELECT * FROM favoritemovies";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        String name = mcursor.getString(2);

            Log.i("threis data",""+name);

//
        int index1=cursor.getColumnIndex(FavoriteContract.FavoriteEntry.favID);
        int index2=cursor.getColumnIndex(FavoriteContract.FavoriteEntry.FavName);
        int index3=cursor.getColumnIndex(FavoriteContract.FavoriteEntry.FavPoster);
        int Id= cursor.getInt(index1);

        String poster = cursor.getString(index3);
        String ID =cursor.getString(index1);
        Log.i("result","msg"+poster+name+ID);
        Movie movie=new Movie(poster,false,null,null,null,Id,name,null,null,null,null,null,null,null);
FavList.add(movie);

    }while (cursor.moveToNext());}
return FavList;
}

}
