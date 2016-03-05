package com.nbs.nontonfilmapp.model;

import com.google.gson.annotations.SerializedName;
import com.nbs.nontonfilmapp.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by Sidiq on 05/03/2016.
 */
public class DiscoverMovieResult extends BaseModel {
    @SerializedName("results")
    public ArrayList<MovieItem> listItems = new ArrayList<>();
}
