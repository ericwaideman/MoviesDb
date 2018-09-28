package br.com.ewait.moviesdb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ewait.moviesdb.R;
import br.com.ewait.moviesdb.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie clickedMovie);
    }

    public MoviesAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final TextView mWeatherTextView;

        MovieAdapterViewHolder(View view) {
            super(view);
            mWeatherTextView = view.findViewById(R.id.tv_movie_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            Movie clickedMovie = mMovieData.get(adapterPosition);
            mClickHandler.onClick(clickedMovie);
        }
    }

    @Override
    @NonNull
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder forecastAdapterViewHolder, int position) {

        Movie currentMovie = mMovieData.get(position);
        forecastAdapterViewHolder.mWeatherTextView.setText(currentMovie.getTitle());
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    public void setMovieData(ArrayList<Movie> weatherData) {
        mMovieData = weatherData;
        notifyDataSetChanged();
    }
}