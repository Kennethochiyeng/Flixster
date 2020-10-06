package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster101.DetailActivity;
import com.example.flixster101.R;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import models.Movie;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;

    List<Movie> movies;

    public MovieAdapter (Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("MovieAdapter", "onCreateViewHolder");

        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("MovieAdapter", "onBindViewHolder " + position);

        // get the movie at the pos

        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView. findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            // if phone is in landscape

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            }
            // else imageUrl = poster image
            else {

                imageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(imageUrl).into(ivPoster);


            //1. Register the click listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2. Navigate to the action that happens afterward

                    Intent i = new Intent(context, DetailActivity.class);

                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);


                }
            });
        }
    }
}
