package me.katherinelazar.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    //base url for loading images
    String imageBaseUrl;
    // the poster size to use when getting images
    String posterSize;

    public Config(JSONObject object) throws JSONException {

        JSONObject images = object.getJSONObject("images");

        imageBaseUrl = images.getString("secure_base_url");


        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");

        posterSize = posterSizeOptions.optString(3, "w342");


    }

    // helper method for creating url
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }


    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
