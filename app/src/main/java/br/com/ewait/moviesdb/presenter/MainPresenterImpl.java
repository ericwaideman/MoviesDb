package br.com.ewait.moviesdb.presenter;

import java.util.ArrayList;

import br.com.ewait.moviesdb.model.Movie;
import br.com.ewait.moviesdb.model.MovieEndpoint;

public class MainPresenterImpl implements MainContract.presenter, MainContract.GetMovieIntractor.OnFinishedListener {

    private MainContract.MainView mainView;
    private MainContract.GetMovieIntractor getNoticeIntractor;

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetMovieIntractor getNoticeIntractor) {
        this.mainView = mainView;
        this.getNoticeIntractor = getNoticeIntractor;
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    private void showProgress(boolean needShow) {
        if(mainView != null){
            if (needShow)
                mainView.showProgress();
            else
                mainView.hideProgress();
        }
    }

    @Override
    public void requestDataFromServer(MovieEndpoint endpoint) {
        showProgress(true);
        getNoticeIntractor.getMovies(endpoint, 1 ,this);
    }


    @Override
    public void onFinished(ArrayList<Movie> noticeArrayList) {
        if(mainView != null){
            mainView.setDataToRecyclerView(noticeArrayList);
            showProgress(false);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
            showProgress(false);
        }
    }
}