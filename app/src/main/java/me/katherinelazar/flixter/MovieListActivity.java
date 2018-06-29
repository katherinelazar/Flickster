package me.katherinelazar.flixter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.katherinelazar.flixter.models.Movie;

public class MovieListActivity extends AppCompatActivity {

    //Constants, base url for api

    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    // parameter name for the api key
    public final static String API_KEY_PARAM = "api_key";

    // the api key - need to move to secure location
//    public final static String API_KEY = "1314fd2b25d02fc8f9ba458932b25426";

    //tag for logging from this activity
    public final static String TAG = "MovieListActivity";

    //instance fields
    AsyncHttpClient client;


    // the list of currently playing movies
    ArrayList<Movie> movies;

    //the recycler view
    RecyclerView rvMovies;

    // the adapter wired to the recycler view
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //initialize client
        client = new AsyncHttpClient();

        movies = new ArrayList<>();

        //initialize the adapter movies array cannot be reinitialized after this point
        adapter = new MovieAdapter(movies);

        //resolve the recycler view and connect a layout manager and the adapter
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        getConfiguration();



    }

    // get the list of currently playing movies from api
    private void getNowPlaying() {
        String url = API_BASE_URL + "/movie/now_playing";

        //set request parameters
        RequestParams params = new RequestParams();

        params.put(API_KEY_PARAM, getString(R.string.api_key)); // api key is always required

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //load results into movies list
                try {
                    JSONArray results = response.getJSONArray("results");

                    for(int i = 0; i< results.length(); i ++ ) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);

                        // notify adapter that a row was added
                        adapter.notifyItemInserted(movies.size() - 1);
                    }

                    //pass in fully formed template or prototype string
                    Log.i(TAG, String.format("loaded %s movies", results.length()));

                } catch (JSONException e) {
                    logError("failed to parse now playing movies", e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("failed to get data from now playing endpoint", throwable, true);
            }
        });
    }


    // get configuration from API
    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";

        //set request parameters
        RequestParams params = new RequestParams();

        params.put(API_KEY_PARAM, getString(R.string.api_key)); // api key is always required

        //execute GET request to get JSON response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //get the image base url
                try {

                    JSONObject images = response.getJSONObject("images");

                    imageBaseUrl = images.getString("secure_base_url");


                    JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");

                    posterSize = posterSizeOptions.optString(3, "w342");

                    Log.i(TAG, String.format("loaded configuration with imagebaseurl %s and posterSize %s", imageBaseUrl, posterSize));

                    //call new method from onCreate, get now playing movie
                    getNowPlaying();


                } catch (JSONException e) {
                    logError("Failure while parsing", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
            }
        });

    }


    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);

        //alert user to avoid silent errors
        if (alertUser) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
