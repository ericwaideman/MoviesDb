package br.com.ewait.moviesdb.main;

import android.util.Log;

import java.util.ArrayList;

import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.model.MovieEndpoint;
import br.com.ewait.moviesdb.model.MovieList;
import br.com.ewait.moviesdb.network.MoviesDataService;
import br.com.ewait.moviesdb.network.RetrofitInstance;
import br.com.ewait.moviesdb.utils.MovieUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class GetMoviesInteractor {

    private static final String TAG = "GetMoviesInteractor";

    interface OnFinishedListener {
        void onFinished(ArrayList<Movie> noticeArrayList);
        void onFailure(Throwable t);
    }

    void getMovies(MovieEndpoint endpoint, Integer page, final OnFinishedListener onFinishedListener) {

        MoviesDataService service = RetrofitInstance.getClient().create(MoviesDataService.class);

        Call<MovieList> call = null;

        switch (endpoint) {
            case POPULAR:
                call = service.getPopular(MovieUtils.getTmdbApiKey(), page);
                break;
            case TOP_RATED:
                call = service.getTopRated(MovieUtils.getTmdbApiKey(), page);
                break;
            default:
                Log.e(TAG, "Invalid endpoint");
        }

        Log.wtf(TAG, call.request().url() + "");

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.body() != null)
                    onFinishedListener.onFinished(response.body().getMovies());
                else
                    onFinishedListener.onFailure(new Throwable("null body"));

            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

}
