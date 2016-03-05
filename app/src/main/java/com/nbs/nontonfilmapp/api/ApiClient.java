package com.nbs.nontonfilmapp.api;

import com.nbs.nontonfilmapp.model.DiscoverMovieResult;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Sidiq on 05/03/2016.
 */
public interface ApiClient {
    @GET("/3/discover/movie")
    Call<DiscoverMovieResult> getDiscoverMovieAsync(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);
}
