package br.com.ewait.moviesdb.details;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.ewait.moviesdb.R;
import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.utils.MovieUtils;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie selectedMovie = (Movie) getIntent().getSerializableExtra(MovieUtils.EXTRA_SELECTED_MOVIE);

        if (selectedMovie == null)
            return;

        ImageView ivPoster = findViewById(R.id.iv_details_poster);
        TextView tvSynopsis = findViewById(R.id.tv_synopsis);
        TextView tvRating = findViewById(R.id.tv_rating);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);
        RatingBar ratingBar = findViewById(R.id.ratingBar);


        Glide.with(this).load(selectedMovie.getPosterPath()).into(ivPoster);
        tvReleaseDate.setText(MovieUtils.formatDate(selectedMovie.getReleaseDate()));
        tvSynopsis.setText(selectedMovie.getOverview());
        ratingBar.setRating(selectedMovie.getVoteAverage().floatValue());
        tvRating.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(selectedMovie.getVoteAverage()));

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
