package br.com.ewait.moviesdb.network;

import br.com.ewait.moviesdb.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {

    public final String API_KEY = "445e20b483657b9fbdd7824f90e8171d";

    @GET("/3/movie/popular?api_key=" + API_KEY)
    Call<MovieList> getPopular(@Query("page") Integer page);

    @GET("/3/movie/top_rated?api_key="+ API_KEY)
    Call<MovieList> getTopRated(@Query("page") Integer page);

}
