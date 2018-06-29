package me.katherinelazar.flixter.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {

    //values coming back from api

    String title;
    String overview;
    String posterPath;
    String backdropPath;
    Double voteAverage;

    // Empty constructor for Parceler
    public Movie() {}

    //initialize json data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");

        overview = object.getString("overview");

        posterPath = object.getString("poster_path");

        backdropPath = object.getString("backdrop_path");

        voteAverage = object.getDouble("vote_average");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() { return backdropPath; }

    public Double getVoteAverage() { return voteAverage; }
}
