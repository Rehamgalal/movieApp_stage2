package com.example.reham.moviesapp;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.reham.moviesapp.Values.apiKey;

/**
 * Created by reham on 5/22/2018.
 */

public class Reviews extends ListFragment {


    public Reviews() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int ID = getArguments().getInt("key");
        final Retrofit2.ApiInterface apiService =
                ApiClient.getClient().create(Retrofit2.ApiInterface.class);
        Call<MovieReview> call = apiService.getMovieReviews(ID, apiKey);
        call.enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {

                List<reviewdetails> review = response.body().getResults();
                final String[] Review = new String[review.size()];
                for (int i = 0; i < review.size(); i++) {

                    Review[i] = review.get(i).getuserName() + " : " + review.get(i).getReview();
                }

                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Review);
                setListAdapter(itemsAdapter);
            }


            @Override
            public void onFailure(Call<MovieReview> call, Throwable t) {
                t.getMessage();
            }


        });


    }
}
