package br.com.ewait.moviesdb.presenter;

import java.util.ArrayList;

import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.model.MovieEndpoint;

public interface MainContract {


    interface presenter{

        void onDestroy();

        void requestDataFromServer(MovieEndpoint endpoint);

    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<Movie> noticeArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface GetMovieIntractor {

        interface OnFinishedListener {
            void onFinished(ArrayList<Movie> noticeArrayList);
            void onFailure(Throwable t);
        }

        void getMovies(MovieEndpoint endpoint, Integer page, OnFinishedListener onFinishedListener);
    }
}