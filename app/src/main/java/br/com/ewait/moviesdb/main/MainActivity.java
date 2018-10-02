package br.com.ewait.moviesdb.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.ewait.moviesdb.R;
import br.com.ewait.moviesdb.adapter.MoviesAdapter;
import br.com.ewait.moviesdb.details.DetailsActivity;
import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.model.MovieEndpoint;
import br.com.ewait.moviesdb.utils.MovieUtils;

public class MainActivity extends AppCompatActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private MainPresenter presenter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        initializeComponents();

        presenter = new MainPresenter(this, new GetMoviesInteractor());
        presenter.requestDataFromServer(MovieEndpoint.POPULAR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void initializeComponents() {

        mRecyclerView = findViewById(R.id.recyclerview_movies);

        progressBar = findViewById(R.id.progressBar);

        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

    }

    private MoviesAdapter.MovieAdapterOnClickHandler recyclerItemClickListener = movie -> {

        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra(MovieUtils.EXTRA_SELECTED_MOVIE, movie);
        startActivity(i);

    };

    @Override
    public void showProgress() {
        clearData();
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Movie> movieArrayList) {
        MoviesAdapter adapter;

        if (mRecyclerView.getAdapter() == null) {
            adapter = new MoviesAdapter(recyclerItemClickListener, this);
            adapter.setMovieData(movieArrayList);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter = (MoviesAdapter) mRecyclerView.getAdapter();
            adapter.setMovieData(movieArrayList);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Error: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_popular:
                presenter.requestDataFromServer(MovieEndpoint.POPULAR);
                break;
            case R.id.navigation_top_rated:
                presenter.requestDataFromServer(MovieEndpoint.TOP_RATED);
                break;
        }
        return true;
    }

    private void clearData() {
        MoviesAdapter adapter = (MoviesAdapter) mRecyclerView.getAdapter();

        if (adapter != null)
            adapter.setMovieData(new ArrayList<>());
    }

}