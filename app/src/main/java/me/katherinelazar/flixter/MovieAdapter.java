package me.katherinelazar.flixter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.katherinelazar.flixter.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // list of movies
    ArrayList<Movie> movies;

    //initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // get context and create the inflater
        Context context = parent.getContext();

        return null;
    }

    //associates created view with a new item
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    // returns number of items in the list
    @Override
    public int getItemCount() {
        return 0;
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
