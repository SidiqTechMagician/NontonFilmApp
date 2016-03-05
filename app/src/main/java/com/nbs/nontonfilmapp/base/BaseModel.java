package com.nbs.nontonfilmapp.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 05/03/2016.
 */
public class BaseModel {
    @SerializedName("page")
    public int page;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("total_pages")
    public int totalPages;
}
