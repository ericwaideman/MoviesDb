package br.com.ewait.moviesdb.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit instance = null;

    private static final String MOVIESDB_BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain ->  {
                Request request = chain.request().newBuilder().addHeader("api-key", MoviesDataService.API_KEY).build();
                return chain.proceed(request);
        }).build();

        instance = new Retrofit.Builder()
                .baseUrl(MOVIESDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return instance;
    }

}