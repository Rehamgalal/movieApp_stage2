package com.example.reham.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    MovieAdapter movieAdapter1;
    String topmovies = "?api_key=61786c8037a97ce6a05735a7fd509cc4";
    String mostpopularmovies = "popular/?api_key=61786c8037a97ce6a05735a7fd509cc4";
    Boolean check = true;
    Cursor c ;
    final Context mContext = this;
    final MovieAdapter.ItemClickListener itemClickListener=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RC.setLayoutManager(new GridLayoutManager(mContext,snapCount));
        RC.setHasFixedSize(true);
        getdata(topmovies);
  /*      Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mostPopular.getId() == checkedId) {
                    getdata(mostpopularmovies);
                    check = true;
                } else if (topRated.getId() == checkedId) {
                   getdata(topmovies);
                    check = false;

                }else if (favMovies.getId()==checkedId){
                    FavMovies f= new FavMovies();
                    ArrayList<Movie> favMovies=f.FavMovies(mContext);

                    RC.setAdapter(movieAdapter);
                }
            }
        });*/
    }

    @Override
    public void onItemClick(final int position) {
    /*    if (check) {
            Intent i = new Intent(MainActivity.this, MovieInformation.class);
            i.putExtra(moviePath, movieAdapter.PAth[position]);
            i.putExtra(movieName, movieAdapter.Name[position]);
            i.putExtra(Overview, movieAdapter.Overview[position]);
            i.putExtra(Rate, movieAdapter.Rate[position]);
            i.putExtra(Date, movieAdapter.date[position]);
            startActivity(i);
        } else {
            Intent i = new Intent(MainActivity.this, MovieInformation.class);
            i.putExtra(moviePath, movieAdapter1.PAth[position]);
            i.putExtra(movieName, movieAdapter1.Name[position]);
            i.putExtra(Overview, movieAdapter1.Overview[position]);
            i.putExtra(Rate, movieAdapter1.Rate[position]);
            i.putExtra(Date, movieAdapter1.date[position]);
            startActivity(i);
        }*/
    }
    public void getdata(String url){
        Retrofit2.ApiInterface apiService =
                ApiClient.getClient().create(Retrofit2.ApiInterface.class);

        Call<Feed> call = apiService.getTopRatedMovies(url);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
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

