package com.example.reham.moviesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by reham on 5/18/2018.
 */

public class FavDBHelper extends SQLiteOpenHelper {
    private static final String dbName = "favoritemovies.db";
    private static final int Version = 3;

    public FavDBHelper(Context context) {
        super(context, dbName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.tableName + " ( " + FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteContract.FavoriteEntry.favID + " INTEGER , " + FavoriteContract.FavoriteEntry.FavName + " TEXT , " +
                FavoriteContract.FavoriteEntry.FavPoster + " TEXT , " + FavoriteContract.FavoriteEntry.ReleaseDate+" TEXT , "+ FavoriteContract.FavoriteEntry.VoteAverage+" DOUBLE , "+ FavoriteContract.FavoriteEntry.OverView+" TEXT );";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.tableName);
        onCreate(db);
    }
}
