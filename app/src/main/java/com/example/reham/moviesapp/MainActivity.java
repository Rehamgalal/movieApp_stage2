package com.example.reham.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener, Values {

    private MovieAdapter movieAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView RC;
    @BindView(R.id.toprated)
    RadioButton topRated;
    @BindView(R.id.mostpopular)
    RadioButton mostPopular;
    @BindView(R.id.fav)
    RadioButton favMovies;
    @BindView(R.id.chooose)
    RadioGroup Group;
    String apiKey = "";
    String topmovies= "topmovies";
    String mostpopularmovies = "popular";
    String fav="favmovies";
    Boolean check = true;
    Cursor c ;
    List<Movie> movies;
    final Context mContext = this;
    final MovieAdapter.ItemClickListener itemClickListener=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RC.setLayoutManager(new GridLayoutManager(mContext,snapCount));
        RC.setHasFixedSize(true);
        getdata(mostpopularmovies,apiKey);
       Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mostPopular.getId() == checkedId) {
                    getdata(mostpopularmovies,apiKey);
                    check = true;
                } else if (topRated.getId() == checkedId) {
                   getdata(topmovies,apiKey);
                    check = false;

                }else if (favMovies.getId()==checkedId){

                   FavMovies favMovies= new FavMovies();
                  List<Movie> favorite= favMovies.getFavList(getBaseContext());
                    RC.setAdapter(new MovieAdapter(mContext,numOfItems,favorite, itemClickListener));
                }
            }
        });
    }

    @Override
    public void onItemClick(final int position) {

            Intent i = new Intent(MainActivity.this, MovieInformation.class);
        String imagepath = movies.get(position).getPosterPath();
        String overview = movies.get(position).getOverview();
        String Date= movies.get(position).getReleaseDate();
        String Name = movies.get(position).getOriginalTitle();
        double Vote=movies.get(position).getVoteAverage();
        int ID  =movies.get(position).getId();
        i.putExtra(moviePath,imagepath);
        i.putExtra(movieName,Name);
        i.putExtra(Overview,overview);
        i.putExtra(Date1,Date);
        i.putExtra(Rate,Vote);
        i.putExtra(Fid,ID);
            startActivity(i);

    }
    public void getdata(String criteria,String apikey){
        Retrofit2.ApiInterface apiService =
                ApiClient.getClient().create(Retrofit2.ApiInterface.class);
        Call<Feed> call = null;
        if (criteria.equals(topmovies)) {
        call = apiService.getTopRatedMovies(apikey);
        }else if (criteria.equals(mostpopularmovies)){
        call = apiService.getPopularMovies(apikey);
        }
        
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                movies = response.body().getResults();
                RC.setAdapter(new MovieAdapter(mContext,numOfItems,movies, itemClickListener));
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {

                // Log error here since request failed
                Log.e("failed", t.toString());
            }
        });
      }

}

