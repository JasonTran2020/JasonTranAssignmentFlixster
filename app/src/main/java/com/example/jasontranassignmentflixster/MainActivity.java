package com.example.jasontranassignmentflixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.jasontranassignmentflixster.adapters.MovieAdapter;
import com.example.jasontranassignmentflixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {


    //THIS HAS THE KEY SHOULD PROBABLY DELETE IT BEFORE COMMITTING.
    public static final String NOW_PLAYING_URL="API KEY GOES HERE";
    //THIS HAS THE KEY SHOULD PROBABLY DELETE IT BEFORE COMMITTING ABOVE.
    public static final String TAG="MainActivity";

    //Member List of objects of class Movie
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies=findViewById(R.id.rvMovies);
        movies=new ArrayList<>();
        //Create adapter
        MovieAdapter movieAdapter=new MovieAdapter(this,movies);


        //Set the adapter on the recylerview
        rvMovies.setAdapter(movieAdapter);
        //Set a layout manager on the recylerview
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //Creating the client to get the site information
        AsyncHttpClient client=new AsyncHttpClient();
        //Telling the  client to get the URL for the movie. We know it's json so we pass a json handler
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            //If it accesses the site this works
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"success");
                JSONObject jsonObject=json.jsonObject;
                try {

                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.d(TAG,"After results: "+ results.toString()  );
                    //We do .addAll because we don't want movies to be pointing at a new object which the recyler wouldn't be pointing at.
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG,"Json except",e);
                }
            }

            //If it doesn't then it goes to here
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"Fail");
            }
        });


    }
}