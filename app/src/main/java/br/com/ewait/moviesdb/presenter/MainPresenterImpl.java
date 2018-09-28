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

    @Override
    public void onRefreshButtonClick() {

        if(mainView != null){
            mainView.showProgress();
        }
        getNoticeIntractor.getMovies(MovieEndpoint.POPULAR, 1 ,this);

    }

    @Override
    public void requestDataFromServer() {
        getNoticeIntractor.getMovies(MovieEndpoint.POPULAR, 1 ,this);
    }


    @Override
    public void onFinished(ArrayList<Movie> noticeArrayList) {
        if(mainView != null){
            mainView.setDataToRecyclerView(noticeArrayList);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}