package com.example.jasontranassignmentflixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.core.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jasontranassignmentflixster.DetailActivity;
import com.example.jasontranassignmentflixster.R;
import com.example.jasontranassignmentflixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int GOOD_MOVIE=33;
    public static final int OK_MOVIE = 32;
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override

    //Inflating a layout from XML and returning the viewholder
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView;
        if(viewType==GOOD_MOVIE)
        {
            movieView=LayoutInflater.from(context).inflate(R.layout.item_movie_good,parent,false);
            return new ViewHolder2(movieView);
        }
        else{
            movieView=LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
            return new ViewHolder(movieView);
        }

    }

    //Involved populating the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        //Get movie at the passed position
        Movie movie=movies.get(position);

        //Bind the movie data into the VH
        if(holder.getItemViewType()==GOOD_MOVIE)
        {
            ViewHolder2 temp=(ViewHolder2) holder;
            temp.bind(movie);
        }
        else
        {
            ViewHolder temp=(ViewHolder) holder;
            temp.bind(movie);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getRating()>=7)
        {
            return GOOD_MOVIE;
        }
        return OK_MOVIE;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageButton ibButton;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            container=itemView.findViewById(R.id.container);
            ibButton=itemView.findViewById(R.id.imageButton);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //if potrait mode
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
                Glide.with(context).load(movie.getPosterPath()).into(ivPoster);

            //if landscape mode
            else{
                Glide.with(context).load(movie.getBackDropPath()).into(ivPoster);
            }
            //For movies with a rating less than 5, they do not get a play button
            if(movie.getRating()<5)
                ibButton.setVisibility(View.INVISIBLE);
            //Identify when a click happens on a movie
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //make sure it works
                    //Toast.makeText(context, movie.getTitle(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    Pair<View, String> p1= Pair.create((View)tvTitle,"titleTrans");
                    Pair<View, String> p2= Pair.create((View)tvOverview,"overviewTrans");
                    Pair<View, String> p3= Pair.create((View)ivPoster,"youtube");

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1, p2,p3);

                    //Go to the DetailActivity
                    context.startActivity(i,options.toBundle());
                }
            });

            //When the play button is clicked, just do the same as if the container was clicked.
            ibButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    container.callOnClick();
                }
            });


        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{
        RelativeLayout container;
        ImageView ivBackDrop;
        public ViewHolder2(@NonNull @NotNull View itemView) {
            super(itemView);
            ivBackDrop=itemView.findViewById(R.id.ivBackDrop);
            container=itemView.findViewById(R.id.bruh);
        }
        public void bind(Movie movie)
        {
            Glide.with(context).load(movie.getBackDropPath()).into(ivBackDrop);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //make sure it works
                    //Toast.makeText(context, movie.getTitle(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));


                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
                    //Go to the DetailActivity
                    context.startActivity(i,options.toBundle());
                }
            });
        }
    }

}
