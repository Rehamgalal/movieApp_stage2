package com.example.reham.moviesapp;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInformation extends AppCompatActivity implements Values {
    @BindView(R.id.Poster)
    ImageView I;
    @BindView(R.id.moviename)
    TextView N;
    @BindView(R.id.movierate)
    RatingBar rate;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.moviedate)
    TextView date;
    @BindView(R.id.ratelabel)
    TextView labelR;
    @BindView(R.id.datelabel)
    TextView labelD;
    @BindView(R.id.overviewlabel)
    TextView labelO;
    @BindView(R.id.myVideo)
    VideoView videoView;
    String Name;
    int ID;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        ButterKnife.bind(this);
        Reviews reviews = new Reviews();
        FragmentManager Fm= getFragmentManager();
        Fm.beginTransaction().add(R.id.frame,reviews).commit();
        Bundle data = getIntent().getExtras();

        if (data != null) {
            Name = data.getString(movieName);
            String Path = data.getString(moviePath);
            String Overview0 = data.getString(Overview);
            double Rate0 = data.getDouble(Rate);
            ID=data.getInt(Fid);
            float rate0 =(float) Rate0 / 2;
            String Date0 = data.getString(Date);
            imagePath = imagesUrl + imageWidth + Path;
            Picasso.with(this).load(imagePath).into(I);
            videoView.setVideoURI(Uri.parse(imagePath));
            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(videoView);
            videoView.setMediaController(vidControl);
            rate.setRating(rate0);
            N.setText(Name);
            rate.setRating(rate0);
            overview.setText(Overview0);
            date.setText(Date0);
        }


    }

    public void onBoxStar(View view) {
       boolean check =((CheckBox)view).isChecked();
        if (check)
        {
            ContentValues cv=new ContentValues();
            cv.put(FavoriteContract.FavoriteEntry.FavName,Name);
            cv.put(FavoriteContract.FavoriteEntry.favID,ID);
            cv.put(FavoriteContract.FavoriteEntry.FavPoster,imagePath);
            getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI,cv);
            Log.i("insert succed","inserted");

        }else {

        }


    }
}
