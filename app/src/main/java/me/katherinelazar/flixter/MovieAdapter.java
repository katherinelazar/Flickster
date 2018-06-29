package me.katherinelazar.flixter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.katherinelazar.flixter.models.Config;
import me.katherinelazar.flixter.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // list of movies
    ArrayList<Movie> movies;

    // config needed for image urls
    Config config;

    //context for rendering
    Context context;

    //initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {

        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        // get context and create the inflater
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_movie layout

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);


        // return a new viewholder
        return new ViewHolder(movieView);
    }

    //associates created view with a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the movie data at the specified position

        Movie movie = movies.get(position);

        // populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());


        //build url for poster image
        String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());

        // load image using glide
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .transform(new RoundedCornersTransformation(15, 0)))
                .into(holder.ivPosterImage);

    }

    // returns number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create the viewholder as a static inner class

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //track view objects

        ImageView ivPosterImage;
        TextView tvOverview;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
