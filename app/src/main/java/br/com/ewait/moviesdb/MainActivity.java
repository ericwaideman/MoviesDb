package br.com.ewait.moviesdb;

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

import br.com.ewait.moviesdb.adapter.MoviesAdapter;
import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.model.MovieEndpoint;
import br.com.ewait.moviesdb.presenter.MainContract;
import br.com.ewait.moviesdb.presenter.MainPresenterImpl;
import br.com.ewait.moviesdb.presenter.MoviesIntractorImpl;

public class MainActivity extends AppCompatActivity implements MainContract.MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private BottomNavigationView navigationView;
    private MainContract.presenter presenter;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        initializeComponents();

        presenter = new MainPresenterImpl(this, new MoviesIntractorImpl());
        presenter.requestDataFromServer(MovieEndpoint.POPULAR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void initializeComponents() {

        mRecyclerView = findViewById(R.id.recyclerview_movies);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        mLoadingIndicator = findViewById(R.id.progress_spinner);

    }

    private MoviesAdapter.MovieAdapterOnClickHandler recyclerItemClickListener = movie -> {
        Toast.makeText(MainActivity.this,
                "List title:  " + movie.getTitle(),
                Toast.LENGTH_LONG).show();
    };

    @Override
    public void showProgress() {
        if (mLoadingIndicator != null)
            mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (mLoadingIndicator != null)
            mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Movie> movieArrayList) {
        MoviesAdapter adapter;

        if(mRecyclerView.getAdapter() == null) {
            adapter = new MoviesAdapter(recyclerItemClickListener, this);
            adapter.setMovieData(movieArrayList);
            mRecyclerView.setAdapter(adapter);
        }
        else {
            adapter = (MoviesAdapter) mRecyclerView.getAdapter();
            adapter.setMovieData(movieArrayList);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.navigation_popular:
                presenter.requestDataFromServer(MovieEndpoint.POPULAR);
                break;
            case R.id.navigation_top_rated:
                presenter.requestDataFromServer(MovieEndpoint.TOP_RATED);
                break;
        }
        return true;
    }
}