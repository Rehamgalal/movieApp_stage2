package com.example.reham.moviesapp;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieInformation extends AppCompatActivity implements Values, CompoundButton.OnCheckedChangeListener {
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
    @BindView(R.id.myVideo)
    Button videoButton;
    @BindView(R.id.ratelabel)
    TextView rLabel;
    @BindView(R.id.datelabel)
    TextView dLabel;
    @BindView(R.id.overviewlabel)
    TextView oLabel;
    @BindView(R.id.favorite)
    CheckBox checkBox;
    String Name;
    int ID;
    String imagePath;
    String Path;
    String Date0;
    float rate0;
    String overView0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        ButterKnife.bind(this);

        final Context mContext = this;
        Bundle data = getIntent().getExtras();
        if (data != null) {
            String checked = data.getString("checkBox");
            if (checked.equals("checked")) checkBox.setChecked(true);
            Name = data.getString(movieName);
            Path = data.getString(moviePath);
            ID = data.getInt(Fid);
            imagePath = imagesUrl + imageWidth + Path;
            Picasso.with(this).load(imagePath).into(I);
            N.setText(Name);

                overView0 = data.getString(Overview);
                double Rate0 = data.getDouble(Rate);

                rate0 = (float) Rate0 / 2;
                Date0 = data.getString(Date1);
                checkBox.setOnCheckedChangeListener(this);
                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Retrofit2.ApiInterface apiService =
                                ApiClient.getClient().create(Retrofit2.ApiInterface.class);
                        Call<Video> call = apiService.getMovieVideo(ID, apiKey);
                        call.enqueue(new Callback<Video>() {
                            @Override
                            public void onResponse(Call<Video> call, Response<Video> response) {
                                List<VideoDEtails> Video = response.body().getResults();
                                int index = 0;
                                for (int i = 0; i < Video.size(); i++) {
                                    String name = Video.get(i).getName();
                                    if (name.equals("Official Trailer")) {
                                        index = i;
                                    }
                                }
                                String key = Video.get(index).getKey();
                                String uri = "https://www.youtube.com/watch?v=" + key;
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(mContext, "Please install youtube application to watch the video", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Video> call, Throwable t) {

                            }
                        });
                    }
                });
                final Reviews reviews = new Reviews();
                FragmentManager Fm = getFragmentManager();
                Bundle args = new Bundle();
                args.putInt("key", ID);
                reviews.setArguments(args);
                Fm.beginTransaction().replace(R.id.frame, reviews).commit();
                rate.setRating(rate0);
                overview.setText(overView0);
                date.setText(Date0);

            }
        }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {

            @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> insertSquawkTask = new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    ContentValues cv = new ContentValues();
                    cv.put(FavoriteContract.FavoriteEntry.FavName, Name);
                    cv.put(FavoriteContract.FavoriteEntry.favID, ID);
                    cv.put(FavoriteContract.FavoriteEntry.FavPoster, Path);
                    cv.put(FavoriteContract.FavoriteEntry.ReleaseDate,Date0);
                    cv.put(FavoriteContract.FavoriteEntry.VoteAverage,rate0);
                    cv.put(FavoriteContract.FavoriteEntry.OverView,overView0);
                    getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, cv);
                    return null;
                }
            };
            insertSquawkTask.execute();
        } else if (!isChecked) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("checkBox", !isChecked).apply();
            Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
            getContentResolver().delete(uri, FavoriteContract.FavoriteEntry.favID + "=?", new String[]{String.valueOf(ID)});

        }
    }


}

