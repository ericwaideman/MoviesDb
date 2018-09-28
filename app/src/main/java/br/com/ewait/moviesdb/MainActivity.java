package br.com.ewait.moviesdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.ewait.moviesdb.adapter.MoviesAdapter;
import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.presenter.MoviesIntractorImpl;
import br.com.ewait.moviesdb.presenter.MainContract;
import br.com.ewait.moviesdb.presenter.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private MainContract.presenter presenter;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        initializeComponents();

        presenter = new MainPresenterImpl(this, new MoviesIntractorImpl());
        presenter.requestDataFromServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void initializeComponents() {

        mRecyclerView = findViewById(R.id.recyclerview_movies);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

    }

    private MoviesAdapter.MovieAdapterOnClickHandler recyclerItemClickListener = movie -> {
        Toast.makeText(MainActivity.this,
                "List title:  " + movie.getTitle(),
                Toast.LENGTH_LONG).show();
    };

    @Override
    public void showProgress() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Movie> movieArrayList) {
        MoviesAdapter adapter = new MoviesAdapter(recyclerItemClickListener);
        adapter.setMovieData(movieArrayList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            presenter.onRefreshButtonClick();
        }

        return super.onOptionsItemSelected(item);
    }
}