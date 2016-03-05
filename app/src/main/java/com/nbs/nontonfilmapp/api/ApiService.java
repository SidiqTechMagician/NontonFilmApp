package com.nbs.nontonfilmapp.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by Sidiq on 26/10/2015.
 */
public class ApiService {
    public final static String APIKEY = "-- drop your awesome key here :P --";

    public final static String BASE_API_URL = "http://api.themoviedb.org";
    public final static String ROOT_POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    public final static String ROOT_BACKDROP_IMAGE_URL = "http://image.tmdb.org/t/p/w500";

    private ApiService(){}

    public static <S> S createService(Class<S> serviceClass){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.interceptors().add(interceptor);

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return builder.create(serviceClass);
    }

}
