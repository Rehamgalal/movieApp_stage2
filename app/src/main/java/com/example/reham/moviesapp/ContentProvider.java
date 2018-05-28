package com.example.reham.moviesapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by reham on 5/18/2018.
 */

public class ContentProvider extends android.content.ContentProvider {
    private FavDBHelper mFavDBHelper ;
    public static final int Movies=100;
    public static final int Movie_ID=101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteContract.Authority,FavoriteContract.path,Movies);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Context context =getContext();
        mFavDBHelper= new FavDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=mFavDBHelper.getReadableDatabase();
        int match =sUriMatcher.match(uri);
        Cursor retCur;
        switch (match){
            case Movies:
       retCur =db.query(FavoriteContract.FavoriteEntry.tableName,projection,selection,selectionArgs,null,null,sortOrder);
        break;
        default:
            throw new UnsupportedOperationException("unknown uri"+uri);}
        retCur.setNotificationUri(getContext().getContentResolver(),uri);
        return retCur;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db=mFavDBHelper.getWritableDatabase();
        int match =sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case Movies:
        long id =db.insert(FavoriteContract.FavoriteEntry.tableName,null,values);

        if (id>0){
           returnUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI,id);
        }else {
            throw new android.database.SQLException("Failed to insert row into "+uri);
        }
        break;
        default:
        throw new UnsupportedOperationException("Unknown Uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db=mFavDBHelper.getWritableDatabase();
        int count;
        int match =sUriMatcher.match(uri);
        switch (match) {
            case Movies:
                count = db.delete(FavoriteContract.FavoriteEntry.tableName, selection, selectionArgs);
                break;

            case Movie_ID:
                String segment = uri.getPathSegments().get(1);
                count = db.delete(FavoriteContract.FavoriteEntry.tableName, FavoriteContract.FavoriteEntry.FavName + "="
                        + segment
                        + (!TextUtils.isEmpty(selection) ? " AND ("
                        + selection+ ')' : ""), selectionArgs);
                break;

            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
