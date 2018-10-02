package br.com.ewait.moviesdb.main;

import java.util.ArrayList;

import br.com.ewait.moviesdb.model.Movie;

public interface MainView {

    void showProgress();

    void hideProgress();

    void setDataToRecyclerView(ArrayList<Movie> noticeArrayList);

    void onResponseFailure(Throwable throwable);

}
