package com.example.flixster101;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import adapters.MovieAdapter;
import models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    public final String NOW_PLAYING_URL = " https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        movies = new ArrayList<>();

        // create the adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);


        // set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {

                Log.d(TAG, "onSuccess");

                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray results =  jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());

                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                Log.d(TAG, "onFailure");
            }
        });
    }
}