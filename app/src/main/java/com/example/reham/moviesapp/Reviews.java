package com.example.reham.moviesapp;


import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by reham on 5/22/2018.
 */

public class Reviews extends ListFragment {

   static String apiKey = "61786c8037a97ce6a05735a7fd509cc4";

   public Reviews(){}


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int ID = getArguments().getInt("key");
        final ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();
        final Retrofit2.ApiInterface apiService =
                ApiClient.getClient().create(Retrofit2.ApiInterface.class);
        Call<MovieReview> call = apiService.getMovieReviews(ID, apiKey);
        call.enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {

                List<reviewdetails> review=response.body().getResults();
                final String [] Review= new String[review.size()];
for(int i = 0 ; i<review.size();i++){

                  Review[i] = review.get(i).getuserName()+": "+review.get(i).getReview();}
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Review);
                setListAdapter(itemsAdapter);
            }




            @Override
            public void onFailure(Call<MovieReview> call, Throwable t) {
                t.getMessage();
            }


        });

       /*         final String []username=new String[20];
                final String [] reviews=new String[20];
                for(int i=0;i<data.size()-1;i++){
                    username[i]= data.get(i).getuserName();
                    reviews[i]=data.get(i).getReview();
              */


    }}
