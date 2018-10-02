package br.com.ewait.moviesdb.network;

import br.com.ewait.moviesdb.model.MovieList;
import br.com.ewait.moviesdb.utils.MovieUtils;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {

    @GET("/3/movie/popular")
    Call<MovieList> getPopular(@Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("/3/movie/top_rated")
    Call<MovieList> getTopRated(@Query("api_key") String apiKey, @Query("page") Integer page);

}
