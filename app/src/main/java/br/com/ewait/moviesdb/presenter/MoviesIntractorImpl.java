package br.com.ewait.moviesdb.presenter;

import android.util.Log;

import br.com.ewait.moviesdb.model.MovieEndpoint;
import br.com.ewait.moviesdb.model.MovieList;
import br.com.ewait.moviesdb.network.MoviesDataService;
import br.com.ewait.moviesdb.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesIntractorImpl implements MainContract.GetMovieIntractor {

        private final String TAG = "MoviesIntractorImpl";

        @Override
        public void getMovies(MovieEndpoint endpoint, Integer page, final OnFinishedListener onFinishedListener) {

            MoviesDataService service = RetrofitInstance.getClient().create(MoviesDataService.class);

            Call<MovieList> call = null;

            switch (endpoint){
                case POPULAR:
                    call = service.getPopular(page);
                    break;
                case TOP_RATED:
                    call = service.getTopRated(page);
                    break;
                default:
                    Log.e(TAG, "Invalid endpoint");
            }

            Log.wtf("URL Called", call.request().url() + "");

            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    onFinishedListener.onFinished(response.body().getMovies());

                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });

        }

}
