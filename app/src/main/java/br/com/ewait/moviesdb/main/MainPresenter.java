package br.com.ewait.moviesdb.main;

import java.util.ArrayList;

import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.model.MovieEndpoint;

public class MainPresenter implements GetMoviesInteractor.OnFinishedListener {

    private MainView mainView;
    private final GetMoviesInteractor getMovieInteractor;

    public MainPresenter(MainView mainView, GetMoviesInteractor interactor) {
        this.mainView = mainView;
        this.getMovieInteractor = interactor;
    }

    public void onDestroy() {
        mainView = null;
    }

    private void showProgress(boolean needShow) {
        if (mainView != null) {
            if (needShow)
                mainView.showProgress();
            else
                mainView.hideProgress();
        }
    }

    public void requestDataFromServer(MovieEndpoint endpoint) {
        showProgress(true);
        getMovieInteractor.getMovies(endpoint, 1, this);
    }


    @Override
    public void onFinished(ArrayList<Movie> noticeArrayList) {
        if (mainView != null) {
            mainView.setDataToRecyclerView(noticeArrayList);
            showProgress(false);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            showProgress(false);
        }
    }
}