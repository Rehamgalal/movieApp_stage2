package com.example.reham.moviesapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by reham on 2/25/2018.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private int numberOfItems;
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<Movie> Movies;
    List<Integer> Ids;
    List<String>isChecked;


    public MovieAdapter(@NonNull Context context, int itemsNum, List<Movie> movies, ItemClickListener itemClickListener) {
        this.numberOfItems = itemsNum;
        this.mClickListener = itemClickListener;
        mContext = context;
        Movies = movies;
        Ids = new ArrayList<>();
        isChecked=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imagepath = Movies.get(position).getPosterPath();
        String imageP = "http://image.tmdb.org/t/p/w500/" + imagepath;
        Picasso.with(mContext).load(imageP).into(holder.image);
        int found = Movies.get(position).getId();
        if (Ids.contains(found)) {isChecked.add("checked");}else {isChecked.add("unchecked");}


    }


    @Override
    public int getItemCount() {

        return Movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageview)
        ImageView image;
        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            FavMovies favMovies = new FavMovies();
            List<Movie> favorite = favMovies.getFavList(mContext);
            for (int i = 0; i < favorite.size(); i++) {
                int id = favorite.get(i).getId();
                Ids.add(id);
            }
        }


        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onItemClick(getAdapterPosition(), isChecked.get(getAdapterPosition()));

        }
    }

    public interface ItemClickListener {

        void onItemClick(int position,String isChecked);

    }
}
