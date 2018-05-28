package com.example.reham.moviesapp;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.reham.moviesapp.Reviews.apiKey;

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
    @BindView(R.id.favorite)
            CheckBox checkBox;
    String Name;
    int ID;
    String imagePath;
    String Path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        ButterKnife.bind(this);
        final Context mContext=this;
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("checkBox1", false);
        checkBox.setChecked(checked);
        Bundle data = getIntent().getExtras();

      if (data != null) {
           Name = data.getString(movieName);
            Path = data.getString(moviePath);
           String Overview0 = data.getString(Overview);
            double Rate0 = data.getDouble(Rate);
            ID=data.getInt(Fid);
            Log.i("insert succed",""+ID);

           float rate0 =(float) Rate0 / 2;
            String Date0 = data.getString(Date1);
            imagePath = imagesUrl + imageWidth + Path;
            Picasso.with(this).load(imagePath).into(I);
            Retrofit2.ApiInterface apiService =
                    ApiClient.getClient().create(Retrofit2.ApiInterface.class);
            Call<Video> call=apiService.getMovieVideo(ID,apiKey);
            call.enqueue(new Callback<Video>() {
                @Override
                public void onResponse(Call<Video> call, Response<Video> response) {
                    List<VideoDEtails> Video =response.body().getResults();
                    int index=0;
                    for(int i =0;i<Video.size();i++){
                        String name= Video.get(i).getName();
                        if (name.equals("Official Trailer"))
                        {  index=i;
                            continue;}
                    }
                   String key = Video.get(index).getKey();
                    String uri="https://www.youtube.com/watch?v="+key;
               try{     Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);}
                    catch (Exception e){
                        Toast.makeText(mContext,"Please install youtube application to watch the video",Toast.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<Video> call, Throwable t) {

                }
            });
           final Reviews reviews = new Reviews();
            FragmentManager Fm= getFragmentManager();
            Bundle args = new Bundle();
            args.putInt("key", ID);
            reviews.setArguments(args);
            Fm.beginTransaction().replace(R.id.frame,reviews).commit();
            rate.setRating(rate0);
            N.setText(Name);
            overview.setText(Overview0);
            date.setText(Date0);}
    }

    public void onBoxStar(View view) {
       boolean check =((CheckBox)view).isChecked();
        if (check)
        {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean("checkBox1", check).apply();
            ContentValues cv=new ContentValues();
            cv.put(FavoriteContract.FavoriteEntry.FavName,Name);
            cv.put(FavoriteContract.FavoriteEntry.favID,ID);
            cv.put(FavoriteContract.FavoriteEntry.FavPoster,imagePath);
            getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI,cv);
            Log.i("insert succed",cv.getAsString(FavoriteContract.FavoriteEntry.favID));

        }else {
 

            String id = Integer.toString(ID);
            Uri uri= FavoriteContract.FavoriteEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(id).build();           getContentResolver().delete(FavoriteContract.FavoriteEntry.CONTENT_URI, null,null);
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean("checkBox1", false).apply();

        }


    }
}
